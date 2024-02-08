package com.pisarevAS.familybudget.user.service;

import com.pisarevAS.familybudget.exception.NotFoundException;
import com.pisarevAS.familybudget.user.model.Role;
import com.pisarevAS.familybudget.user.model.User;
import com.pisarevAS.familybudget.user.model.UserDto;
import com.pisarevAS.familybudget.user.model.UserMapper;
import com.pisarevAS.familybudget.user.repository.RoleRepository;
import com.pisarevAS.familybudget.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean create(UserDto userDto) {
        Role role = roleRepository.findRoleByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("Role not found"));
        UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto, role)));
        return true;
    }

    @Override
    public void update(String name, String password) {
        User user = getByLogin();

        if (!name.isBlank()) {
            user.setName(name);
        }

        if (!password.isBlank()) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }

        userRepository.save(user);
    }

    @Override
    public void delete() {
        User user = getByLogin();
        userRepository.deleteUserByLogin(user.getLogin());
    }

    @Override
    public User getByLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepository.findUserByLogin(currentPrincipalName)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}