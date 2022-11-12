package vn.techmaster.bookonline.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Long price;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private Set<Author> authors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new LinkedHashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBook(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setBook(null);
    }

    @ManyToMany
    @JoinTable(name = "category_book", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new LinkedHashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getBooks().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getBooks().remove(this);
    }

    @ManyToOne
    @JoinColumn(name = "publishing_company_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book")
    private Set<OrderBook> orderBooks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<CartBook> cartBooks = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}