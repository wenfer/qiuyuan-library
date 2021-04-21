package site.qiuyuan.library.mybatis.spring;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import site.qiuyuan.library.mybatis.annotation.EnableMybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableMybatis.class.getName()));
        if (mapperScanAttrs != null) {
            String baseBeanName = generateBaseBeanName(importingClassMetadata, 0);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
            List<String> basePackages = new ArrayList<>();
            basePackages.addAll(Arrays.stream(mapperScanAttrs.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));
            basePackages.addAll(Arrays.stream(mapperScanAttrs.getStringArray("basePackages")).filter(StringUtils::hasText).collect(Collectors.toList()));
            basePackages.addAll(Arrays.stream(mapperScanAttrs.getClassArray("basePackageClasses")).map(ClassUtils::getPackageName).collect(Collectors.toList()));
            if (basePackages.isEmpty()) {
                basePackages.add(getDefaultBasePackage(importingClassMetadata));
            }
            String sqlSessionTemplateRef = mapperScanAttrs.getString("sqlSessionTemplateRef");
            if (StringUtils.hasText(sqlSessionTemplateRef)) {
                builder.addPropertyValue("sqlSessionTemplateBeanName", sqlSessionTemplateRef);
            }
            builder.addPropertyValue("basePackage",StringUtils.collectionToCommaDelimitedString(basePackages));
            Class<? extends BeanNameGenerator> generatorClass = mapperScanAttrs.getClass("nameGenerator");
            if (!BeanNameGenerator.class.equals(generatorClass)) {
                builder.addPropertyValue("nameGenerator", BeanUtils.instantiateClass(generatorClass));
            }

            registry.registerBeanDefinition(baseBeanName,builder.getBeanDefinition());
        }
    }

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + MapperScannerConfigurer.class.getSimpleName() + "#" + index;
    }

    private static String getDefaultBasePackage(AnnotationMetadata importingClassMetadata) {
        return ClassUtils.getPackageName(importingClassMetadata.getClassName());
    }
}
