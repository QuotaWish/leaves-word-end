package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamPaperVersion;
import com.quotawish.leaveword.service.impl.ExamPaperVersionImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷版本记录表(exam_paper_version)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_paper_version")
public class ExamPaperVersionController {
/**
* 服务对象
*/
    @Autowired
    private ExamPaperVersionImpl examPaperVersionServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamPaperVersion selectOne(Integer id) {
    return examPaperVersionServiceImpl.getById(id);
    }

}
