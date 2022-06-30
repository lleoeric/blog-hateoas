package cn.leo.entities;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @NotNull
    @Column(name = "username")
    private String username;
    /**
     * 密码
     */
    @NotNull
    @Column(name = "password")
    private String password;
    /**
     * 邮箱
     */
    @Column(name = "email_Address")
    private String emailAddress;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @ToString.Exclude
    private Set<Blog> blogs = new LinkedHashSet<>();

/*
    博客
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}