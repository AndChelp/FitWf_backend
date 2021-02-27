package info.andchelp.fitwf.controller;

import info.andchelp.fitwf.service.MailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Test {

    final MailService mailService;

    public Test(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/public")
    public String testpub() {
        mailService.sendSimpleMessage("dsfsaegf.mo@yandex.ru", "Test", "tesT");
        return "Public";
    }
}
