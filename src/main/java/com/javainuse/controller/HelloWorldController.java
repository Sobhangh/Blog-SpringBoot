package com.javainuse.controller;

import com.javainuse.model.JwtRequest;
import com.javainuse.model.Post;
import com.javainuse.model.PostDTO;
import com.javainuse.model.UserDTO;
import com.javainuse.service.PostDetailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * This is the main controller of the webapp.
 */
@RestController
public class HelloWorldController implements WebMvcConfigurer {

	@Autowired
	PostDetailService postDetailService;

	//Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

	//This is the main page
	@RequestMapping({ "/" })
	public ModelAndView firstPage() {
		ModelAndView mv = new ModelAndView("welcome");
		//The public posts are shown in the first page
		mv.addObject("posts",postDetailService.findPublicPosts());
		return mv;
		//return "hello";
	}


	@RequestMapping({ "/about" })
	public ModelAndView aboutPage() {
		ModelAndView mv = new ModelAndView("About");
		return mv;
	}


	@RequestMapping({ "/signup" })
	public ModelAndView SignUpPage() {
		ModelAndView mv = new ModelAndView("signup");
		mv.addObject("UserDTO", new UserDTO());
		return mv;
	}

	//This is the profile page where the posts of a user are displayed and the user can add a new post
	@RequestMapping({ "/profile" })
	public ModelAndView ProfilePage() {
		ModelAndView mv = new ModelAndView("profile");
		//The posts of the currently logged in user
		mv.addObject("posts", postDetailService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		return mv;
	}

	//The page for making a new post
	@RequestMapping({ "/newpost" })
	public ModelAndView PostPage() {
		ModelAndView mv = new ModelAndView("newPost");
		mv.addObject("post", new PostDTO());
		return mv;
	}

	//Page displaying a specific post determined with the request parameters of title and user
	@RequestMapping({ "/post" })
	public ModelAndView RetrivePost(@RequestParam String title, @RequestParam String user) {
		String authuser = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Post> p = postDetailService.findByUsernameAndTitle(user,title);
		if(p.isPresent()){
			if(p.get().isPrivatePost()){
				if(authuser!=user){
					ModelAndView mv = new ModelAndView("PrivateError");
				}
			}
			ModelAndView mv = new ModelAndView("post");
			mv.addObject("post", p.get());
			return mv;
		}
		else{
			return ProfilePage();
		}
	}

	//Page for editing a previous post determined by the request parameter id.
	@RequestMapping({ "/update" })
	public ModelAndView UpdatePost(@RequestParam Long id) {
		String authuser = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Post> p = postDetailService.findById(id);
		if(p.isPresent()){
			ModelAndView mv = new ModelAndView("update");
			mv.addObject("post", p.get());
			return mv;
		}
		else{
			return ProfilePage();
		}
	}

	//Receiving the updated post determined by its id
	@PostMapping({ "/update-post" })
	public ModelAndView postUpdate(PostDTO post, @RequestParam Long id) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Post> p = postDetailService.findById(id);
		if(p.isPresent()){
			postDetailService.update(post,p.get());
		}
		else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The to post to be updated not found");
		}
		return ProfilePage();
	}

	//Deleting the post specified by its id
	@RequestMapping({ "/delete" })
	public ModelAndView postDelete(@RequestParam Long id) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean p = postDetailService.delete(id);
		if(p){
		}
		else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The to post to be deleted not found");
		}
		return ProfilePage();
	}

	// Receiving the post submitted by the user in the page /newpost
	@PostMapping({ "/submit-post" })
	public ModelAndView SubmitPost(PostDTO post) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional p = postDetailService.findByUsernameAndTitle(user,post.getTitle());
		if(p.isPresent()){
			ModelAndView mv = new ModelAndView("newPost");
			mv.addObject("post", post);
			mv.addObject("repeated",true);
			return mv;
		}
		else{
			postDetailService.save(post,user);
			return ProfilePage();
		}
	}

}