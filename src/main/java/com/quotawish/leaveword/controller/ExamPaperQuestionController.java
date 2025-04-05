package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamPaperQuestion;
import com.quotawish.leaveword.service.impl.ExamPaperQuestionImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷与试题关联表(exam_paper_question)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_paper_question")
public class ExamPaperQuestionController {
/**
* 服务对象
*/
    @Autowired
    private ExamPaperQuestionImpl examPaperQuestionServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamPaperQuestion selectOne(Integer id) {
    return examPaperQuestionServiceImpl.getById(id);
    }

}
