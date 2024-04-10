package com.example.turboaz.service.auth;

import com.example.turboaz.dao.entity.UserEntity;
import com.example.turboaz.dao.repository.UserRepository;
import com.example.turboaz.exception.EntityExistException;
import com.example.turboaz.exception.NotFoundException;
import com.example.turboaz.mapper.UserMapper;
import com.example.turboaz.model.AuthRequestDTO;
import com.example.turboaz.model.AuthenticationDTO;
import com.example.turboaz.model.UserRegisterRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserMapper userMapper;

    @Transactional
    public void register(UserRegisterRequestDTO requestDTO){
        log.info("Registering user: {}", requestDTO.getUsername());
        Optional<UserEntity> optionalUser = userRepository.findUserByUsername(requestDTO.getUsername());
        if (optionalUser.isPresent()){
            throw new EntityExistException("USERNAME_ALREADY_EXIST");
        }
        var user = UserRegisterRequestDTO
                .builder()
                .username(requestDTO.getUsername())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .roles(requestDTO.getRoles())
                .build();
        userRepository.save(userMapper.mapRegisterRequestDtoToEntity(user));
        log.info("User registered successfully: {}", requestDTO.getUsername());
    }

    public AuthenticationDTO authenticate(AuthRequestDTO authRequestDTO){
        log.info("Authenticating user: {}", authRequestDTO.getUsername());
        UserEntity user = userRepository.findUserByUsername(authRequestDTO.getUsername())
                .orElseThrow(
                        () -> new BadCredentialsException("INVALID_USERNAME_OR_PASSWORD")
                );
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDTO.getUsername(),
                            authRequestDTO.getPassword()
                    )
            );
            var jwtToken = jwtService.generateToken(user);
            log.info("User authenticated successfully: {}", user.getUsername());
            return AuthenticationDTO
                    .builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e){
            log.error("Authentication failed for user {}: {}", authRequestDTO.getUsername(), e.getMessage());
            throw new BadCredentialsException("INVALID_USERNAME_OR_PASSWORD");
        }
    }

    @Transactional
    public void deleteUser(String username) {
        log.info("Deleting user: {}", username);
        var user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("USER_NOT_FOUND")
        );
        user.getRoles().clear();
        userRepository.deleteById(user.getId());
        log.info("User deleted successfully: {}", username);
    }

}