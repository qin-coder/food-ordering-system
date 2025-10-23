package com.xuwei.service;

import com.xuwei.response.UserResponse;
import com.xuwei.model.User;

public interface UserService {
    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    UserResponse getUserProfileResponse(String jwt) throws Exception;
}
