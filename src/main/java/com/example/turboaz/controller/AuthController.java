package com.example.turboaz.controller;

import com.example.turboaz.model.AuthRequestDTO;
import com.example.turboaz.model.AuthenticationDTO;
import com.example.turboaz.model.UserRegisterRequestDTO;
import com.example.turboaz.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserRegisterRequestDTO requestDto) {
        authService.register(requestDto);
    }

    @PostMapping("/login")
    public AuthenticationDTO login(@RequestBody @Valid AuthRequestDTO authRequestDto) {
        return authService.authenticate(authRequestDto);
    }

    @DeleteMapping("/delete/{username}")
    public void delete(@PathVariable String username){
        authService.deleteUser(username);
    }
}