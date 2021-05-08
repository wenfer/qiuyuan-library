package site.qiuyuan.library.mvc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.qiuyuan.library.mvc.exceptionhandle.ServiceExceptionHandle;
import site.qiuyuan.library.mvc.factory.JacksonConverterFactory;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2.5
 */
@EnableBaseMvc
@Import(ServiceExceptionHandle.class)
public class BaseMvcConfiguration implements WebMvcConfigurer {

    private List<HandlerMethodArgumentResolver> platformResolvers;

    public BaseMvcConfiguration(List<HandlerMethodArgumentResolver> resolvers) {
        this.platformResolvers = resolvers;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        if (!CollectionUtils.isEmpty(platformResolvers)) {
            resolvers.addAll(platformResolvers);
        }
    }

    @Bean
    @ConditionalOnClass(MappingJackson2HttpMessageConverter.class)
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        return new JacksonConverterFactory().build();
    }

}
