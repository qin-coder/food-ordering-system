package com.xuwei.service.impl;

import com.xuwei.config.JwtProvider;
import com.xuwei.dto.FavoriteRestaurantDTO;
import com.xuwei.response.UserResponse;
import com.xuwei.model.User;
import com.xuwei.repository.UserRepository;
import com.xuwei.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public UserResponse getUserProfileResponse(String jwt) throws Exception {
        User user = findUserByJwtToken(jwt);

        java.util.List<FavoriteRestaurantDTO> simplifiedFavorites = user.getFavorites().stream()
                .map(restaurant -> new FavoriteRestaurantDTO(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getDescription(),
                        restaurant.getCuisineType(),
                        restaurant.getAddress()
                ))
                .collect(Collectors.toList());
        String roleString = user.getRole().name();
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                simplifiedFavorites,
                user.getAddresses()
        );
    }

}
