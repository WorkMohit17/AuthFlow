package com.week6.AuthFlow.controllers;

import com.week6.AuthFlow.dtos.LoginDTO;
import com.week6.AuthFlow.dtos.LoginResponseDTO;
import com.week6.AuthFlow.dtos.SignUpDTO;
import com.week6.AuthFlow.dtos.SignUpResponseDTO;
import com.week6.AuthFlow.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthContoller {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signup(@RequestBody SignUpDTO request){
        SignUpResponseDTO response = authService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request, HttpServletResponse response){
        LoginResponseDTO responseDTO = authService.login(request);
        Cookie cookie = new Cookie(
                "refreshToken", responseDTO.getRefreshToken()
        );
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new AuthenticationServiceException("Refresh token not found");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(()-> new AuthenticationServiceException("Refresh token not inside cookie"));

        LoginResponseDTO response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged Out Successfully");
    }

}
