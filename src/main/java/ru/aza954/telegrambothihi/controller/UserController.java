package ru.aza954.telegrambothihi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.aza954.telegrambothihi.model.UserAuthority;
import ru.aza954.telegrambothihi.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/roles")
    public ResponseEntity<List<UserAuthority>> getUserRoles(@PathVariable Long id) {
        List<UserAuthority> roles = userService.getUserRoles(id);
        return ResponseEntity.ok(roles);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}/roles")
    public ResponseEntity<String> addRoleToUser(@PathVariable Long id, @RequestParam UserAuthority role) {
        userService.addRoleToUser(id, role);
        return ResponseEntity.ok("Роль добавлена");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}/roles")
    public ResponseEntity<String> removeRoleFromUser(@PathVariable Long id, @RequestParam UserAuthority role) {
        userService.removeRoleFromUser(id, role);
        return ResponseEntity.ok("Роль удалена");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/roles")
    public ResponseEntity<String> replaceUserRole(@PathVariable Long id,
                                                  @RequestParam UserAuthority oldRole,
                                                  @RequestParam UserAuthority newRole) {
        userService.replaceUserRole(id, oldRole, newRole);
        return ResponseEntity.ok("Роль заменена");
    }
}