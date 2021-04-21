package site.qiuyuan.library.mybatis.query;

import lombok.Getter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.dao.support.DaoSupport;
import site.qiuyuan.library.common.data.Page;
import site.qiuyuan.library.common.data.PageRequest;
import site.qiuyuan.library.mybatis.spring.SqlSessionTemplate;

import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/4/1
 */
public class BaseMapperImpl<T,M> extends DaoSupport implements BaseMapper<T>, FactoryBean<M> {

    private SqlSessionTemplate sqlSessionTemplate;

    private Class<M> mapperInterface;

    public BaseMapperImpl(Class<M> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Page<T> paging(PageRequest pageRequest) {
        return null;
    }

    @Override
    public T save(T entity) {
        return null;
    }

    @Override
    public T findById(Serializable id) {
        return null;
    }

    @Override
    public int deleteById(Serializable id) {
        return 0;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        notNull(this.sqlSessionTemplate, "Property 'sqlSession' are required");
    }

    @Override
    public M getObject() throws Exception {
        return getSqlSessionTemplate().getMapper(this.mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return this.mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
