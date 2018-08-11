package com.porua.api.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import io.swagger.annotations.Api;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class ApiGen {

	private static String SRC_FILE = "src/main/java";

	public static void main(String[] args) throws Exception {
		generateApiClass("api.yaml");
	}

	public static void generateApiClass(String apiPath) throws Exception {
		List<MethodSpec> listMethod = new ArrayList<>();
		Swagger api = new SwaggerParser().read(apiPath);

		api.getPaths().forEach((path, pathItem) -> {
			MethodSpec ms = null;
			if (pathItem.getGet() != null) {
				ms = addMethod(path, "get", GET.class, pathItem.getGet());
				listMethod.add(ms);
			}
			if (pathItem.getPost() != null) {
				ms = addMethod(path, "post", POST.class, pathItem.getPost());
				listMethod.add(ms);
			}
			if (pathItem.getPut() != null) {
				ms = addMethod(path, "put", PUT.class, pathItem.getPut());
				listMethod.add(ms);
			}
			if (pathItem.getDelete() != null) {
				ms = addMethod(path, "delete", DELETE.class, pathItem.getDelete());
				listMethod.add(ms);
			}
		});

		// API class annotations.
		List<AnnotationSpec> listClassAnnot = new ArrayList<>();
		listClassAnnot.add(AnnotationSpec.builder(Path.class)
				.addMember("value", CodeBlock.of("$S", api.getBasePath() == null ? "/" : api.getBasePath())).build());
		listClassAnnot.add(AnnotationSpec.builder(Api.class).addMember("value", CodeBlock.of("$S", "Test")).build());

		// API class fields.
		List<FieldSpec> listField = new ArrayList<>();
		listField.add(FieldSpec.builder(UriInfo.class, "uriInfo")
				.addAnnotation(AnnotationSpec.builder(Context.class).build()).build());
		listField.add(FieldSpec.builder(HttpHeaders.class, "headers")
				.addAnnotation(AnnotationSpec.builder(Context.class).build()).build());

		TypeSpec className = TypeSpec.classBuilder("MyAPI").addModifiers(Modifier.PUBLIC).addAnnotations(listClassAnnot)
				.addFields(listField).addMethods(listMethod).build();
		JavaFile javaFile = JavaFile.builder("anupam.generated", className).build();
		javaFile.writeTo(new File(SRC_FILE));
	}

	static MethodSpec addMethod(String path, String operationName, Class<?> type, Operation op) {
		MethodSpec.Builder msBuilder = MethodSpec.methodBuilder(operationName.concat(path.replaceAll("/", "")));

		// Operation annotation.
		AnnotationSpec.Builder asBuilder = null;
		asBuilder = AnnotationSpec.builder(type);
		msBuilder.addAnnotation(asBuilder.build());

		// Path annotation.
		asBuilder = AnnotationSpec.builder(Path.class);
		asBuilder.addMember("value", CodeBlock.of("$S", path));
		msBuilder.addAnnotation(asBuilder.build());

		msBuilder.addParameters(addMethodParams(op));
		return msBuilder.addModifiers(Modifier.PUBLIC).build();
	}

	static List<ParameterSpec> addMethodParams(Operation op) {
		List<ParameterSpec> listParam = new ArrayList<>();

		if (op.getParameters() != null) {
			op.getParameters().forEach(p -> {
				AnnotationSpec.Builder asBuilder = null;
				ParameterSpec.Builder psBuilder = null;

				if ("query".equals(p.getIn())) {
					asBuilder = AnnotationSpec.builder(QueryParam.class);
					asBuilder.addMember("value", CodeBlock.of("$S", p.getName()));
					psBuilder = ParameterSpec.builder(String.class, p.getName());
					psBuilder.addAnnotation(asBuilder.build());

				} else if ("header".equals(p.getIn())) {
					asBuilder = AnnotationSpec.builder(HeaderParam.class);
					asBuilder.addMember("value", CodeBlock.of("$S", p.getName()));
					psBuilder = ParameterSpec.builder(String.class, p.getName());
					psBuilder.addAnnotation(asBuilder.build());
				}
				if (psBuilder != null) {
					listParam.add(psBuilder.build());
				}

			});
		}

		// Required parameter.
		listParam.add(
				ParameterSpec.builder(AsyncResponse.class, "asyncResponse").addAnnotation(Suspended.class).build());
		return listParam;
	}

}
