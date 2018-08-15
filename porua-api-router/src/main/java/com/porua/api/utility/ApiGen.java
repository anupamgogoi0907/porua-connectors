package com.porua.api.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.ContainerRequest;

import com.porua.api.router.ContextMaker;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Operation;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class ApiGen {

	private static String SRC_FILE = "src/main/java";
	public static String RESOURCE_PACKAGE = "api.generated";

	public static void main(String[] args) throws Exception {
		generateApiClass("api.yaml", SRC_FILE);
	}

	public static void generateApiClass(String apiPath, String srcPath) throws Exception {
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
		listField.add(FieldSpec.builder(Configuration.class, "config")
				.addAnnotation(AnnotationSpec.builder(Context.class).build()).build());
		listField.add(FieldSpec.builder(UriInfo.class, "uriInfo")
				.addAnnotation(AnnotationSpec.builder(Context.class).build()).build());
		listField.add(FieldSpec.builder(ContainerRequest.class, "request")
				.addAnnotation(AnnotationSpec.builder(Context.class).build()).build());

		TypeSpec className = TypeSpec.classBuilder("MyAPI").addModifiers(Modifier.PUBLIC).superclass(ContextMaker.class)
				.addAnnotations(listClassAnnot).addFields(listField).addMethods(listMethod).build();
		JavaFile javaFile = JavaFile.builder(RESOURCE_PACKAGE, className).build();
		javaFile.writeTo(new File(srcPath));
	}

	static MethodSpec addMethod(String path, String operationName, Class<?> type, Operation op) {
		String methodName = operationName.concat(path.replaceAll("/", ""));
		MethodSpec.Builder msBuilder = MethodSpec.methodBuilder(methodName);

		// Operation annotation.
		AnnotationSpec.Builder asBuilder = null;
		asBuilder = AnnotationSpec.builder(type);
		msBuilder.addAnnotation(asBuilder.build());

		// Path annotation.
		asBuilder = AnnotationSpec.builder(Path.class);
		asBuilder.addMember("value", CodeBlock.of("$S", path));
		msBuilder.addAnnotation(asBuilder.build());

		// Produces annotation.
		if (op.getProduces() != null) {
			String produeces = makeString(op.getProduces());
			msBuilder.addAnnotation(
					AnnotationSpec.builder(Produces.class).addMember("value", CodeBlock.of(produeces)).build());
		}

		// Consumes annotation.
		if (op.getConsumes() != null) {
			String consumes = makeString(op.getConsumes());
			msBuilder.addAnnotation(
					AnnotationSpec.builder(Consumes.class).addMember("value", CodeBlock.of(consumes)).build());
		}

		// APIOperation.
		msBuilder.addAnnotation(
				AnnotationSpec.builder(ApiOperation.class).addMember("value", CodeBlock.of("$S", methodName)).build());

		// APIResponses.
		if (op.getResponses() != null) {
			System.out.println("");
			asBuilder = AnnotationSpec.builder(ApiResponses.class);

			List<AnnotationSpec> listInner = new ArrayList<>();
			op.getResponses().keySet().stream().forEach(key -> {
				Response res = op.getResponses().get(key);
				AnnotationSpec.Builder asBuilderInner = AnnotationSpec.builder(ApiResponse.class);
				asBuilderInner.addMember("code", CodeBlock.of("$L", Integer.parseInt(key)));
				asBuilderInner.addMember("message", CodeBlock.of("$S", res.getDescription()));
				listInner.add(asBuilderInner.build());
			});

			asBuilder.addMember("value", CodeBlock.of(makeString(listInner)));
			msBuilder.addAnnotation(asBuilder.build());
		}

		// Method parameters.
		msBuilder.addParameters(addMethodParams(op));

		// Body.
		msBuilder.addCode(CodeBlock.of("makeContext(config, uriInfo, request, payload, asyncResponse);"));
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
		listParam.add(ParameterSpec.builder(Object.class, "payload").build());
		listParam.add(
				ParameterSpec.builder(AsyncResponse.class, "asyncResponse").addAnnotation(Suspended.class).build());
		return listParam;
	}

	static String makeString(List<?> list) {
		StringBuilder sb = new StringBuilder("{");
		if (list.get(0) instanceof String) {
			list.forEach((s) -> {
				sb.append("\"").append(s).append("\"").append(",");
			});
		} else if (list.get(0) instanceof AnnotationSpec) {
			list.forEach(as -> {
				sb.append(as.toString()).append(",");
			});
		}
		String str = sb.toString().substring(0, sb.length() - 1).concat("}");
		return str;
	}

}
