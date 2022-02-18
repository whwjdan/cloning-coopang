package com.clone.coopang.controller;

import com.clone.coopang.network.request.UserApiRequest;
import com.clone.coopang.network.response.UserApiResponse;
import com.clone.coopang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/join")
    public ResponseEntity<String> save(@RequestBody UserApiRequest userApiRequest){

        UserApiResponse response = userService.createUser(userApiRequest);
        return new ResponseEntity<>(response.getPhoneNumber(), HttpStatus.OK);
    }

}
