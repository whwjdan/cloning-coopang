package com.clone.coopang.controller;

import com.clone.coopang.network.request.SignUpRequest;
import com.clone.coopang.network.response.SignUpResponse;
import com.clone.coopang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/exists/{email}")
    public ResponseEntity<Void> checkDuplicateEmail(@PathVariable java.lang.String email){
        userService.verifyEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/join")
    public ResponseEntity<SignUpResponse> save(@RequestBody SignUpRequest signUpRequest){
        SignUpResponse response = userService.saveUser(signUpRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}