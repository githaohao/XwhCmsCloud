package com.xwh.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 把对象转成json格式字符串返回
 */
public class WriterUtil {

	/**
	 *
	 * @Description: 客户端返回JSON字符串
	 * @param @param response
	 * @param @param object
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String renderString(HttpServletResponse response, String object) {
		return renderString(response, object, "application/json");
	}

	/**
	 *
	 * @Title：renderStringDealNull
	 * @Description: 空值时也返回属性
	 * @param @param response
	 * @param @param object
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String renderStringDealNull(HttpServletResponse response, Object object) {
        return renderString(response, JSONObject.toJSONString(object,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullNumberAsZero), "application/json");
    }

	/**
	 *
	 * @Description: 客户端返回字符串
	 * @param @param response
	 * @param @param string
	 * @param @param type <br/>1、 普通文本：text/plain
				<br/>2 、HTML: text/html
				<br/>3 、XML: text/xml
				<br/>4 、javascript: text/javascript
				<br/>5、 json: application/json
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public static Object renderScript(HttpServletResponse response, String script) {
		try {
			response.reset();
			response.setContentType("text/html;charset=UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        script = "<script type=\"text/javascript\">"+script+"</script>";
			response.getWriter().print(script);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
