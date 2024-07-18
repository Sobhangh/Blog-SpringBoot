package com.javainuse.controller;

import java.util.Objects;

import com.javainuse.model.DAOUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.javainuse.service.JwtUserDetailsService;


import com.javainuse.config.JwtTokenUtil;
import com.javainuse.model.JwtRequest;
import com.javainuse.model.JwtResponse;
import com.javainuse.model.UserDTO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@RestController
@CrossOrigin
public class JwtAuthenticationController implements WebMvcConfigurer {

	//@Autowired
	//private AuthenticationManager authenticationManager;
	Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ModelAndView createAuthenticationToken(JwtRequest authenticationRequest , Model m) throws Exception {

		//authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		m.addAttribute("jwtToken",new JwtResponse(token));
		//res.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
		ModelAndView mv = new ModelAndView("welcome");
		mv.addObject("success",1);
		//mv.addObject("JwtRequest", new JwtRequest());
		mv.addObject("jwtToken",new JwtResponse(token));
		return mv;
		//return "index";

		//return ResponseEntity.ok(new JwtResponse(token));
	}

	//Endpoint for registering a new user
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute UserDTO user) throws Exception {
		logger.trace("registration...............");
		ModelAndView mv = new ModelAndView("signup");
		DAOUser u = userDetailsService.getUserDao().findByUsername(user.getUsername());
		if(u==null){
			userDetailsService.save(user);
			mv.addObject("success",1);
		}
		else{
			mv.addObject("success",-1);
		}
		mv.addObject("UserDTO", new UserDTO());
		return mv;
		//return ResponseEntity.ok(userDetailsService.save(user));
	}

	/**private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}**/
}