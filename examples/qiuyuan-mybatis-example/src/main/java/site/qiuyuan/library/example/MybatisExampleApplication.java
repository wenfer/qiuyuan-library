package site.qiuyuan.library.example;

import org.apache.ibatis.annotations.Mapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import site.qiuyuan.library.mybatis.annotation.EnableMybatis;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
@EnableMybatis("site.qiuyuan.library.example.mapper")
@SpringBootApplication
public class MybatisExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisExampleApplication.class, args);
    }

}
