/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd. All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold. Use or reproduction of this software by any unauthorized
 * individual or entity is strictly prohibited. This software is the confidential and proprietary
 * information of Coomia Network Technology Co., Ltd. Disclosure of such confidential information
 * and shall use it only in accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Coomia Network
 * Technology Co., Ltd. SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coomia.erm.common.auth.ajax.AjaxAuthenticationProvider;
import com.coomia.erm.common.auth.ajax.AjaxLoginProcessingFilter;
import com.coomia.erm.common.auth.ajax.CoomiaLogoutHandler;
import com.coomia.erm.common.auth.jwt.JwtAuthenticationProvider;
import com.coomia.erm.common.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.coomia.erm.common.auth.jwt.SkipPathRequestMatcher;
import com.coomia.erm.common.auth.jwt.extractor.TokenExtractor;
import com.coomia.erm.controller.endpoint.oauth.RestAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * WebSecurityConfig
 * 
 * @author spancer.ray
 *
 *         Aug 3, 2016
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
	public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/erm/api/auth/login";
	public static final String FORM_BASED_LOGOUT_ENTRY_POINT = "/erm/api/auth/logout";
	public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/erm/api/**";
	public static final String TOKEN_REFRESH_ENTRY_POINT = "/erm/api/auth/token";

	@Autowired
	private RestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	@Autowired
	private AjaxAuthenticationProvider ajaxAuthenticationProvider;
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private TokenExtractor tokenExtractor;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper objectMapper;

	protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
		AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler,
				failureHandler, objectMapper);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
		List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT);
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
		JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(failureHandler,
				tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean(name = "coomiaLogoutHandler")
	public CoomiaLogoutHandler logoutHandler() {
		return new CoomiaLogoutHandler();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(ajaxAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers("/erm/api/ermFile/uploadFileWithoutLogin", "/erm/api/ermFile/img/**",
				"/erm/api/ermH5StudentInfo/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
				"/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // We don't need CSRF for JWT based authentication
				.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests().antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
				.antMatchers(FORM_BASED_LOGOUT_ENTRY_POINT).permitAll() // Logout end-point
				.antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
				.antMatchers("/public/**").permitAll().antMatchers("/console").permitAll() // H2 Console Dash-board -
																							// only for testing
				.and().logout().logoutUrl(FORM_BASED_LOGOUT_ENTRY_POINT).addLogoutHandler(logoutHandler())
				.logoutSuccessUrl("/").permitAll()

				.and().authorizeRequests().antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API
																										// End-points
				.and().addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(),
						UsernamePasswordAuthenticationFilter.class);
	}
}
