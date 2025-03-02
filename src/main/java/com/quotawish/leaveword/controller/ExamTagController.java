package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamTag;
import com.quotawish.leaveword.service.impl.ExamTagServiceImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷标签表(exam_tag)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_tag")
public class ExamTagController {
/**
* 服务对象
*/
    @Autowired
    private ExamTagServiceImpl examTagServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamTag selectOne(Integer id) {
    return examTagServiceImpl.selectByPrimaryKey(id);
    }

}
