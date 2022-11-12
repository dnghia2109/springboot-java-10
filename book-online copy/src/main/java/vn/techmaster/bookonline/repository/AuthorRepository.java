package vn.techmaster.bookonline.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.techmaster.bookonline.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {
    // Find all, pageable order by fullName
    Page<Author> findByOrderByFullNameAsc(Pageable pageable);

}