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

}