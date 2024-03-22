package com.example.turboaz.controller;

import com.example.turboaz.enums.UserStatus;
import com.example.turboaz.model.ChangePasswordDTO;
import com.example.turboaz.model.UserDTO;
import com.example.turboaz.model.UserRegisterRequestDTO;
import com.example.turboaz.model.UserStatusDTO;
import com.example.turboaz.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/details")
    public UserDTO getUserDetails(HttpServletRequest request) {
        return userService.getUserDetails(request);
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(HttpServletRequest request, @ModelAttribute @Valid UserRegisterRequestDTO userRegisterRequestDTO) {
        userService.updateUser(request, userRegisterRequestDTO);
    }

    @PatchMapping("/change-password")
    public void changePassword(HttpServletRequest request, @RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        userService.changePassword(request, changePasswordDTO);
    }

    @GetMapping("/status")
    public List<UserDTO> getUserByStatus(@RequestParam UserStatus userStatus){
        return userService.getUserByStatus(userStatus);
    }

    @PatchMapping("/status/update")
    public void updateStatus(@Valid @RequestBody UserStatusDTO userStatusDTO){
        userService.updateStatus(userStatusDTO);
    }

    @GetMapping("/list")
    public List<UserDTO> getAllUser() {
        return userService.getAll();
    }
}