package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.dto.request.SignInDto;
import info.andchelp.fitwf.dto.request.SignUpDto;
import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {

    final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(ResponseDto.of(authService.signIn(signInDto)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(ResponseDto.of(authService.signUp(signUpDto)));
    }
}
