package com.quotawish.leaveword.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.quotawish.leaveword.model.entity.english.word.WordStatusChange;
import com.quotawish.leaveword.service.WordStatusChangeService;
import com.quotawish.leaveword.mapper.WordStatusChangeMapper;
import org.springframework.stereotype.Service;

/**
* @author TalexDS
* @description 针对表【word_status_change(单词状态变更记录表)】的数据库操作Service实现
* @createDate 2025-01-24 12:47:41
*/
@Service
public class WordStatusChangeServiceImpl extends ServiceImpl<WordStatusChangeMapper, WordStatusChange>
    implements WordStatusChangeService{

}




