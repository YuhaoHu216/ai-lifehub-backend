package space.huyuhao.controller;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import space.huyuhao.dto.LoginDTO;
import space.huyuhao.serverce.UserService;
import space.huyuhao.vo.Result;

import java.io.UnsupportedEncodingException;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    // 发送邮件验证码
    @GetMapping("/email")
    public Result sendEmail(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        log.info("发送到邮箱:{}",email);
        userService.sendEmail(email);
        return Result.success("邮件发送成功");
    }

    // 发送手机验证码(模拟)
    @GetMapping("/phone")
    public Result sendPhone(@RequestParam("phone") String phone)  {
        log.info("发送到手机:{}",phone);
        return userService.sendPhone(phone);
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        log.info("登录用户:{}",loginDTO);
        return userService.login(loginDTO);
    }
}
