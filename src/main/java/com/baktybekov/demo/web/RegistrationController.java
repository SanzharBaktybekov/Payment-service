package com.baktybekov.demo.web;

import com.baktybekov.demo.model.User;
import com.baktybekov.demo.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegistrationController {
    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final UserDetailsService userDetailsService;
    public RegistrationController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        UserService userService = (UserService) userDetailsService;
        userService.registerNewUser(user);
        logger.info("New user saved: " + user.getUsername());
        return ResponseEntity.ok(user.getUsername());
    }
}