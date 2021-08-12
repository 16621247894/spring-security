package com.zwq.cloud.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configurable
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
}