package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamPaperStats;
import com.quotawish.leaveword.service.impl.ExamPaperStatsServiceImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 试卷统计表(exam_paper_stats)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_paper_stats")
public class ExamPaperStatsController {
/**
* 服务对象
*/
    @Autowired
    private ExamPaperStatsServiceImpl examPaperStatsServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamPaperStats selectOne(Integer id) {
    return examPaperStatsServiceImpl.selectByPrimaryKey(id);
    }

}
