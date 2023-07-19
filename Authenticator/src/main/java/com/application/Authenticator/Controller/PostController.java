package com.application.Authenticator.Controller;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.application.Authenticator.model.Post;
import com.application.Authenticator.repository.PostRepository;
import com.application.Authenticator.repository.SearchRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostRepository repo;
    @Autowired
    SearchRepository srepo;

//    @GetMapping("/allUsers")
//    public List<Post> getAllUsers(){
//            return repo.findAll();
//    }
//    @GetMapping("/getpass/{userId}")
//    public Post getGivenUser(@PathVariable String userId){
//       return getUser(userId);
//    }

    @PostMapping("/POST/signup")
    public Post signupPost(@RequestBody Post post)
    {
        return repo.save(post);
    }


    @GetMapping("GET/users/{userId}")

    public ResponseEntity<Post> getUser(@PathVariable String userId, @RequestHeader("Authorization") String authorizationHeader) // mine
    {

        if (authenticateUser(authorizationHeader, userId)) {
            return ResponseEntity.ok(getUser(userId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    private boolean authenticateUser(String authorizationHeader, String userId) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length());
           // String credentials = new String(Base64Utils.decodeFromString(base64Credentials));
            String credentials =  new String(Base64.decodeBase64(base64Credentials));

            // Split the credentials into username and password
            String[] splitCredentials = credentials.split(":", 2);
            String username = splitCredentials[0];
            String password = splitCredentials[1];

            // Compare the provided username and password with the stored values
            // Return true if authentication is successful, otherwise false
            return username.equals(userId) && password.equals(getUserPassword(userId));
        }

        return false;
    }
    private String getUserPassword(String userId) {

       Post UserInfo = getUser(userId);

       return UserInfo.getPassword();
    }

    private Post getUser(String userId){

        Post UserInfo = srepo.findByUserId(userId);
        return UserInfo;
    }



}
