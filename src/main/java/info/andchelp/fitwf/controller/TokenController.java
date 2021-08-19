package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenController {
    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/email/verify")
    public void proceed(@RequestParam String token) {
        tokenService.proceedVerifyEmailToken(token);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseDto> refresh(@RequestParam String token) {
        return ResponseDto.ofSuccess(tokenService.refreshToken(token));
    }
}
