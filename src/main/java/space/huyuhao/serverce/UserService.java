package space.huyuhao.serverce;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import space.huyuhao.vo.Result;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {
    // 发送邮件验证码
    void sendEmail(String email) throws MessagingException, UnsupportedEncodingException;

    // 发送手机验证码(模拟)
    Result sendPhone(String phone);
}
