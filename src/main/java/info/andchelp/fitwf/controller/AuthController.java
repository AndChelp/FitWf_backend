package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.dto.request.SignInDto;
import info.andchelp.fitwf.dto.request.SignUpDto;
import info.andchelp.fitwf.dto.response.Response;
import info.andchelp.fitwf.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<Response> signIn(@RequestBody SignInDto signInDto) {
        return Response.of(authService.signIn(signInDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> signUp(@RequestBody SignUpDto signUpDto) {
        return Response.of(authService.signUp(signUpDto));
    }
}
