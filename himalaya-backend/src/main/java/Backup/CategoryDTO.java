package Backup;

import java.util.List;

import com.wellness.entities.Product;

import lombok.Data;

@Data
public class CategoryDTO {
	private int categoryId;
	private String categoryname;
	private List<Product>product;
}
