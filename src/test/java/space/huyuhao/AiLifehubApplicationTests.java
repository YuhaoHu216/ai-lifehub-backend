package space.huyuhao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.huyuhao.service.impl.ShopServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootTest
class AiLifehubApplicationTests {

    @Autowired
    private ShopServiceImpl shopServiceImpl;

    @Test
    void contextLoads() {
    }

    @Test
    void testSaveShop(){
        for(long i = 1; i < 10; i++){
            shopServiceImpl.saveShop2Redis(i,10L);
        }
    }

    @Test
    void getBeginTimeStamp(){
        LocalDateTime now = LocalDateTime.of(2025,10,11,21,36);
        long second = now.toEpochSecond(ZoneOffset.UTC);
        System.out.println(second);
    }

}
