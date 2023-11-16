import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/9/30 21:07
 */
@SpringBootApplication
@MapperScan("com.jun.mapper")
@ComponentScan("com.jun")
@EnableScheduling
@EnableSwagger2
public class SpringbootblogApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringbootblogApplication.class, args);
    }



}
