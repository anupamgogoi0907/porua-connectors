package com.porua.component.java;

import org.springframework.context.ApplicationContext;

import com.porua.core.context.PoruaContext;

/**
 * Interface for implementing Custom Components.
 * @author agogoi
 *
 */
public interface Pluggable {
	public void onCall(ApplicationContext springContext, PoruaContext poruaContext, Object payload);
}
