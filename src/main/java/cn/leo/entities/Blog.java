package cn.leo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "title")
    private String title;

    /**
     * 博客首图
     */
    @Column(name = "first_picture")
    private String firstPicture;
    /**
     * 博客标签
     */
    @OneToMany(mappedBy = "blog", orphanRemoval = true)
    @ToString.Exclude
    private Set<Tag> tags = new LinkedHashSet<>();
    /**
     * 博客类型
     */
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "type_id")
    private Type type;
    /**
     * 浏览次数
     */
    @Column(name = "views")
    private Integer views;

    /**
     * 赞赏开启 false不开启
     */
    @Column(name = "appreciate")
    private Boolean appreciate = false;

    /**
     * 版权开启 false可以转载
     */
    @Column(name = "copyright")
    private Boolean copyright = false;
    /**
     * 评论开启 false 不开启
     */
    @Column(name = "comment_show")
    private Boolean commentShow = false;
    /**
     * 是否发布
     */
    @Column(name = "publish")
    private Boolean publish = false;




    /**
     * 评论
     */
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content_id;

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