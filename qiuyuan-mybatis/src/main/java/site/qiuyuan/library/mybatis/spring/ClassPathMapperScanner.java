package site.qiuyuan.library.mybatis.spring;

import lombok.Setter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.StringUtils;
import site.qiuyuan.library.mybatis.query.BaseMapper;
import site.qiuyuan.library.mybatis.query.BaseMapperImpl;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/4/1
 */
@Setter
public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    private Class<?> markerInterface = BaseMapper.class;
    private String sqlSessionTemplateBeanName = "sqlSessionTemplate";

    public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {
        boolean acceptAllInterfaces = true;
        // override AssignableTypeFilter to ignore matches on the actual marker interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (!beanDefinitions.isEmpty()) {
            this.processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }


    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            // the mapper interface is the original class of the bean
            // but, the actual class of the bean is MapperFactoryBean
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.setBeanClass(BaseMapperImpl.class);
            if (StringUtils.hasText(this.sqlSessionTemplateBeanName)) {
                definition.getPropertyValues().add("sqlSessionTemplate",
                        new RuntimeBeanReference(this.sqlSessionTemplateBeanName));
            }
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }


    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }


}

