package space.huyuhao.user.service;


import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import space.huyuhao.user.dto.LoginDTO;
import space.huyuhao.user.vo.Result;


import java.io.UnsupportedEncodingException;

@Service
public interface UserService {
    // 发送邮件验证码
    void sendEmail(String email) throws MessagingException, UnsupportedEncodingException;

    // 发送手机验证码(模拟)
    Result sendPhone(String phone);
    // 用户登录
    Result login(LoginDTO loginDTO);
}
