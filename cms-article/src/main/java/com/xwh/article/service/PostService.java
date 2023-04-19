package com.xwh.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xwh.article.entity.Post;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.dao.Page;

/**
 * @author xwh
 **/
public interface PostService extends IService<Post> {

    /**
     * 保存当前用户的文章
     * @param vo
     */
    public void saveByUser(Post vo);

    /**
     * 保存文章的标签
     * @param postId
     * @param arr
     */
    public void savePostTags(String postId, String arr);


    /**
     * 查询当前用户的的文章列表
     *
     * @param param
     * @return
     */
    public Page<Post> postByUser(PostParam param);


    /**
     * 查询单个文章
     *
     * @param postId
     * @return
     */
    Post getPost(String postId);


    /**
     * 查询文章列表
     *
     * @param page
     * @param query
     * @return
     */
    Page<Post> listPage(PostParam postParam);
}
