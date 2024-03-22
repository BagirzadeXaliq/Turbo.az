package com.example.turboaz.service;

import com.example.turboaz.dao.UserEntity;
import com.example.turboaz.dao.UserRepository;
import com.example.turboaz.enums.UserStatus;
import com.example.turboaz.exception.PasswordWrongException;
import com.example.turboaz.exception.UserNotFoundException;
import com.example.turboaz.mapper.UserMapper;
import com.example.turboaz.model.ChangePasswordDTO;
import com.example.turboaz.model.UserDTO;
import com.example.turboaz.model.UserRegisterRequestDTO;
import com.example.turboaz.model.UserStatusDTO;
import com.example.turboaz.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDTO getUserDetails(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        log.debug("Getting user by id: {}", userId);
        UserDTO userDTO = userRepository.findUserByUsername(userId.toString())
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new UserNotFoundException("User not found with id: " + userId);
                });
        log.debug("User found: {}", userDTO);
        return userDTO;
    }

    public List<UserDTO> getAll() {
        log.debug("Getting all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public void updateStatus(UserStatusDTO userStatusDTO) {
        Long userId = Long.valueOf(userStatusDTO.getId());
        log.debug("Updating status for user with id: {}", userId);
        UserEntity user = getUserEntityById(userId);
        user.setStatus(userStatusDTO.getUserStatus());
        userRepository.save(user);
        log.debug("Status updated successfully for user with id: {}", userId);
    }

    public List<UserDTO> getUserByStatus(UserStatus userStatus) {
        log.debug("Getting users by status: {}", userStatus);
        return userRepository.findByStatus(userStatus)
                .orElseThrow(() -> {
                    log.error("No users found with status: {}", userStatus);
                    return new UserNotFoundException("No users found with status: " + userStatus);
                })
                .stream()
                .map(userMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    public void updateUser(HttpServletRequest request, UserRegisterRequestDTO userRegisterRequestDTO) {
        Long userId = getUserIdFromRequest(request);
        log.debug("Updating user with id: {}", userId);
        UserEntity user = getUserEntityByUsername(userId.toString());
        userRegisterRequestDTO.setPassword(user.getPassword());
        userRepository.save(userMapper.mapRegisterRequestDtoToEntity(userRegisterRequestDTO));
        log.debug("User updated successfully with id: {}", userId);
    }

    public void changePassword(HttpServletRequest request, ChangePasswordDTO changePasswordDTO) {
        Long userId = getUserIdFromRequest(request);
        log.debug("Changing password for user with id: {}", userId);
        UserEntity user = getUserEntityByUsername(userId.toString());
        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            log.error("Current password is incorrect for user with id: {}", userId);
            throw new PasswordWrongException("Current password is incorrect.");
        }
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getRetryPassword())) {
            log.error("New passwords do not match for user with id: {}", userId);
            throw new PasswordWrongException("New passwords do not match.");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
        log.debug("Password changed successfully for user with id: {}", userId);
    }

    private Long getUserIdFromRequest(HttpServletRequest request) {
        return Long.valueOf(jwtUtil.getUserId(jwtUtil.resolveClaims(request)));
    }

    private UserEntity getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new UserNotFoundException("User not found with id: " + userId);
                });
    }

    private UserEntity getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new UserNotFoundException("User not found with username: " + username);
                });
    }

}