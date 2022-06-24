package cn.leo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {


    @JsonIgnore
    @CreatedDate
    @Column(name = "create_time", unique = true)
    private Date createDate;
    @JsonIgnore
    @Column(name = "update_time", unique = true)
    @LastModifiedDate
    private Date updateDate;


}
