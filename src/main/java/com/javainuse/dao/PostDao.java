package com.javainuse.dao;

import com.javainuse.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostDao extends CrudRepository<Post, Long> {

    Optional<Post> findByUsernameAndTitle(String username, String title);
    List<Post> findByUsername(String username);
    List<Post> findByPrivatePost(boolean privatePost);
}


