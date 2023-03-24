package com.xwh.system.entity.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MenuVo implements Serializable {

    private String name;

    private String path;

    private Boolean hidden;

    private String icon;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVo meta;

    private List<MenuVo> children;
}
