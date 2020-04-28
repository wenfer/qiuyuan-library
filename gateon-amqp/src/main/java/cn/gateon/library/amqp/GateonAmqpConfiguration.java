package cn.gateon.library.amqp;

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
public class GateonAmqpConfiguration {

    @Bean
    public GateonConverter converter() {
        return new GateonConverter();
    }

}
