package site.qiuyuan.library.mybatis.core;

import org.apache.ibatis.binding.MapperProxyFactory;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
public class CustomizeMapperProxyFactory<T> extends MapperProxyFactory<T> {
    public CustomizeMapperProxyFactory(Class<T> mapperInterface) {
        super(mapperInterface);
    }
}
