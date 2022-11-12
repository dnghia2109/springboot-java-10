package vn.techmaster.bookonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.dto.AuthorRequest;
import vn.techmaster.bookonline.entity.Author;
import vn.techmaster.bookonline.exception.NotFoundException;
import vn.techmaster.bookonline.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    // Find by id
    public Author findById(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new NotFoundException("No author with id = " + id));
    }

    // Find all
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    // Save entity
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    // Map request from entity
    public AuthorRequest mapRequestEntity(Author author) {
        AuthorRequest authorRequest = new AuthorRequest();

        authorRequest.setId(author.getId());
        authorRequest.setFullName(author.getFullName());
        authorRequest.setGender(author.getGender());
        authorRequest.setAddress(author.getAddress());
        authorRequest.setYearOfBirth(author.getYearOfBirth());
        authorRequest.setYearOfDeath(author.getYearOfDeath());

        return authorRequest;
    }

    // Save entity by request
    public Author saveByRequest(AuthorRequest authorRequest) {
        Author author;
        if (authorRequest.getId() == null) {
            author = new Author();
        } else {
            author = findById(authorRequest.getId());
        }

        author.setFullName(authorRequest.getFullName());
        author.setGender(authorRequest.getGender());
        author.setAddress(authorRequest.getAddress());
        author.setYearOfBirth(authorRequest.getYearOfBirth());
        author.setYearOfDeath(authorRequest.getYearOfDeath());

        return authorRepository.save(author);
    }

    // Delete by id
    public void deleteById(String id) {
        authorRepository.deleteById(id);
    }

    // Find all, pageable order by fullName
    public Page<Author> findByOrderByFullNameAsc(Pageable pageable) {
        return authorRepository.findByOrderByFullNameAsc(pageable);
    }
}
