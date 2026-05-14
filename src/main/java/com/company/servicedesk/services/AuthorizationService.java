package com.company.servicedesk.services;

import com.company.servicedesk.dtos.RegisterDTO;
import com.company.servicedesk.exceptions.UserAlreadyExistsException;
import com.company.servicedesk.exceptions.UserNotFoundException;
import com.company.servicedesk.models.UserModel;
import com.company.servicedesk.models.UserRole;
import com.company.servicedesk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    //this should be in another service, when program escalates do that
    @Transactional
    public void register(RegisterDTO data) {
        if(this.userRepository.findByLogin(data.userLogin()).isPresent()) {
            throw new UserAlreadyExistsException("Login já cadastrado");
        }

        UserModel newUser = new UserModel();
        newUser.setLogin(data.userLogin());
        newUser.setPassword(passwordEncoder.encode(data.password()));
        newUser.setDepartment(data.departament());
        newUser.setRole(UserRole.USER);

        userRepository.save(newUser);
    }
}
