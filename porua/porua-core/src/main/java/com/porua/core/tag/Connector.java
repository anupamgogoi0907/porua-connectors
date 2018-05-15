package com.porua.core.tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Connector {
	public String tagName();

	public String tagNamespace();

	public String tagSchemaLocation();

	public String imageName();
}
