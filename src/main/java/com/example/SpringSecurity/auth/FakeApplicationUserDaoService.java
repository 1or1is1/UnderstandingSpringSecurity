package com.example.SpringSecurity.auth;

import com.example.SpringSecurity.security.ApplicationUserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getAllApplicationUser()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getAllApplicationUser() {
        return List.of(
                new ApplicationUser(
                        "anna",
                        passwordEncoder.encode("password"),
                        ApplicationUserRole.STUDENT.getGrantedAuthorities(),
                        true, true, true, true
                ),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("password"),
                        ApplicationUserRole.ADMIN.getGrantedAuthorities(),
                        true, true, true, true
                ),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("password"),
                        ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities(),
                        true, true, true, true
                )
        );
    }

}
