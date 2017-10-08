package cn.don9cn.blog.action;

import cn.don9cn.blog.model.bussiness.article.Article;
import cn.don9cn.blog.plugins.DaoHelper.DaoHelper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Optional;


/**
 * Created by don9 on 2017/10/8.
 */
@RestController
@RequestMapping("/test")
public class TestAction {

    @Autowired
    private Optional<DataSource> dataSource;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @GetMapping("/01")
    public Object test01(){
        DaoHelper.baseInsert(new Article());
        return "asdasd";
    }
}
