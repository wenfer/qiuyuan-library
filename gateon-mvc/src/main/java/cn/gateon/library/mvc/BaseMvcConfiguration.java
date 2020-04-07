package cn.gateon.library.mvc;

import cn.gateon.library.mvc.factory.JacksonConverterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.2.5
 */
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

    @Override
    @ConditionalOnClass(MappingJackson2HttpMessageConverter.class)
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new JacksonConverterFactory().build());
    }

    @Override
    @ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
