package cn.gateon.library.xxljob;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobConfiguration {

    private XxlJobProperties properties;

    public XxlJobConfiguration(XxlJobProperties properties) {
        this.properties = properties;
    }

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(properties.getAdminAddress());
        xxlJobSpringExecutor.setAppname(properties.getAppName());
        xxlJobSpringExecutor.setIp(properties.getIp());
        if (properties.getPort() != null) {
            xxlJobSpringExecutor.setPort(properties.getPort());
        }
        xxlJobSpringExecutor.setAccessToken(properties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(properties.getLogPath());
        if (properties.getLogRetentionDays() != null) {
            xxlJobSpringExecutor.setLogRetentionDays(properties.getLogRetentionDays());
        }
        return xxlJobSpringExecutor;
    }
}
