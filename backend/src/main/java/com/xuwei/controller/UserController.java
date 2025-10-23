package com.xuwei.controller;

import com.xuwei.response.UserResponse;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        UserResponse userResponse = userService.getUserProfileResponse(jwt);
        return ResponseEntity.ok(userResponse);
    }

}
