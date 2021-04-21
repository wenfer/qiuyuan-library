package site.qiuyuan.library.mybatis.annotation;



import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;
import site.qiuyuan.library.mybatis.QiuyuanMyBatisConfiguration;
import site.qiuyuan.library.mybatis.spring.MapperScannerRegistrar;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({QiuyuanMyBatisConfiguration.class, MapperScannerRegistrar.class})
public @interface EnableMybatis {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    String sqlSessionTemplateRef() default "sqlSessionTemplate";

    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;
}
