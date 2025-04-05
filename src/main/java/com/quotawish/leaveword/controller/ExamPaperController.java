package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamPaper;
import com.quotawish.leaveword.service.impl.ExamPaperImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷表(exam_paper)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_paper")
public class ExamPaperController {
/**
* 服务对象
*/
    @Autowired
    private ExamPaperImpl examPaperServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamPaper selectOne(Integer id) {
    return examPaperServiceImpl.getById(id);
    }

}
