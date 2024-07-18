package com.javainuse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.javainuse.model.PostDTO;
import com.javainuse.model.UserDTO;
import com.javainuse.service.JwtUserDetailsService;
import com.javainuse.service.PostDetailService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SpringBootHelloWorldApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private static JwtUserDetailsService usersRepository;

    @Autowired
    private PostDetailService postDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        UserDTO user = new UserDTO();
        user.setUsername("user");
        user.setPassword("password");
        usersRepository.save(user);
    }

    @Test
    @WithMockUser(username="user",password="password")
    public void addingPost(){
        /**PostDTO post = new PostDTO();
        post.setText("hellooooooo");
        post.setTitle("test");
        post.setPrivatePost(true);*/
        try {
            //String s = objectMapper.writeValueAsString(post);
            mockMvc.perform(post("/submit-post")
                            .queryParam("title","test")
                            .queryParam("text","heloooo")
                            .queryParam("privatePost","on")
                            .with(csrf())
            );
            //System.out.print(postDetailService.findByUsername("user").size());
            assertEquals(postDetailService.findByUsername("user").size(),1);
            assertEquals(postDetailService.findByUsername("user").get(0).getTitle(),"test");
        }
        catch (Exception e){
            System.out.print(e);
            assert false;
        }

    }

    @Test
    @WithMockUser(username="user2",password="password")
    public void addingPost2() {
        /**PostDTO post = new PostDTO();
         post.setText("hellooooooo");
         post.setTitle("test");
         post.setPrivatePost(true);*/
        try {
            //String s = objectMapper.writeValueAsString(post);
            mockMvc.perform(post("/submit-post")
                    .queryParam("title", "test")
                    .queryParam("text", "heloooo")
                    .queryParam("privatePost", "on")
                    .with(csrf())
            );
            //System.out.print(postDetailService.findByUsername("user").size());
            assertEquals(postDetailService.findByUsername("user2").size(), 1);
            assertEquals(postDetailService.findByUsername("user2").get(0).getTitle(), "test");
        } catch (Exception e) {
            System.out.print(e);
            assert false;
        }

    }
}