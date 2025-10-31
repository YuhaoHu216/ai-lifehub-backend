package space.huyuhao;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.huyuhao.service.impl.ShopServiceImpl;
import space.huyuhao.utils.RedisIdWorker;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;

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

    /**
     * 测试获取时间戳功能
     * 该方法用于测试LocalDateTime转换为UTC时间戳的功能
     * 无参数
     * 无返回值
     */
    @Test
    void getBeginTimeStamp(){
        // 创建指定时间的LocalDateTime对象
        LocalDateTime now = LocalDateTime.of(2025,10,11,21,36);
        // 将LocalDateTime转换为UTC时间戳
        long second = now.toEpochSecond(ZoneOffset.UTC);
        System.out.println(second);
    }

    @Resource
    private RedisIdWorker redisIdWorker;
    private static final ExecutorService es = Executors.newFixedThreadPool(500);
    /**
     * 测试RedisID生成器的并发性能
     * 该测试通过300个线程并发生成ID，每个线程生成100个ID，总共生成30000个ID
     * 用于验证ID生成器在高并发场景下的正确性和性能表现
     *
     * @throws InterruptedException 当线程等待过程中被中断时抛出
     */
    @Test
    void testIdWorker() throws InterruptedException {
        // 创建计数器，用于等待所有线程执行完成
        CountDownLatch latch = new CountDownLatch(300);

        // 定义任务：每个线程生成100个订单ID并打印
        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = redisIdWorker.nextId("order");
                System.out.println("id = " + id);
            }
            latch.countDown();
        };

        // 记录开始时间
        long begin = System.currentTimeMillis();

        // 提交300个任务到线程池并发执行
        for (int i = 0; i < 300; i++) {
            es.submit(task);
        }

        // 等待所有线程执行完成
        latch.await();

        // 计算并打印总耗时
        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - begin));
    }



}
