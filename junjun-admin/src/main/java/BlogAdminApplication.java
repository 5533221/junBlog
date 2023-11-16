

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 2716
 * @date 2023/10/1314点56分
 */
@SpringBootApplication //把这个类标识为引导类(也叫启动类)
@MapperScan("com.jun.mapper") //mybatis的配置
@ComponentScan("com.jun")
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}