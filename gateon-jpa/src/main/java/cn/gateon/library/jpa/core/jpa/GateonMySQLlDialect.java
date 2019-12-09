package cn.gateon.library.jpa.core.jpa;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;
import org.springframework.data.jpa.repository.query.QueryUtils;

/**
 * <p>
 * mysql方言扩展
 * </p>
 *
 * @author qiuyuan
 * @since 1.1
 */
public class GateonMySQLlDialect extends MySQL5Dialect {

    public GateonMySQLlDialect() {
        super.registerFunction("convert", new SQLFunctionTemplate(StringType.INSTANCE, "CONVERT(?1 USING ?2)"));
        super.registerFunction("find_in_set", new SQLFunctionTemplate(StringType.INSTANCE, "find_in_set(?1 USING ?2)"));
    }

}
