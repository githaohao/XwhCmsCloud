package com.xwh.article.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author xwh
 **/
@Getter
@Setter
@Entity
@Table
public class PostTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 20)
    String postId;

    @Id
    @Column(length = 20)
    String tagId;

}
