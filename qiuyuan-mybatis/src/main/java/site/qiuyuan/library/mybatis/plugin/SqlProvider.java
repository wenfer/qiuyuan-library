package site.qiuyuan.library.mybatis.plugin;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;

import java.lang.reflect.Method;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/24
 */
public class SqlProvider implements ProviderMethodResolver {

    @Override
    public Method resolveMethod(ProviderContext context) {
        return null;
    }
}
