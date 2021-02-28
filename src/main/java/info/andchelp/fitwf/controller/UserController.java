package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @DeleteMapping("/signout")
    public String signOut() {
        authService.signOut();
        return "K";
    }
}
