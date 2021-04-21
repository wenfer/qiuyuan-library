package site.qiuyuan.library.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.qiuyuan.library.common.rest.Result;
import site.qiuyuan.library.example.mapper.TestMapper;

/**
 * <p>
 * </p>
 *
 * @author wangye
 * @since 2021/3/25
 */
@RestController
public class TestController {


    @Autowired
    private TestMapper testMapper;


    @GetMapping
    public Result index(){
        return Result.SUCCESS;
    }

}
