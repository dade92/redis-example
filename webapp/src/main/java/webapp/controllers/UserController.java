package webapp.controllers;

import data.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.UserCache;

@RestController
public class UserController {

    private final UserCache userCache;

    public UserController(UserCache userCache) {
        this.userCache = userCache;
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> retrieveUser(@RequestParam String name) {
        User user = userCache.retrieve(name);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(
                UserResponse.from(user)
            );
        }
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
        userCache.add(new User(userRequest.name()));
        return ResponseEntity.noContent().build();
    }

}
