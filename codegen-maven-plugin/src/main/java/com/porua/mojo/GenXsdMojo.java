package com.porua.mojo;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.porua.codegen.GenerateCode;
import com.porua.utility.MojoUtility;

@Mojo(name = "genxsd")
public class GenXsdMojo extends AbstractMojo {
	@Parameter(name = "pkg", defaultValue = "generated", required = true)
	private String pkg;

	@Parameter(name = "connectors", required = true)
	private List<String> connectors;

	@Component
	private MavenProject mavenProject;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			if (connectors != null) {
				ClassLoader loader = MojoUtility.getClassLoader(mavenProject);
				GenerateCode.generateXsdAssets(pkg, connectors, loader);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
