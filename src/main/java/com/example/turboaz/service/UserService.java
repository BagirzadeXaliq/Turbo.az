package com.example.turboaz.service;

import com.example.turboaz.enums.UserStatus;
import com.example.turboaz.model.ChangePasswordDTO;
import com.example.turboaz.model.UserDTO;
import com.example.turboaz.model.UserRegisterRequestDTO;
import com.example.turboaz.model.UserStatusDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    UserDTO getUserDetails(HttpServletRequest request);

    List<UserDTO> getAll();

    void updateStatus(UserStatusDTO userStatusDTO);

    List<UserDTO> getUserByStatus(UserStatus userStatus);

    void updateUser(HttpServletRequest request, UserRegisterRequestDTO userRegisterRequestDTO);

    void changePassword(HttpServletRequest request, ChangePasswordDTO changePasswordDTO);

}