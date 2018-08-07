package com.porua.component.java;

import org.springframework.context.ApplicationContext;

import com.porua.core.context.PoruaContext;

public interface Pluggable {
	public void onCall(ApplicationContext springContext, PoruaContext poruaContext);
}
