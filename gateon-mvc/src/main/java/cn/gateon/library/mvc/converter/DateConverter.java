package cn.gateon.library.mvc.converter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since v1.1
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (StrUtil.isBlank(source)) {
            return null;
        }
        return new Date(Long.parseLong(source));
    }
}
