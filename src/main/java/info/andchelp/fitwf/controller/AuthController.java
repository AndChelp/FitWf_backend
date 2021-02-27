package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.dto.SignUpDto;
import info.andchelp.fitwf.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/signin")
    public String signin() {
        System.out.println();
        return "OK";
    }

    @PostMapping("/signup")
    public void signup(SignUpDto signUpDto) {
        authService.signUp(signUpDto);
    }

}
