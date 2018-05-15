package com.porua;

import java.util.ArrayList;
import java.util.List;

import com.porua.codegen.GenerateCode;
import com.porua.test.MyConnector;
import com.porua.test.SkypeConnector;

@SuppressWarnings("all")
public class Main {

	public static void main(String[] args) throws Exception {
		List<String> list = new ArrayList<>();
		list.add(SkypeConnector.class.getName());
		list.add(MyConnector.class.getName());
//		GenerateCode.generateJaxbAssets("com.porua.test", list, Main.class.getClassLoader());
		GenerateCode.generateXsdAssets("com.porua.test", list, Main.class.getClassLoader());
		
	}

}
