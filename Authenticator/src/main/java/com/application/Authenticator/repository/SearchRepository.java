package com.application.Authenticator.repository;

import com.application.Authenticator.model.Post;

public interface SearchRepository {

    public Post findByUserId(String userId);
}
