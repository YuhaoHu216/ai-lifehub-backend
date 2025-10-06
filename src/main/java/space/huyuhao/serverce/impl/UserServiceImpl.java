package space.huyuhao.serverce.impl;

import cn.hutool.core.util.RandomUtil;

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
import space.huyuhao.constant.UserConstant;
import space.huyuhao.dto.LoginDTO;
import space.huyuhao.enums.ErrorCode;
import space.huyuhao.exception.UserException;
import space.huyuhao.mapper.UserMapper;
import space.huyuhao.po.User;
import space.huyuhao.serverce.UserService;
import org.thymeleaf.context.Context;
import space.huyuhao.utils.UserHolder;
import space.huyuhao.vo.Result;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static space.huyuhao.constant.UserConstant.CODE_TTL;
import static space.huyuhao.constant.UserConstant.USER_NICKNAME_PREFIX;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine; // Thymeleaf 引擎

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    // 发送邮件验证码
    public void sendEmail(String email) throws MessagingException, UnsupportedEncodingException {

        // TODO 邮箱的校验
        // 生成验证码
        Random r = new Random();
        int code = r.nextInt(100000,999999);
        // 判断验证码是否存在
        if(stringRedisTemplate.hasKey("code:"+email)){
            throw new UserException(ErrorCode.SEND_EMAIL_FREQUENT);
        }
        // 将验证码存入redis
        stringRedisTemplate.opsForValue().set("code:" + email, String.valueOf(code), CODE_TTL, TimeUnit.MINUTES);
        // 设置动态参数
        Context context = new Context();
        context.setVariable("username", "牢孙");
        context.setVariable("code", code);
        context.setVariable("expire", CODE_TTL);

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

    // 模拟发送手机验证码
    @Override
    public Result sendPhone(String phone) {
        // TODO 手机号校验
        // 生成验证码
//        String code = RandomUtil.randomNumbers(6);
        String code = "111111";
        // TODO 发送验证码逻辑
        // 存储验证码
        stringRedisTemplate.opsForValue().set("code:"+phone,code,CODE_TTL, TimeUnit.MINUTES);
        return Result.success("验证码发送成功");
    }

    // 用户登录
    public Result login(LoginDTO loginDTO) {
        // TODO 校验手机号
        String phone = loginDTO.getPhone();

        // 获取redis中的验证码
        String cacheCode = stringRedisTemplate.opsForValue().get("code:"+phone);
        // 校验验证码
        if (cacheCode == null || !cacheCode.equals(loginDTO.getCode()) ){
             return Result.error("验证码错误");
        }

        // 根据手机号查询用户
        User user = userMapper.getUser(phone);
        // 不存在就创建账号
        if(user == null){
            user = new User();
            user.setPhone(phone);
            user.setNickName(USER_NICKNAME_PREFIX + RandomUtil.randomString(6));
            userMapper.insertUser(user);
        }
        // 存在就保存用户信息
        UserHolder.saveUser(user);
        return Result.success();
    }
}
