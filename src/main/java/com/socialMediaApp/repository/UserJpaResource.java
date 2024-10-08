package com.socialMediaApp.repository;

import com.socialMediaApp.customException.UserNotFoundException;
import com.socialMediaApp.dao.UserDaoService;
import com.socialMediaApp.entity.Post;
import com.socialMediaApp.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserJpaResource {
    private UserDaoService service;



//    public UserJpaResource() {
//    }

    //@Autowired
    private UserRepository repository;
   // @Autowired
    private PostRepository postRepository;

//    public UserJpaResource(UserRepository repository, PostRepository postRepository) {
//        this.repository = repository;
//        this.postRepository = postRepository;
//    }

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers()
    {
        return repository.findAll();
    }

    @GetMapping("/jpa/user/{id}")
    public EntityModel<User> retriveUser(@PathVariable int id)
    {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id : "+id);

        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;
    }
    @PostMapping("/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user)
    {
        User savedUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        repository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPostOfUser(@PathVariable int id)
    {
       Optional<User> user = repository.findById(id);
       if(user.isEmpty())
       {
           throw new UserNotFoundException(id+"id");
       }
       return user.get().getPosts();
    }

    @PostMapping ("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post)
    {
        User user = repository.findById(id).orElseThrow(()->new UserNotFoundException("id "+id));
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();

    }


}
