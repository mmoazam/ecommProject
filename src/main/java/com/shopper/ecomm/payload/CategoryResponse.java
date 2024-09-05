package com.shopper.ecomm.payload;

import com.shopper.ecomm.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> content;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean lastPage;
}
