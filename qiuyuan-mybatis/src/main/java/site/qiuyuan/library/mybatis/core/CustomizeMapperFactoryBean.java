package site.qiuyuan.library.mybatis.core;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.dao.support.DaoSupport;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
public class CustomizeMapperFactoryBean <T> extends DaoSupport implements FactoryBean<T> {


    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {

    }
}
