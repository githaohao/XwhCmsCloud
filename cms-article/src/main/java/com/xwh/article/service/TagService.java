package com.xwh.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.article.entity.Tag;

import java.util.List;

/**
 * @author xwh
 **/
public interface  TagService extends IService<Tag> {

    /**
     * 查询当前用户的所有标签
     *
     * @return
     */
    public List<Tag> listByUser(Tag vo);

    /**
     * 保存标签
     *
     * @return
     */
    public Tag saveTag(Tag tag);

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
    boolean editUser(String userId, Tag tag);

    /**
     * 保存当前用户的标签
     * @param userId
     * @param tag
     * @return
     */
    boolean saveByUser(String userId, Tag tag);
}
