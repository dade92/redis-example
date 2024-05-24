package webapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<UserResponse> retrieveUser(@RequestParam String key) {
        return ResponseEntity.ok(
            UserResponse.from(userCache.retrieve(key))
        );
    }

}
