package cn.gateon.library.jpa.core.jpa;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

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
    }
}
