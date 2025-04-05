package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.ExamOperationLog;
import com.quotawish.leaveword.service.impl.ExamOperationLogImpl;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 操作日志表(exam_operation_log)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/exam_operation_log")
public class ExamOperationLogController {
/**
* 服务对象
*/
    @Autowired
    private ExamOperationLogImpl examOperationLogServiceImpl;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public ExamOperationLog selectOne(Integer id) {
    return examOperationLogServiceImpl.getById(id);
    }

}
