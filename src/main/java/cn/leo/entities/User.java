package cn.leo.entities;

import javax.persistence.*;

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
    @Column(name = "username")
    private String username;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    /**
     * 邮箱
     */
    @Column(name = "mail")
    private String mail;
    /**
     * 描述
     */
    @Column(name = "describe")
    private String describe;
    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;
}