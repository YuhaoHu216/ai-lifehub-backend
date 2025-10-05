package space.huyuhao.controller;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.huyuhao.serverce.UserService;
import space.huyuhao.vo.Result;

import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    // 发送邮件验证码
    @GetMapping("/user/email")
    public Result sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        log.info("发送到邮箱:{}",email);
        userService.sendEmail(email);
        return Result.success("邮件发送成功");
    }
}
