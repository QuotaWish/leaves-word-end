package com.quotawish.leaveword.controller;
import com.quotawish.leaveword.model.entity.DictionaryCategory;
import com.quotawish.leaveword.service.DictionaryCategoryService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 书籍分类关系表(dictionary_category)表控制层
*
* @author TalexDreamSoul
*/
@RestController
@RequestMapping("/dictionary_category")
public class DictionaryCategoryController {
/**
* 服务对象
*/
    @Autowired
    private DictionaryCategoryService dictionaryCategoryService;



}
