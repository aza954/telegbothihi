package ru.aza954.telegrambothihi.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.aza954.telegrambothihi.model.User;
import ru.aza954.telegrambothihi.model.UserAuthority;
import ru.aza954.telegrambothihi.model.UserRole;
import ru.aza954.telegrambothihi.repository.UserRepository;
import ru.aza954.telegrambothihi.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        UserRole role = new UserRole();
        role.setUser(user);
        role.setUserAuthority(UserAuthority.USER);

        user.getUserRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public List<UserAuthority> getUserRoles(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return user.getUserRoles().stream()
                .map(UserRole::getUserAuthority)
                .toList();
    }

    @Override
    public void addRoleToUser(Long userId, UserAuthority role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        boolean hasRole = user.getUserRoles().stream()
                .anyMatch(r -> r.getUserAuthority().equals(role));
        if (hasRole) {
            throw new RuntimeException("У пользователя уже есть такая роль");
        }
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setUserAuthority(role);
        user.getUserRoles().add(userRole);
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(Long userId, UserAuthority role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        boolean removed = user.getUserRoles().removeIf(r -> r.getUserAuthority().equals(role));
        if (!removed) {
            throw new RuntimeException("У пользователя нет такой роли");
        }
        userRepository.save(user);
    }

    @Override
    public void replaceUserRole(Long userId, UserAuthority oldRole, UserAuthority newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        boolean removed = user.getUserRoles().removeIf(r -> r.getUserAuthority().equals(oldRole));
        if (!removed) {
            throw new RuntimeException("У пользователя нет роли для замены");
        }
        UserRole newUserRole = new UserRole();
        newUserRole.setUser(user);
        newUserRole.setUserAuthority(newRole);
        user.getUserRoles().add(newUserRole);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }
}


