package cn.gateon.library.xxljob;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 2.0
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    private String adminAddress;

    private String accessToken;

    private String appName;

    private String ip;

    private Integer port;

    private String logPath;

    private Integer logRetentionDays;

    private String address;
}
