package com.application.Authenticator.repository;

import com.application.Authenticator.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post,String> {

}
