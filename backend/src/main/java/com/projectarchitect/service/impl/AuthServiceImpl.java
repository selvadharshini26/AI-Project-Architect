package com.projectarchitect.service.impl;

import com.projectarchitect.dto.request.LoginRequest;
import com.projectarchitect.dto.request.RegisterRequest;
import com.projectarchitect.dto.response.AuthResponse;
import com.projectarchitect.exception.BadRequestException;
import com.projectarchitect.model.Role;
import com.projectarchitect.model.User;
import com.projectarchitect.repository.UserRepository;
import com.projectarchitect.security.JwtUtil;
import com.projectarchitect.security.SecurityUser;
import com.projectarchitect.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AuthService} handling user registration and
 * login, including password encryption and JWT issuance.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email '" + request.getEmail() + "' is already registered");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.USER))
                .enabled(true)
                .build();

        User savedUser = userRepository.save(user);
        log.info("New user registered: {}", savedUser.getUsername());

        SecurityUser securityUser = SecurityUser.build(savedUser);
        String token = jwtUtil.generateToken(securityUser);

        return buildAuthResponse(token, securityUser);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        String token = jwtUtil.generateToken(securityUser);
        log.info("User logged in: {}", securityUser.getUsername());

        return buildAuthResponse(token, securityUser);
    }

    private AuthResponse buildAuthResponse(String token, SecurityUser securityUser) {
        Set<String> roles = securityUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(securityUser.getId())
                .username(securityUser.getUsername())
                .email(securityUser.getEmail())
                .roles(roles)
                .build();
    }
}
