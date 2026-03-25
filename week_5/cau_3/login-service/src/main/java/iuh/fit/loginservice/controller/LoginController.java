package iuh.fit.loginservice.controller;

import iuh.fit.loginservice.entity.User;
import iuh.fit.loginservice.repository.UserRepository;
import iuh.fit.loginservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User u = userService.getUserFromRegister(user.getUsername());

        if (u != null && u.getPassword().equals(user.getPassword())) {
            return "Login success";
        }

        return "Login fail";
    }
}