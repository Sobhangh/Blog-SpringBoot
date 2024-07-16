package com.javainuse.controller;

import com.javainuse.model.JwtRequest;
import com.javainuse.model.Post;
import com.javainuse.model.PostDTO;
import com.javainuse.model.UserDTO;
import com.javainuse.service.PostDetailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class HelloWorldController implements WebMvcConfigurer {

	@Autowired
	PostDetailService postDetailService;

	//Logger logger = LoggerFactory.getLogger(LoggingController.class);

	@RequestMapping({ "/" })
	public ModelAndView firstPage() {
		ModelAndView mv = new ModelAndView("welcome");
		mv.addObject("posts",postDetailService.findPublicPosts());
		return mv;
		//return "hello";
	}

	/**@RequestMapping({ "/login" })
	public ModelAndView LoginPage() {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("JwtRequest", new JwtRequest());
		return mv;
	}**/

	@RequestMapping({ "/signup" })
	public ModelAndView SignUpPage() {
		ModelAndView mv = new ModelAndView("signup");
		mv.addObject("UserDTO", new UserDTO());
		return mv;
	}

	@RequestMapping({ "/profile" })
	public ModelAndView ProfilePage() {
		ModelAndView mv = new ModelAndView("profile");
		mv.addObject("posts", postDetailService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		return mv;
	}

	@RequestMapping({ "/newpost" })
	public ModelAndView PostPage() {
		ModelAndView mv = new ModelAndView("newPost");
		mv.addObject("post", new PostDTO());
		return mv;
	}

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
			//TO DO: Return error...
			return ProfilePage();
		}
	}

	@PostMapping({ "/update-post" })
	public ModelAndView postUpdate(PostDTO post, @RequestParam Long id) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Post> p = postDetailService.findById(id);
		if(p.isPresent()){
			postDetailService.update(post,p.get());
			//TO DO: REturn confirmation
		}
		else{
			//TO DO: Return error...

		}
		return ProfilePage();
	}

	@RequestMapping({ "/delete" })
	public ModelAndView postDelete(@RequestParam Long id) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean p = postDetailService.delete(id);
		if(p){
			//TO DO: REturn confirmation
		}
		else{
			//TO DO: Return error...

		}
		return ProfilePage();
	}

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