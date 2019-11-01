package cn.gateon.library.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * <p>
 * 手机号处理工具类
 * </p>
 *
 * @author qiuyuan
 * @since v1.1
 */
public class PhoneUtil {

    private static final String PATTERN = "(\\d{3})\\d{4}(\\d{4})";

    private static final String REPLACE = "(\\d{3})\\d{4}(\\d{4})";


    /**
     * 手机号隐藏中间4位
     */
    public static String hideMiddle(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return "";
        }
        return phone.replaceAll(PATTERN, REPLACE);
    }

    public static boolean match(String phone) {
        return Pattern.matches(PATTERN, phone);
    }

}
