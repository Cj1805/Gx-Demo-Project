package Backup;

import org.springframework.stereotype.Component;

import com.wellness.entities.Category;

@Component
public class CategoryMapper {
	
	public CategoryDTO convertToDto(Category category) {
		CategoryDTO categoryDto = new CategoryDTO();
		categoryDto.setCategoryId(category.getCategoryId());
		categoryDto.setCategoryname(category.getCategoryName());
		
		return categoryDto;
	}
	
	public Category convertToEntity(CategoryDTO categoryDto) {
		Category category = new Category();
		category.setCategoryId(categoryDto.getCategoryId());
		category.setCategoryName(categoryDto.getCategoryname());
		return category;
	}
}
