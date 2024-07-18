package com.javainuse.config;

import com.javainuse.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	//@Autowired
	//private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	//@Autowired
	//private JwtRequestFilter jwtRequestFilter;

	/**@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}**/

	/**@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(jwtUserDetailsService.getBcryptEncoder());
	}**/

	/**
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/authenticate", "/register","/","/login", "/signup","/js/JWT.js","/static/js/JWT.js").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}**/

	//@Override
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {  // (2)
		http.authorizeHttpRequests((request)-> request
				.requestMatchers( "/", "/signup","/js/JWT.js","/static/js/JWT.js","/login*","/post","/js/header.js","/css/general.css","/about").permitAll() // (3)
						.requestMatchers("/register").permitAll()
						.anyRequest().authenticated())// (4)
				.formLogin(formLogin ->
				formLogin.permitAll()
				)
				//.formLogin().permitAll().and()
				//.formLogin() // (5)
				//.loginPage("/login") // (5)
				//.permitAll()
				//.and()
				.logout(logout -> logout.logoutSuccessUrl("/"));
				//.logout() // (6)
				//.logoutSuccessUrl("/")
				//.deleteCookies("JSESSIONID")
				//.invalidateHttpSession(true)
				//.permitAll();
				//.and()
				//.httpBasic(); // (7)
		return http.build();
	}
}