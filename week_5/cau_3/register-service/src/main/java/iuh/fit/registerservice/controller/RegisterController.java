package iuh.fit.registerservice.controller;

import iuh.fit.registerservice.entity.User;
import iuh.fit.registerservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userRepository.save(user);
        return "Register success";
    }

    // API cho login gọi
    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
}