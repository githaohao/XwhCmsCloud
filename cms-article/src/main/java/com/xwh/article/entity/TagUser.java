package com.xwh.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
