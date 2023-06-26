package com.cg.repository;


import com.cg.model.Category;
import com.cg.model.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryName(String name);

    @Query("SELECT NEW com.cg.model.dto.CategoryDTO(" +
            "c.id, " +
            "c.categoryName " +
            ") FROM Category AS c")
    List<CategoryDTO> finAllCategoryDTO();
}
