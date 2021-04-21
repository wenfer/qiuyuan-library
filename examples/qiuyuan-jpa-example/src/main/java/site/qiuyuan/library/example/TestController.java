package site.qiuyuan.library.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.qiuyuan.library.common.rest.Result;
import site.qiuyuan.library.example.repository.TestRepository;

import javax.annotation.PostConstruct;

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
    private TestRepository testMapper;

    @PostConstruct
    public void s(){
        System.out.println(testMapper);
    }

    @GetMapping
    public Result index(){
        return Result.SUCCESS;
    }

}
