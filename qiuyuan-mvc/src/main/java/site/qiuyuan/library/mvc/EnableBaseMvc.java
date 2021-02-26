package site.qiuyuan.library.mvc;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author qiuyuan
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(BaseMvcConfiguration.class)
public @interface EnableBaseMvc {
}
