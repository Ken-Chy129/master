package cn.ken.master.server;

import cn.ken.master.server.core.AppManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Ken-Chy129
 * @date 2024/8/28
 */
@MapperScan("cn.ken.master.server.*.mapper")
@SpringBootApplication
public class MasterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterServerApplication.class, args);
        AppManager.init();
    }
}
