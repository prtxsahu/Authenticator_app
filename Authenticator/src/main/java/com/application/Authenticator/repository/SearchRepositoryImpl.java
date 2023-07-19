package com.application.Authenticator.repository;

import com.application.Authenticator.model.Post;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class SearchRepositoryImpl implements SearchRepository{
    @Autowired
    MongoClient client;
    @Autowired
    MongoConverter converter;
    @Override
    public Post findByUserId(String userId){

        MongoDatabase database = client.getDatabase("auth2");
        MongoCollection<Document> collection = database.getCollection("information");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                new Document("text",
                        new Document("query", userId)
                                .append("path", "userId")))));


        List<Post> listOfPost=new ArrayList<Post>();
        result.forEach(doc->listOfPost.add(converter.read(Post.class,doc)));
        if(listOfPost.isEmpty()){
            Post defaultPost = new Post();
            return defaultPost;
        }

        else {
            return listOfPost.get(0);
        }
    }
}
