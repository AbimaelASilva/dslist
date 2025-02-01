package com.famdev.dslist.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.famdev.dslist.dto.AuthenticationDTO;
import com.famdev.dslist.dto.LoginResponseDTO;
import com.famdev.dslist.dto.RegisterDTO;
import com.famdev.dslist.entities.User;
import com.famdev.dslist.infra.security.JwtService;
import com.famdev.dslist.repositories.UserRepository;
import com.famdev.dslist.services.AuthenticationService;
import com.famdev.dslist.utils.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody RegisterDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO registerDTO) {

        if (userRepository.findByLogin(registerDTO.login()) != null)
            return ResponseEntity.badRequest().build();
        String encriptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = new User(registerDTO.login(), encriptedPassword,
                registerDTO.role());
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    // @PostMapping("/login")
    // public ResponseEntity login(@RequestBody @Validated AuthenticationDTO
    // authenticationDTO) {
    // var userNamePassword = new
    // UsernamePasswordAuthenticationToken(authenticationDTO.login(),
    // authenticationDTO.password());
    // var auth = this.authenticationManager.authenticate(userNamePassword);

    // var token = tokenService.generateToken((User) auth.getPrincipal());

    // return ResponseEntity.ok(new LoginResponseDTO(token) );
    // }

    // @PostMapping("/register")
    // public ResponseEntity register(@RequestBody @Validated RegisterDTO
    // registerDTO) {

    // if (userRepository.findByLogin(registerDTO.login()) != null)
    // return ResponseEntity.badRequest().build();
    // String encriptedPassword = new
    // BCryptPasswordEncoder().encode(registerDTO.password());
    // User newUser = new User(registerDTO.login(), encriptedPassword,
    // registerDTO.role());
    // userRepository.save(newUser);
    // return ResponseEntity.ok().build();
    // }

}
