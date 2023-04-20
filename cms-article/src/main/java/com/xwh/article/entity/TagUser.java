package com.xwh.article.entity;

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
public class TagUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 20)
    String tagId;

    @Id
    @Column(length = 20)
    String userId;
}
