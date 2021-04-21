package site.qiuyuan.library.mybatis.core;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
public class CustomizeMapperRegistry extends MapperRegistry {
    public CustomizeMapperRegistry(Configuration config) {
        super(config);
    }
}
