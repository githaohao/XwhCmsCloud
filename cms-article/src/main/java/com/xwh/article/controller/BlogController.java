package com.xwh.article.controller;

import com.alibaba.fastjson2.JSON;
import com.xwh.article.enums.TimeRange;
import com.xwh.article.entity.Post;
import com.xwh.article.entity.Tag;
import com.xwh.article.entity.dto.BlogUserDto;
import com.xwh.article.service.BlogService;
import com.xwh.article.service.PostService;
import com.xwh.article.service.TagService;
import com.xwh.article.service.param.PostParam;
import com.xwh.core.controller.BaseController;
import com.xwh.core.dao.Page;
import com.xwh.core.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apiguardian.api.API;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客接口
 *
 * @author xwh
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("blog")
@Api(tags = "文章:博客接口管理")
public class BlogController extends BaseController {

    final PostService postService;
    final BlogService blogService;
    final TagService tagService;


    /**
     * List result.
     *
     * @param postParam the post param
     * @return the result
     * 查询所有用户的公开文章分页模糊查询
     * */
    @ApiOperation("查询所有用户的公开文章分页模糊查询")
    @PostMapping("list")
    public Result list(@RequestBody PostParam postParam) {
        Page<Post> postPage = postService.listPage(postParam);
        return success().add(propertyDel(postPage, "password"));
    }

    /**
     * 查询单个文章
     *
     * @param postId the post id
     * @return the result
     */
    @ApiOperation("查询单个文章")
    @GetMapping("get/{postId}")
    public Result get(@PathVariable String postId) {
        BlogUserDto post = blogService.getBlog(postId);
        // 获取当前文章的用户姓名
        return success().add(propertyDel(post, "password"));
    }

    @ApiOperation("查询热门文章")
    @GetMapping("top{size}/{time}")
    public Result getTop(@PathVariable Integer size, @PathVariable String time) {
        TimeRange timeRange = TimeRange.valueOf(time.toUpperCase());
        List<Post> topBlog = blogService.getTopBlog(size, timeRange);
        // 返回指定字段
        return success().add(propertyShow(topBlog, "title", "visits", "postId", "createTime"));
    }

    @ApiOperation("查询热门标签")
    @GetMapping("topTag{size}/{time}")
    public Result getTopTag(@PathVariable Integer size, @PathVariable String time) {
        TimeRange timeRange = TimeRange.valueOf(time.toUpperCase());
        List<Tag> topTag = blogService.getTopTag(size, timeRange);
        // 返回指定字段
        return success().add(propertyShow(topTag, "name", "visits", "tagId", "color"));
    }

    @ApiOperation("点赞文章")
    @GetMapping("like/{postId}")
    public Result like(@PathVariable String postId) {
        blogService.like(postId);
        return success();
    }

    @ApiOperation("取消点赞文章")
    @GetMapping("unlike/{postId}")
    public Result unlike(@PathVariable String postId) {
        blogService.unlike(postId);
        return success();
    }
    /**
     * 获取全部标签
     */
    @ApiOperation("获取全部标签")
    @GetMapping("allTag")
    public Result getAllTag() {
        List<Tag> list = tagService.list();
        // 返回指定字段
        return success().add(propertyShow(list, "name", "visits", "tagId", "color"));
    }

    @ApiOperation("工具列表")
    @GetMapping("tools")
    public Result tools() {
        String arr = "[{\"id\":\"1002601662180753408\",\"typeId\":0,\"name\":\"视频\",\"pinyin\":\"shipin\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:04.000+00:00\",\"updateTime\":\"2022-07-29T07:41:04.000+00:00\"},{\"id\":\"1002601784557961216\",\"typeId\":0,\"name\":\"图片\",\"pinyin\":\"tupian\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:33.000+00:00\",\"updateTime\":\"2022-07-29T07:41:33.000+00:00\"},{\"id\":\"1002601822235394048\",\"typeId\":0,\"name\":\"文本\",\"pinyin\":\"wenben\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:42.000+00:00\",\"updateTime\":\"2022-07-29T07:41:42.000+00:00\"},{\"id\":\"1006581703679934464\",\"typeId\":0,\"name\":\"程序员\",\"pinyin\":\"chengxuyuan\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-08-09T07:16:20.000+00:00\",\"updateTime\":\"2022-08-09T07:16:20.000+00:00\"},{\"id\":\"1046429574805585920\",\"typeId\":0,\"name\":\"办公\",\"pinyin\":\"office\",\"description\":\"办公类小工具，白领必备的工具\",\"keywords\":\"办公工具\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-11-27T06:17:32.000+00:00\",\"updateTime\":\"2022-11-27T06:17:32.000+00:00\"}]";
        return success().add(JSON.parseArray(arr));
    }

    @ApiOperation("通过类型获取工具列表")
    @GetMapping("tools/{typeId}")
    public Result tools(@PathVariable Integer typeId) {
        String arr = "[{\"id\":\"1059431116450037760\",\"title\":\"M3U8视频下载V2Pro版\",\"category_id\":\"1002601662180753408\",\"image\":\"tool-M3U8V2Pro.svg\",\"content\":\"M3U8V2Pro\",\"type\":0,\"state\":\"0\",\"summary\":\"全新的M3U8下载工具\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2023-01-02T03:21:01.000+00:00\",\"updateTime\":\"2023-01-02 11:21:01\",\"category\":{\"id\":\"1002601662180753408\",\"typeId\":0,\"name\":\"视频\",\"pinyin\":\"shipin\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:04.000+00:00\",\"updateTime\":\"2022-07-29T07:41:04.000+00:00\"}},{\"id\":\"1046432938494787584\",\"title\":\"Word转PDF\",\"category_id\":\"1046429574805585920\",\"image\":\"tool-WordToPDF.svg\",\"content\":\"WordToPDF\",\"type\":0,\"state\":\"0\",\"summary\":\"doc、docx转pdf\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-11-27T06:30:54.000+00:00\",\"updateTime\":\"2022-11-27 02:30:54\",\"category\":{\"id\":\"1046429574805585920\",\"typeId\":0,\"name\":\"办公\",\"pinyin\":\"office\",\"description\":\"办公类小工具，白领必备的工具\",\"keywords\":\"办公工具\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-11-27T06:17:32.000+00:00\",\"updateTime\":\"2022-11-27T06:17:32.000+00:00\"}},{\"id\":\"1038450689891631104\",\"title\":\"Png转SVG\",\"category_id\":\"1002601784557961216\",\"image\":\"tool-PngToSvg.svg\",\"content\":\"PngToSvg\",\"type\":0,\"state\":\"0\",\"summary\":\"把Png格式转换成SVG格式\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-11-05T05:52:18.000+00:00\",\"updateTime\":\"2022-11-05 01:52:18\",\"category\":{\"id\":\"1002601784557961216\",\"typeId\":0,\"name\":\"图片\",\"pinyin\":\"tupian\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:33.000+00:00\",\"updateTime\":\"2022-07-29T07:41:33.000+00:00\"}},{\"id\":\"1034463691191353344\",\"title\":\"文字里的秘密\",\"category_id\":\"1002601822235394048\",\"image\":\"tool-TextSecret.svg\",\"content\":\"TextSecret\",\"type\":0,\"state\":\"0\",\"summary\":\"把一段文字隐藏在另一段文字中\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-10-25T05:49:23.000+00:00\",\"updateTime\":\"2022-10-25 01:49:23\",\"category\":{\"id\":\"1002601822235394048\",\"typeId\":0,\"name\":\"文本\",\"pinyin\":\"wenben\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:42.000+00:00\",\"updateTime\":\"2022-07-29T07:41:42.000+00:00\"}},{\"id\":\"1033424410448494592\",\"title\":\"M3U8视频在线下载\",\"category_id\":\"1002601662180753408\",\"image\":\"tool-DownloadM3U8.svg\",\"content\":\"DownloadM3U8\",\"type\":0,\"state\":\"0\",\"summary\":\"M3U8视频在线下载合成Mp4\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-10-22T08:59:40.000+00:00\",\"updateTime\":\"2022-10-22 04:59:40\",\"category\":{\"id\":\"1002601662180753408\",\"typeId\":0,\"name\":\"视频\",\"pinyin\":\"shipin\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:04.000+00:00\",\"updateTime\":\"2022-07-29T07:41:04.000+00:00\"}},{\"id\":\"1023257289349398528\",\"title\":\"国庆头像生成器\",\"category_id\":\"1002601784557961216\",\"image\":\"tool-NationalDayAvatar.png\",\"content\":\"NationalDayAvatar\",\"type\":0,\"state\":\"0\",\"summary\":\"一键生成国庆头像\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-09-24T07:39:09.000+00:00\",\"updateTime\":\"2022-09-24 03:39:09\",\"category\":{\"id\":\"1002601784557961216\",\"typeId\":0,\"name\":\"图片\",\"pinyin\":\"tupian\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:33.000+00:00\",\"updateTime\":\"2022-07-29T07:41:33.000+00:00\"}},{\"id\":\"1014911450298187776\",\"title\":\"Base64编码解码\",\"category_id\":\"1002601822235394048\",\"image\":\"tool-Base64Convert.svg\",\"content\":\"Base64Convert\",\"type\":0,\"state\":\"0\",\"summary\":\"Base64加密解密,编码解码\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-09-01T06:55:46.000+00:00\",\"updateTime\":\"2022-09-01 02:55:46\",\"category\":{\"id\":\"1002601822235394048\",\"typeId\":0,\"name\":\"文本\",\"pinyin\":\"wenben\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:42.000+00:00\",\"updateTime\":\"2022-07-29T07:41:42.000+00:00\"}},{\"id\":\"1012120570432585728\",\"title\":\"Unicode中文互转\",\"category_id\":\"1002601822235394048\",\"image\":\"tool-UnicodeConvert.svg\",\"content\":\"UnicodeConvert\",\"type\":0,\"state\":\"0\",\"summary\":\"Unicode与中文互转工具\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-08-24T14:05:48.000+00:00\",\"updateTime\":\"2022-08-24 10:05:48\",\"category\":{\"id\":\"1002601822235394048\",\"typeId\":0,\"name\":\"文本\",\"pinyin\":\"wenben\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:42.000+00:00\",\"updateTime\":\"2022-07-29T07:41:42.000+00:00\"}},{\"id\":\"1012012433608278016\",\"title\":\"CSS渐变背景工具\",\"category_id\":\"1006581703679934464\",\"image\":\"tool-CSSGradient.svg\",\"content\":\"CSSGradient\",\"type\":0,\"state\":\"0\",\"summary\":\"可以快速调试出漂亮的渐变色\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-08-24T06:56:06.000+00:00\",\"updateTime\":\"2022-08-24 02:56:06\",\"category\":{\"id\":\"1006581703679934464\",\"typeId\":0,\"name\":\"程序员\",\"pinyin\":\"chengxuyuan\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-08-09T07:16:20.000+00:00\",\"updateTime\":\"2022-08-09T07:16:20.000+00:00\"}},{\"id\":\"1006583037296640000\",\"title\":\"CSS边框可视化\",\"category_id\":\"1006581703679934464\",\"image\":\"tool-FancyBorderRadius.svg\",\"content\":\"FancyBorderRadius\",\"type\":0,\"state\":\"0\",\"summary\":\"拖拽式可视化调整CSS边框样式\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-08-09T07:21:38.000+00:00\",\"updateTime\":\"2022-08-09 03:21:38\",\"category\":{\"id\":\"1006581703679934464\",\"typeId\":0,\"name\":\"程序员\",\"pinyin\":\"chengxuyuan\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-08-09T07:16:20.000+00:00\",\"updateTime\":\"2022-08-09T07:16:20.000+00:00\"}},{\"id\":\"1005595037087563776\",\"title\":\"造新词生成器\",\"category_id\":\"1002601784557961216\",\"image\":\"tool-MakePhrase.svg\",\"content\":\"MakePhrase\",\"type\":0,\"state\":\"0\",\"summary\":\"造新词生成器，田字格成语生成器\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-08-06T13:55:40.000+00:00\",\"updateTime\":\"2022-08-06 09:55:40\",\"category\":{\"id\":\"1002601784557961216\",\"typeId\":0,\"name\":\"图片\",\"pinyin\":\"tupian\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:33.000+00:00\",\"updateTime\":\"2022-07-29T07:41:33.000+00:00\"}},{\"id\":\"1003659040141606912\",\"title\":\"在线录屏\",\"category_id\":\"1002601662180753408\",\"image\":\"tool-ScreenRec.svg\",\"content\":\"ScreenRec\",\"type\":0,\"state\":\"0\",\"summary\":\"在线屏幕录制,录屏+麦克风免费在线录制\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-08-01T05:42:42.000+00:00\",\"updateTime\":\"2022-08-01 01:42:42\",\"category\":{\"id\":\"1002601662180753408\",\"typeId\":0,\"name\":\"视频\",\"pinyin\":\"shipin\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:04.000+00:00\",\"updateTime\":\"2022-07-29T07:41:04.000+00:00\"}},{\"id\":\"1002612073852567552\",\"title\":\"微博图片生成\",\"category_id\":\"1002601784557961216\",\"image\":\"tool-WeiBoGenerates.svg\",\"content\":\"WeiBoGenerates\",\"type\":0,\"state\":\"0\",\"summary\":\"名人微博图片生成,生成微博图片\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-07-29T08:22:26.000+00:00\",\"updateTime\":\"2022-07-29 04:22:26\",\"category\":{\"id\":\"1002601784557961216\",\"typeId\":0,\"name\":\"图片\",\"pinyin\":\"tupian\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:33.000+00:00\",\"updateTime\":\"2022-07-29T07:41:33.000+00:00\"}},{\"id\":\"1002611903198920704\",\"title\":\"文本去重\",\"category_id\":\"1002601822235394048\",\"image\":\"tool-TextDistinct.svg\",\"content\":\"TextDistinct\",\"type\":0,\"state\":\"0\",\"summary\":\"文本去重工具，批量去重\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-07-29T08:21:45.000+00:00\",\"updateTime\":\"2022-07-29 04:21:45\",\"category\":{\"id\":\"1002601822235394048\",\"typeId\":0,\"name\":\"文本\",\"pinyin\":\"wenben\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:42.000+00:00\",\"updateTime\":\"2022-07-29T07:41:42.000+00:00\"}},{\"id\":\"1002611667952992256\",\"title\":\"NPlayer播放器\",\"category_id\":\"1002601662180753408\",\"image\":\"tool-NPlayer.svg\",\"content\":\"NPlayer\",\"type\":0,\"state\":\"0\",\"summary\":\"支持接入任何流媒体：hls、flv、dash\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-07-29T08:20:49.000+00:00\",\"updateTime\":\"2022-07-29 04:20:49\",\"category\":{\"id\":\"1002601662180753408\",\"typeId\":0,\"name\":\"视频\",\"pinyin\":\"shipin\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:04.000+00:00\",\"updateTime\":\"2022-07-29T07:41:04.000+00:00\"}},{\"id\":\"1002611366755827712\",\"title\":\"flv直播播放器\",\"category_id\":\"1002601662180753408\",\"image\":\"tool-FlvPlayer.svg\",\"content\":\"FlvPlayer\",\"type\":0,\"state\":\"0\",\"summary\":\"flv播放器，flv拉流测试\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-07-29T08:19:37.000+00:00\",\"updateTime\":\"2022-07-29 04:19:37\",\"category\":{\"id\":\"1002601662180753408\",\"typeId\":0,\"name\":\"视频\",\"pinyin\":\"shipin\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:04.000+00:00\",\"updateTime\":\"2022-07-29T07:41:04.000+00:00\"}},{\"id\":\"1002611058881331200\",\"title\":\"图片转Base64\",\"category_id\":\"1002601784557961216\",\"image\":\"tool-ImageToBase64.svg\",\"content\":\"ImageToBase64\",\"type\":0,\"state\":\"0\",\"summary\":\"图片转Base64，Base64转图片\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-07-29T08:18:24.000+00:00\",\"updateTime\":\"2022-07-29 04:18:24\",\"category\":{\"id\":\"1002601784557961216\",\"typeId\":0,\"name\":\"图片\",\"pinyin\":\"tupian\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:33.000+00:00\",\"updateTime\":\"2022-07-29T07:41:33.000+00:00\"}},{\"id\":\"1002610633432104960\",\"title\":\"时间戳在线转换\",\"category_id\":\"1002601822235394048\",\"image\":\"tool-timestamp.svg\",\"content\":\"Timestamp\",\"type\":0,\"state\":\"0\",\"summary\":\"时间戳转日期，日期转时间戳，获取时间戳\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-07-29T08:16:43.000+00:00\",\"updateTime\":\"2022-07-29 04:16:43\",\"category\":{\"id\":\"1002601822235394048\",\"typeId\":0,\"name\":\"文本\",\"pinyin\":\"wenben\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:42.000+00:00\",\"updateTime\":\"2022-07-29T07:41:42.000+00:00\"}},{\"id\":\"1002610122695901184\",\"title\":\"英文大小写转换\",\"category_id\":\"1002601822235394048\",\"image\":\"tool-e2E.svg\",\"content\":\"EnglistConvert\",\"type\":0,\"state\":\"0\",\"summary\":\"大小写转换，首字母大写，首字母小写\",\"labels\":\"\",\"viewCount\":0,\"createTime\":\"2022-07-29T08:14:41.000+00:00\",\"updateTime\":\"2022-07-29 04:14:41\",\"category\":{\"id\":\"1002601822235394048\",\"typeId\":0,\"name\":\"文本\",\"pinyin\":\"wenben\",\"description\":\"\",\"keywords\":\"\",\"sort\":0,\"state\":\"1\",\"createTime\":\"2022-07-29T07:41:42.000+00:00\",\"updateTime\":\"2022-07-29T07:41:42.000+00:00\"}}]";
        return success().add("content", JSON.parseArray(arr));
    }

}
