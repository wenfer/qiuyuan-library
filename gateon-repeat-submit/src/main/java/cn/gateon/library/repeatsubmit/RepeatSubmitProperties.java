package cn.gateon.library.repeatsubmit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.3
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "repeat.submit")
public class RepeatSubmitProperties {

    private long expired;

    private String prefix;

}
