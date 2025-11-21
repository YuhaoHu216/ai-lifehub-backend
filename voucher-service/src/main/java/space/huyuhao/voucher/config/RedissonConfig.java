package space.huyuhao.voucher.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        // 添加redis地址,这里添加了单点的地址,也可以用config.userClusterServers()添加集群地址
        config.useSingleServer().setAddress("redis://127.0.0.1:6379")
//                .setPassword("123321")
        ;
        // 创建RedissonClient对象
        return Redisson.create(config);
    }
}
