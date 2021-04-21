package site.qiuyuan.library.mybatis.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
@Setter
@Getter
public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    private String basePackage;

    private ApplicationContext applicationContext;

    private String beanName;

    private BeanNameGenerator nameGenerator;

    private String sqlSessionTemplateBeanName;


    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
        scanner.setSqlSessionTemplateBeanName(sqlSessionTemplateBeanName);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setBeanNameGenerator(this.nameGenerator);
        scanner.setBeanNameGenerator(this.nameGenerator);
        scanner.registerFilters();
        scanner.doScan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //do nothing
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
