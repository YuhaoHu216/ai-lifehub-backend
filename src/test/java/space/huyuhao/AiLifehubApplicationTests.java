package space.huyuhao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.huyuhao.service.impl.ShopServiceImpl;

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
            shopServiceImpl.savaShop2Redis(i,10L);
        }
    }

}
