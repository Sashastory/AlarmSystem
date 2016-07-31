package org.sashastory.javadev.rest;

import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;

public class RestJaxRsApplication extends ResourceConfig {
		
	public RestJaxRsApplication() {
		
		packages("org.sashastory.javadev.rest");
		register(EntityFilteringFeature.class);
		EncodingFilter.enableFor(this, GZipEncoder.class);
		
	}
}
