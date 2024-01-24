package com.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private int categoryId;
    @NotEmpty
    @Size(min = 4, max = 30, message = "Category name must be of length between 4 and 30")
    private String name;

}
