package site.qiuyuan.library.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.qiuyuan.library.mybatis.spring.SqlSessionFactoryBean;
import site.qiuyuan.library.mybatis.spring.SqlSessionTemplate;

import javax.sql.DataSource;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/24
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DataSourceProperties.class)
public class QiuyuanMyBatisConfiguration {



    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource source) throws Exception {
        SqlSessionFactoryBean ss = new SqlSessionFactoryBean();
        ss.setDataSource(source);
        return ss.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory factory) {
        return new SqlSessionTemplate(factory);
    }


}
