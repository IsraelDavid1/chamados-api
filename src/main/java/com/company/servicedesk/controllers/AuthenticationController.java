package com.company.servicedesk.controllers;

import com.company.servicedesk.dtos.AuthenticationDTO;
import com.company.servicedesk.dtos.LoginResponseDTO;
import com.company.servicedesk.dtos.RegisterDTO;
import com.company.servicedesk.dtos.UserResponseDTO;
import com.company.servicedesk.infra.security.TokenService;
import com.company.servicedesk.models.UserModel;
import com.company.servicedesk.services.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;
    private  final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.userLogin(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) Objects.requireNonNull(auth.getPrincipal()));

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        authorizationService.register(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/validate")
    public ResponseEntity<UserResponseDTO> validateSession(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.ok(new UserResponseDTO(
                user.getId(), user.getLogin(), user.getDepartment(), user.getRole()
        ));
    }
}
