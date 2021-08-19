package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ResponseDto> logout() {
        authService.logout();
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }
}
