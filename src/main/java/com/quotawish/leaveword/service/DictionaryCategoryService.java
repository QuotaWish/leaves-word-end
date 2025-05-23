package com.quotawish.leaveword.service;

import com.quotawish.leaveword.model.dto.category.CategoryRelativeRequest;
import com.quotawish.leaveword.model.entity.Category;
import com.quotawish.leaveword.model.entity.DictionaryCategory;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface DictionaryCategoryService extends IService<DictionaryCategory>{


    int updateBatch(List<DictionaryCategory> list);

    int updateBatchSelective(List<DictionaryCategory> list);

    int batchInsert(List<DictionaryCategory> list);

    int deleteByPrimaryKeyIn(List<Integer> list);

    int insertOnDuplicateUpdate(DictionaryCategory record);

    int insertOnDuplicateUpdateSelective(DictionaryCategory record);

    boolean addCategoryForDictionaryWithBatch(CategoryRelativeRequest req);

    List<Category> getDictionaryCategoryByDictionaryId(Long dict_id);

}
