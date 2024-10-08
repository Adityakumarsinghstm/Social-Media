package com.socialMediaApp.repository;

import com.socialMediaApp.entity.Post;
import com.socialMediaApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
