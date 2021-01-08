package site.qiuyuan.library.common.utils;


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

    public static final String PATTERN = "(\\d{3})\\d{4}(\\d{4})";

    private static final String REPLACE = "(\\d{3})\\d{4}(\\d{4})";


    /**
     * 手机号隐藏中间4位
     */
    public static String hideMiddle(String phone) {
        if (phone == null || phone.isEmpty()) {
            return "";
        }
        return phone.replaceAll(PATTERN, REPLACE);
    }

    public static boolean match(String phone) {
        return Pattern.matches(PATTERN, phone);
    }

}
