package cn.leo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "blog")
@JsonFormat
public class Blog extends BaseEntity {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 博客标题
     */
    @NotNull
    @Column(name = "title")
    private String title;

    /**
     * 博客首图
     */
    @NotNull
    @Column(name = "first_picture")
    private String firstPicture;

    /**
     * 浏览次数
     */
    @NotNull
    @Column(name = "views")
    private Integer views = 0;

    /**
     * 赞赏开启 false不开启
     */
    @NotNull
    @Column(name = "appreciate")
    private Boolean appreciate = false;

    /**
     * 版权开启 false可以转载
     */
    @NotNull
    @Column(name = "copyright")
    private Boolean copyright = false;
    /**
     * 评论开启 false 不开启
     */
    @NotNull
    @Column(name = "comment_show")
    private Boolean commentShow = false;
    /**
     * 是否发布
     */
    @NotNull
    @Column(name = "publish")
    private Boolean publish = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    /**
     * 描述
     */
    @Column(name = "description")
    @NotNull
    private String description;
    /*
        标签
         */
    @ManyToMany
    @JoinTable(name = "blog_tags",
            joinColumns = @JoinColumn(name = "blogs_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Set<Tag> tags = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "content_id")
    private Content content;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Comment> comments = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Blog blog = (Blog) o;
        return id != null && Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}