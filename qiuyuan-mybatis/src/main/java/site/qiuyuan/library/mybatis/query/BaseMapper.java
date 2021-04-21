package site.qiuyuan.library.mybatis.query;

import site.qiuyuan.library.common.data.Page;
import site.qiuyuan.library.common.data.PageRequest;

import java.io.Serializable;

/**
 * @author wangye
 */
public interface BaseMapper<T> {

    Page<T> paging(PageRequest pageRequest);

    T save(T entity);

    T findById(Serializable id);

    int deleteById(Serializable id);

}
