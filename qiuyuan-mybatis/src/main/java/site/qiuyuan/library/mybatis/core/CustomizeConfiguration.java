package site.qiuyuan.library.mybatis.core;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
public class CustomizeConfiguration extends Configuration {

    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    @Override
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return super.getMapper(type, sqlSession);
    }
}
