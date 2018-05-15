package com.porua.utility;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.apache.maven.project.MavenProject;

public class MojoUtility {

	private static ClassLoader loader = null;

	public static ClassLoader getClassLoader(MavenProject mavenProject) throws Exception {
		if (MojoUtility.loader == null) {
			List<?> runtimeClasspathElements = mavenProject.getRuntimeClasspathElements();
			URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
			for (int i = 0; i < runtimeClasspathElements.size(); i++) {
				String element = (String) runtimeClasspathElements.get(i);
				runtimeUrls[i] = new File(element).toURI().toURL();
			}
			MojoUtility.loader = new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
		}
		return MojoUtility.loader;
	}
}
