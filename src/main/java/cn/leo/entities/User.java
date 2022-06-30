package cn.leo.entities;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

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
    @Column(name = "u_username")
    private String username;
    /**
     * 密码
     */
    @Column(name = "u_password")
    private String password;
    /**
     * 邮箱
     */
    @Column(name = "u_mail")
    private String mail;
    /**
     * 描述
     */
    @Column(name = "u_describe")
    private String describe;
    /**
     * 头像
     */
    @Column(name = "u_avatar")
    private String avatar;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Blog> blogs = new LinkedHashSet<>();

    public Set<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(Set<Blog> blogs) {
        this.blogs = blogs;
    }
/**
     * 博客
     */



}