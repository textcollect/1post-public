package com.gen.onepost.security;

//This Class will implement the WebMvcConfigurer interface provided by Spring Boot Framework
//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurer.html

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//This Class is to perform action on URL Routing and mapping when a HTTP request comes in
//E.g. if user key in localhost:8080 in the browser, browser will send a HTTP GET
// request to the server (back-end). The back-end will need to handle which HTML to
// response back to the browser (client) - index.html
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	@Value("${image.folder}")
	private String imageFolder; //now imageFolder variable the value = siteimages

	public void addViewControllers(ViewControllerRegistry registry) {
		//Map the browser's URL to a specific View (HTML) inside resources/templates directory
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/about").setViewName("about");
		registry.addViewController("/postlist").setViewName("postlist");
		registry.addViewController("/postForm").setViewName("postForm");
		registry.addViewController("/showPostDetail").setViewName("showPostDetail");
		registry.addViewController("/help").setViewName("help");

		registry.addViewController("/registerNewPerson").setViewName("registerNewPerson");
		registry.addViewController("/login").setViewName("login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static")
				.addResourceLocations("classpath:/static/")
				.setCachePeriod(0);
	}
}

/*
ResourceHandlerRegistry object
 Stores registrations of resource handlers for serving static resources such as
images, css files and others through Spring MVC including setting cache headers
optimized for efficient loading in a web browser.
Resources can be served out of locations under web application root, from the classpath, and others.

 To create a resource handler, use addResourceHandler(String...)
providing the URL path patterns for which the handler should be invoked to serve static resources
(e.g. "/resources/**").

 Then use additional methods on the returned ResourceHandlerRegistration to add one or more locations
from which to serve static content from (e.g. {"/", "classpath:/META-INF/public-web-resources/"})
or to specify a cache period for served resources.


addResourceHandler method
 Add a resource handler to serve static resources.
The handler is invoked for requests that match one of the specified URL path patterns.
Patterns such as "/static/**" or "/css/{filename:\\w+\\.css}" are supported.

Returns: ResourceHandlerRegistration object to add locations to serve static content


addResourceLocations method
 Add one or more resource locations from which to serve static content.
Each location must point to a valid directory.
Multiple locations may be specified as a comma-separated list,
and the locations will be checked for a given resource in the order specified.

 For example, {"/", "classpath:/META-INF/public-web-resources/"} allows resources to be served
both from the web application root and from any JAR on the classpath that contains a
/META-INF/public-web-resources/ directory, with resources in the web application root taking precedence.

 For URL-based resources (e.g. files, HTTP URLs, etc) this method supports a special prefix
to indicate the charset associated with the URL so that relative paths appended to it can be
encoded correctly, e.g. [charset=Windows-31J]https://example.org/path.

Returns:
the same ResourceHandlerRegistration instance, for chained method invocation
*/