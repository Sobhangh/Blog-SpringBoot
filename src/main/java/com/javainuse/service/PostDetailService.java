package com.javainuse.service;

import com.javainuse.dao.PostDao;
import com.javainuse.model.DAOUser;
import com.javainuse.model.Post;
import com.javainuse.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostDetailService{
    @Autowired
    PostDao postDao;

    public List<Post> findByUsername(String username){
        return postDao.findByUsername(username);
    }

    public Optional<Post> findByUsernameAndTitle(String username, String title){
        return postDao.findByUsernameAndTitle(username,title);
    }

    public List<Post> findPublicPosts(){
        return postDao.findByPrivatePost(false);
    }

    public Optional<Post> findById(Long id){
        return postDao.findById(id);
    }

    public Post save(PostDTO post, String username){
        Post p = new Post();
        p.setUser(username);
        p.setTitle(post.getTitle());
        p.setText(post.getText());
        p.setPrivatePost(post.isPrivatePost());
        p.setCreatedate(LocalDateTime.now());
        p.setModifydate(LocalDateTime.now());
        return postDao.save(p);
    }

    public void update(PostDTO upost, Post post){
        post.setTitle(upost.getTitle());
        post.setText(upost.getText());
        post.setPrivatePost(upost.isPrivatePost());
        post.setModifydate(LocalDateTime.now());
        postDao.save(post);
    }

    public boolean delete(Long id){
        Optional<Post> p = postDao.findById(id);
        if(p.isPresent()){
            postDao.delete(p.get());
            return true;
        }
        else{
            return false;
        }
    }

}
