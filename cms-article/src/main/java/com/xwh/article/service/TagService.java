package com.xwh.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.article.entity.TagEntity;

import java.util.List;

/**
 * @author xwh
 **/
public interface  TagService extends IService<TagEntity> {

    /**
     * 查询当前用户的所有标签
     *
     * @return
     */
    public List<TagEntity> listByUser(TagEntity vo);

    /**
     * 保存标签
     *
     * @return
     */
    public TagEntity saveTag(TagEntity tag);

    /**
     * 删除该角色的标签
     *
     * @param id
     */
    public void deleteByUser(String id);

    /**
     * 通过用户id 修改标签
     * @param userId
     * @param tag
     * @return
     */
    boolean editUser(String userId, TagEntity tag);

    /**
     * 保存当前用户的标签
     * @param userId
     * @param tag
     * @return
     */
    boolean saveByUser(String userId, TagEntity tag);

}
