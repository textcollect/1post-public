package com.gen.onepost.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	// Use dependency injection method to inject the DataSource (interface) object
	//into this WebSecurityConfig class so tt we can use the properties/methods
	//from the DataSource object

	// Purpose: to make use of the details we put in the application.properties
	//to be able to make a connection to our MySQL server and access our Schema
	@Autowired
	private DataSource dataSource;

//	@Bean
//	public JdbcUserDetailsManager jdbcUserDetailsManager() {
//		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//		jdbcUserDetailsManager.setDataSource(dataSource);
//
//		return jdbcUserDetailsManager;
//	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// When a user is authenticated, Spring Security object will create a user session (backend)
		// Spring Security object will also be responsible to manage the session (e.g. timeout, error)
		// Spring Security object will need to end the user session if logout/timeout

		// in sql query, get info from the frontend through the ? symbol
		// sending of info from frontend is through thymeleaf

		// usersByUsernameQuery is sql query method provided by AuthenticationManagerBuilder
		// the query used here gets the row tt matches what the frontend send
		// usersByUsernameQuery will check that the password matches and enabled=1
		// authoritiesByUsernameQuery - retrieve role of this user
		auth.jdbcAuthentication()
				.passwordEncoder(new BCryptPasswordEncoder())
				.dataSource(dataSource)
				.usersByUsernameQuery("SELECT username, password, enabled FROM Users WHERE username=?")
				.authoritiesByUsernameQuery("SELECT username, role FROM Users WHERE username=?");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// After authentication is done and user is logged in and session is active
		//need to setup the security policy for the http pages the user is able to access
//		http.csrf((csrf) -> csrf.disable());
		http
				.formLogin((formLogin) -> formLogin
						.usernameParameter("username")
						.passwordParameter("password")
						.loginPage("/login")
						.defaultSuccessUrl("/index", true)
				)
//		NOTE see below for explanation on disabling csrf
				.csrf((csrf) -> csrf.disable())
				.logout((logout) -> logout
						.permitAll()
						.logoutSuccessUrl("/index"));

		// The requiresChannel().anyRequest().requiresSecure() configuration ensures that
		//all requests must be served over a secure (HTTPS) connection.
//		http.requiresChannel().anyRequest().requiresSecure();


		// Specify which pages to allow users to access w/o logging in
		//and which pages users with "ADMIN" role can access
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/index", "/login", "/postlist", "/showPostDetail", "/about", "/help",
						"/images/**", "/js/**", "/css/**",
						"/post/allpost", "/reply/thisPostReply/**", "/users/allusers", "/registerNewPerson", "/users/add").permitAll()
				.requestMatchers("/postForm/**", "/replyForm/**", "/reply/add", "/post/add").hasAnyRole("ADMIN", "USER")
//				.requestMatchers("/registerNewPerson", "/users/add").hasRole("REGISTER")
		);

		return http.build();
	}
}


/*
CSRF is needed when you have web forms submissions which are prone to "cross site requests"
within the same browser's other tabs.
These applications typically generate entire HTML on server side using template engines (like velocity, JSF, thymeleaf etc).

Modern applications however relies mostly on REST API endpoints
(instead of traditional controllers which used to emit HTML).
These endpoints are designed to consume and generate mostly JSON.

The intended consumer of these APIs are either mobile apps, web frameworks (like reactjs, angularjs or alike)
or other b2b applications.

These APIs are mostly stateless and DO NOT rely on server side sessions or browser cookies.

As per CSRF explanation, one of the condition is no longer relevant (Cookie-based session handling)
thus these APIs are not prone to CSRF attacks.

This is the primary reason why most of the modern apps (which exposes APIs only) disable CSRF for these endpoints.
(https://stackoverflow.com/questions/62696806/reason-to-disable-csrf-in-spring-boot)

CSRF protection checks for a CSRF token on changing methods like POST, PUT, DELETE.
And as a REST API is stateless you don't have a token in a cookie. That's why you have to disable it for REST APIs.
(https://stackoverflow.com/questions/61915052/spring-boot-restcontroller-delete-request-fails-without-csrf-disable)
*/
