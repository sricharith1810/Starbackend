package com.example.Star.authentication.service;

import com.example.Star.authentication.dto.UserDTO;
import com.example.Star.authentication.model.User;
import com.example.Star.authentication.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<UserDTO> getUserDTOByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserDTO(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhonenumber(),
                        user.getAddress(),
                        user.getState(),
                        user.getDob(),
                        user.getCity(),
                        user.getPostalcode()
                ));
    }
}
