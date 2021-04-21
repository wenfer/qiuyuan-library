package site.qiuyuan.library.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import site.qiuyuan.library.jpa.EnableJPA;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/26
 */
@SpringBootApplication
@EnableJPA
public class JpaExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaExampleApplication.class, args);
    }

}
