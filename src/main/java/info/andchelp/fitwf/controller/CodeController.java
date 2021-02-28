package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.service.CodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/public/code")
public class CodeController {
    final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/verify")
    public void verify(@RequestParam UUID code) {
        codeService.verifyCode(code);
    }
}
