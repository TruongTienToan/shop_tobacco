package com.cg.service.category;

import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ICategoryService extends IGeneralService<Category> {
    Category findCatetoryByName(String name);
    List<CategoryDTO> finAllCategoryDTO();
}
