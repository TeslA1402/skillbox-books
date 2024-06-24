package org.example.skillboxbooks.service;

import lombok.RequiredArgsConstructor;
import org.example.skillboxbooks.entity.Category;
import org.example.skillboxbooks.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category getOrCreateCategoryByName(String name) {
        String normalizedName = name.trim().toLowerCase();
        Optional<Category> optional = categoryRepository.findByNameIgnoreCase(normalizedName);
        return optional.orElseGet(() -> categoryRepository.save(Category.builder()
                .name(normalizedName).build()));
    }
}
