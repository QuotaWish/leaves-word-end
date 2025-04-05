package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamCategoryPaper;
import com.quotawish.leaveword.service.impl.ExamCategoryPaperImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷与分类关联表(exam_category_paper)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_category_paper")
public class ExamCategoryPaperController {
/**
* 服务对象
*/
    @Autowired
    private ExamCategoryPaperImpl examCategoryPaperServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamCategoryPaper selectOne(Integer id) {
    return examCategoryPaperServiceImpl.getById(id);
    }

}
