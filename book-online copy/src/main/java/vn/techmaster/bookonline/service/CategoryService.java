package vn.techmaster.bookonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.dto.CategoryRequest;
import vn.techmaster.bookonline.entity.Category;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Find by id
    public Category findById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("No category with id = " + id));
    }

    // Find all
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // Save entity
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    // Map request from entity
    public CategoryRequest mapRequestEntity(Category category) {
        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setId(category.getId());
        categoryRequest.setName(category.getName());

        return categoryRequest;
    }

    // Save entity by request
    public Category saveByRequest(CategoryRequest categoryRequest) {
        Category category;
        if (categoryRequest.getId() == null) {
            category = new Category();
        } else {
            category = findById(categoryRequest.getId());
        }

        category.setName(categoryRequest.getName());

        return categoryRepository.save(category);
    }

    // Delete by id
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    // Find all, pageable order by name
    public Page<Category> findByOrderByNameAsc(Pageable pageable) {
        return categoryRepository.findByOrderByNameAsc(pageable);
    }

    // Check exists by name
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
