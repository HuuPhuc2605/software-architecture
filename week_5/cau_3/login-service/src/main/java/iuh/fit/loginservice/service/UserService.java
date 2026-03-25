package iuh.fit.loginservice.service;

import iuh.fit.loginservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    public User getUserFromRegister(String username) {
        String url = "http://localhost:8082/user/" + username;
        return restTemplate.getForObject(url, User.class);
    }
}