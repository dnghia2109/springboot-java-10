package vn.techmaster.bookonline.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "home_address")
    private String homeAddress;

    @Column(name = "work_address")
    private String workAddress;

    @Column(name = "avatar")
    private String avatar;

    @Type(type = "json")
    @Column(name = "roles", columnDefinition = "json")
    private List<String> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new LinkedHashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setUser(null);
    }

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Cart cart;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}