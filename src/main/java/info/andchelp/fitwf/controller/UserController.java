package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.dto.response.ResponseDto;
import info.andchelp.fitwf.model.enums.MailMessageType;
import info.andchelp.fitwf.security.SecurityContextUtils;
import info.andchelp.fitwf.service.AuthService;
import info.andchelp.fitwf.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final AuthService authService;
    private final MailService mailService;

    @DeleteMapping("/logout")
    public ResponseEntity<ResponseDto> logout() {
        authService.logout();
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }

    @GetMapping("/send_verify_token")
    public ResponseEntity<ResponseDto> sendVerifyToken() {
        mailService.sendVerifyRegistrationLink(SecurityContextUtils.getUser(), LocaleContextHolder.getLocale(), MailMessageType.VERIFY_EMAIL);
        return ResponseEntity.ok(ResponseDto.ofSuccess());
    }

}
