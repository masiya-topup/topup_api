package com.masiya.topup;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

//import com.wordnik.swagger.jersey.listing.ApiListingResourceJSON;
//import com.wordnik.swagger.jersey.listing.JerseyApiDeclarationProvider;
//import com.wordnik.swagger.jersey.listing.JerseyResourceListingProvider;

public class TopupApplication extends ResourceConfig {

	public TopupApplication() {
		register(new TopupApplicationBinder());
		packages(this.getClass().getPackage().getName());
		register(RolesAllowedDynamicFeature.class);
		//register(ApiListingResourceJSON.class);
		//register(JerseyApiDeclarationProvider.class);
		//register(JerseyResourceListingProvider.class);
		TopupConstants.initialize(System.getProperty("env"));
	}
	
}
