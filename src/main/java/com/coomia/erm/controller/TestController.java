package com.coomia.erm.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.model.UserContext;

@RestController
@RequestMapping(value="/erm/api/test")
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class TestController {
	
	@RequestMapping(value="/1")
	@PreAuthorize("hasAnyRole('EB1')")
	public String test() {
		return "test";
	}
	
	@RequestMapping(value="/3")
	@PreAuthorize("hasAnyRole('EB')")
	public String test3() {
		return "test3";
	}
	
	@RequestMapping(value="/2")
	public UserContext getCurrentUser() {
		UserContext userInfo = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null && null != securityContext.getAuthentication()) {
            Object principal = securityContext.getAuthentication().getPrincipal();
            if (UserContext.class.isAssignableFrom(principal.getClass())) {
                userInfo = (UserContext) principal;
            }
        }
        return userInfo;
    }
	
}
