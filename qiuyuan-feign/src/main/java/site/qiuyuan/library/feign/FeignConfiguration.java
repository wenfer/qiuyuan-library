package site.qiuyuan.library.feign;

import feign.Feign;
import feign.Retryer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
@Configuration
@ConditionalOnClass
public class FeignConfiguration extends FeignClientsConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    public Feign.Builder feignBuilder(Retryer retryer) {
        Feign.Builder retryer1 = Feign.builder().retryer(retryer);
        retryer1.errorDecoder(new FeignErrorDecoder());
        return retryer1;
    }

}
