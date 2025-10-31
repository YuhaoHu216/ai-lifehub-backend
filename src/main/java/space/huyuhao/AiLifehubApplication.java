package space.huyuhao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy  = true)
@SpringBootApplication
@MapperScan("space.huyuhao.mapper")
public class AiLifehubApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiLifehubApplication.class, args);
    }

}
