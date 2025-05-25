package ru.aza954.telegrambothihi.service;

import ru.aza954.telegrambothihi.model.User;
import ru.aza954.telegrambothihi.model.UserAuthority;

import java.util.List;

public interface UserService {
    User registerNewUser(String username, String rawPassword);

    List<UserAuthority> getUserRoles(Long userId);

    void addRoleToUser(Long userId, UserAuthority role);

    void removeRoleFromUser(Long userId, UserAuthority role);

    void replaceUserRole(Long userId, UserAuthority oldRole, UserAuthority newRole);
}
