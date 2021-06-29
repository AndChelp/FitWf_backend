package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.dto.request.LoginDto;
import info.andchelp.fitwf.dto.request.RegisterDto;
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

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> signIn(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(ResponseDto.of(authService.login(loginDto)));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> signUp(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(ResponseDto.of(authService.register(registerDto)));
    }
}
