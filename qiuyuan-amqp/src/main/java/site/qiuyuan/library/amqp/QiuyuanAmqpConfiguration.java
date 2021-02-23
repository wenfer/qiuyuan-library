package site.qiuyuan.library.amqp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Configuration
public class QiuyuanAmqpConfiguration {

    @Bean
    public QiuyuanConverter converter() {
        return new QiuyuanConverter();
    }

}
