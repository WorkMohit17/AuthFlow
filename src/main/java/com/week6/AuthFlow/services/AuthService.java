package com.week6.AuthFlow.services;

import com.week6.AuthFlow.dtos.LoginDTO;
import com.week6.AuthFlow.dtos.LoginResponseDTO;
import com.week6.AuthFlow.dtos.SignUpDTO;
import com.week6.AuthFlow.dtos.SignUpResponseDTO;
import com.week6.AuthFlow.entities.UserEntity;
import com.week6.AuthFlow.enums.Subscription;
import com.week6.AuthFlow.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    private final UserRepository userRepository;

    @Transactional
    public SignUpResponseDTO signUp(SignUpDTO request){
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if(user != null)
            throw new IllegalArgumentException("User with email " + request.getEmail() + " already exists.");
        UserEntity toSave = mapper.map(request, UserEntity.class);
        toSave.setPassword(passwordEncoder.encode(request.getPassword()));
        toSave.setSubscriptions(new HashSet<>());
        toSave.getSubscriptions().add(Subscription.FREE);
        UserEntity saved = userRepository.save(toSave);
        return mapper.map(saved, SignUpResponseDTO.class);
    }

    public LoginResponseDTO login(LoginDTO request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        sessionService.generateNewSession(user, refreshToken);

        return LoginResponseDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void logout(String refreshToken){
        sessionService.deleteSessionByTRefreshToken(refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);

        UserEntity user = userService.getUserById(userId);
        String accessToken = jwtService.generateAccessToken(user);
        return LoginResponseDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void addSubscription(Long userId, Subscription subscription){
        UserEntity user = userService.getUserById(userId);
        user.getSubscriptions().add(subscription);
        userRepository.save(user);
    }

    @Transactional
    public void removeSubscription(Long userId, Subscription subscription){
        UserEntity user = userService.getUserById(userId);
        if(subscription == Subscription.FREE)
            throw new IllegalArgumentException("FREE subscription cannot be removed.");
        user.getSubscriptions().remove(subscription);
        userRepository.save(user);
    }
}
