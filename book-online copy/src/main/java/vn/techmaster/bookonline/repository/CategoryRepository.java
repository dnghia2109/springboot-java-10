package vn.techmaster.bookonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.bookonline.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    // Find all, pageable order by name
    Page<Category> findByOrderByNameAsc(Pageable pageable);

    // Check exists by name
    boolean existsByName(String name);
}