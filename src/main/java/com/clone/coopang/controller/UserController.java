package com.clone.coopang.controller;

import com.clone.coopang.network.request.UserApiRequest;
import com.clone.coopang.network.response.UserApiResponse;
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
    public ResponseEntity<Void> checkDuplicateEmail(@PathVariable String email){
        boolean duplicateEmail = userService.isExistsEmail(email);
        if (duplicateEmail) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/user/join")
    public ResponseEntity<UserApiResponse> save(@RequestBody UserApiRequest userApiRequest){

        UserApiResponse response = userService.createUser(userApiRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
