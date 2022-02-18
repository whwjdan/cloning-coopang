package com.clone.coopang.controller;

import com.clone.coopang.network.request.UserApiRequest;
import com.clone.coopang.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    private UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/user/join")
    public void save(@RequestBody UserApiRequest userApiRequest){
        userService.createUser(userApiRequest);
    }

}
