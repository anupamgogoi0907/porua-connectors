package com.porua.core.context;

import java.net.URL;
import java.net.URLClassLoader;

public class PoruaClassLoader extends URLClassLoader {

	public PoruaClassLoader(URL url) {
		super(new URL[0]);
		addURL(url);
	}

}
