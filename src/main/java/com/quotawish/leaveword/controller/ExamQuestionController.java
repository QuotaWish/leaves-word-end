package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamQuestion;
import com.quotawish.leaveword.service.impl.ExamQuestionServiceImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试题表(exam_question)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_question")
public class ExamQuestionController {
/**
* 服务对象
*/
    @Autowired
    private ExamQuestionServiceImpl examQuestionServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamQuestion selectOne(Integer id) {
    return examQuestionServiceImpl.selectByPrimaryKey(id);
    }

}
