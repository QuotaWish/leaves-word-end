package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamCategory;
import com.quotawish.leaveword.service.impl.ExamCategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷分类表(exam_category)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_category")
public class ExamCategoryController {
/**
* 服务对象
*/
    @Autowired
    private ExamCategoryServiceImpl examCategoryServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamCategory selectOne(Integer id) {
    return examCategoryServiceImpl.selectByPrimaryKey(id);
    }

}
