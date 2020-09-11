package com.falvarez.marketplace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.falvarez.marketplace.exception.RestAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Bean
	public RestAccessDeniedHandler accessDeniedHandler() {
		return new RestAccessDeniedHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests()
				.antMatchers("/api/users/signin").permitAll()
				.antMatchers("/api/users/signup").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.antMatchers("/h2-console/**/**").permitAll().anyRequest().authenticated();

		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
		.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement();

		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs")
				.antMatchers("/swagger-resources/**")
				.antMatchers("/swagger-ui.html")
				.antMatchers("/actuator/**")
				.and().ignoring().antMatchers("/h2-console/**/**");
		;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
