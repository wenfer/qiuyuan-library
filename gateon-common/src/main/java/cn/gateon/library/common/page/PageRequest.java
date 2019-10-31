package cn.gateon.library.common.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author qiuyuan
 * @since 1.0
 */
@Setter
@Getter
public class PageRequest implements Serializable {

    private int page = 0;

    private int size = 10;

    private String sort = "id";

    private Boolean asc = false;

}
