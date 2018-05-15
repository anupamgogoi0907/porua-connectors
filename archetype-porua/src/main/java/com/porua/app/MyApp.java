package com.porua.app;

import java.io.File;
import java.net.URL;

import org.apache.xbean.spring.context.FileSystemXmlApplicationContext;

import com.porua.container.PoruaContainer;

public class MyApp {

	public static void main(String[] a) throws Exception {
		local();
	}

	public static void porua() throws Exception {
		String file1 = "file:/Users/ac-agogoi/java-projects/my-app/src/main/app/my-app-1.xml";
		String file2 = "file:/Users/ac-agogoi/java-projects/my-app/src/main/app/my-app-2.xml";
		String[] apps = new String[] { file1, file2 };
		URL jarUrl = new File("/Users/ac-agogoi/java-projects/my-app/target/my-app-1.0.0.jar").toURI().toURL();
		PoruaContainer.loadSingleApp(jarUrl, apps);
	}

	public static void local() {
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
				"file:/Users/ac-agogoi/java-projects/my-app/src/main/app/bean-test.xml");
		ctx.close();

	}
}
