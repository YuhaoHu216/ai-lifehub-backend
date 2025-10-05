package space.huyuhao.serverce.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import space.huyuhao.enums.ErrorCode;
import space.huyuhao.exception.UserException;
import space.huyuhao.serverce.UserService;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine; // Thymeleaf 引擎

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 发送邮件验证码
    public void sendEmail(String email) throws MessagingException, UnsupportedEncodingException {

        // 生成验证码
        Random r = new Random();
        int code = r.nextInt(100000,999999);
        if(stringRedisTemplate.hasKey("code:"+email)){
            throw new UserException(ErrorCode.SEND_EMAIL_FREQUENT);
        }
        // 将验证码存入redis
        stringRedisTemplate.opsForValue().set("code:" + email, String.valueOf(code),1, TimeUnit.MINUTES);
        // 设置动态参数
        Context context = new Context();
        context.setVariable("username", "牢孙");
        context.setVariable("code", code);
        context.setVariable("expire", 5);

        // 渲染 HTML
        String htmlContent = templateEngine.process("emailTemplate", context);

        // 发送邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//        helper.setFrom(new InternetAddress("2913278634@qq.com", "智慧农业创新实验室", "UTF-8"));
        helper.setFrom("智慧农业创新实验室 <2913278634@qq.com>");
        helper.setTo(email);
        helper.setSubject("欢迎加入智慧农业创新实验室");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
}
