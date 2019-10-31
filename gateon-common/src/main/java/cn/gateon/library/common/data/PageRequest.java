package cn.gateon.library.common.data;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author qiuyuan
 * @since 1.0
 * 分页请求
 */
@Setter
@Getter
public class PageRequest implements Serializable {

    private int page = 0;

    private int size = 10;

    private String sort = "id";

    private Boolean asc = false;

    public PageRequest() {
    }

    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }


}
