package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamPaperTag;
import com.quotawish.leaveword.service.impl.ExamPaperTagImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷与标签关联表(exam_paper_tag)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_paper_tag")
public class ExamPaperTagController {
/**
* 服务对象
*/
    @Autowired
    private ExamPaperTagImpl examPaperTagServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamPaperTag selectOne(Integer id) {
    return examPaperTagServiceImpl.getById(id);
    }

}
