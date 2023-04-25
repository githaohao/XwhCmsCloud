package com.xwh.core.utils;


import jakarta.servlet.http.HttpServletRequest;

/**
 * @author xiangwenhao
 */
public class TokenUtil {



	/**
	 * 获取jwt authorization
	 */
	public static String getAuthorization() {
		HttpServletRequest request = ServletUtils.getRequest();
		return request.getHeader("Authorization");
	}

	/**
	 * 获取jwt token
	 */
	public static String getToken() {
		String token = "";
		String authorization = getAuthorization();
		if (BlankUtils.isNotBlank(authorization) && authorization.startsWith("Bearer ")) {
			token = authorization.substring(7);
		}
		return token;
	}

	/**
	 * 获取userInfoStr
	 */
	public static String getUserInfoStr() {
		HttpServletRequest request = ServletUtils.getRequest();
		return request.getHeader("userInfo");
	}

	/**
	 * 获取userInfoStr
	 */
//	public static String getUserInfoStr() {
//		JwtTokenUtil jwtTokenUtil = SpringUtil.getBean("jwtTokenUtil");
//		return jwtTokenUtil.getUserInfoStrFromToken(getToken());
//	}

	/**
	 * 获取登录用户id
	 */
	public static String getUserId() {
		String userId = "";
		String userInfoStr = getUserInfoStr();
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 0) {
				userId = userInfoArr[0];
			}
		}
		return userId;
	}


	/**
	 * 获取登录用户id
	 */
	public static String getUserId(String userInfoStr) {
		String userId = "";
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 0) {
				userId = userInfoArr[0];
			}
		}
		return userId;
	}


	/**
	 * 获取用户登录类型
	 */
	public static int getLoginType() {
		String loginType = "";
		String userInfoStr = getUserInfoStr();
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 2) {
				loginType = userInfoArr[2];
			}
		}
		return Integer.parseInt(loginType);
	}


	public static int getLoginType(String userInfoStr) {
		String loginType = "";
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 2) {
				loginType = userInfoArr[2];
			}
		}
		return Integer.parseInt(loginType);
	}


	/**
	 * 获取登录用户名
	 */
	public static String getUsernameFromToken() {
		String userName = "";
		String userInfoStr = getUserInfoStr();
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 1) {
				userName = userInfoArr[1];
			}
		}
		return userName;
	}


	/**
	 * 获取登录用户名
	 */
	public static String getUsernameFromToken(String userInfoStr) {
		String userName = "";
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 1) {
				userName = userInfoArr[1];
			}
		}
		return userName;
	}


	/**
	 * 获取是否为超级管理员
	 */
	public static String getUserIsAdmin() {
		String userName = "";
		String userInfoStr = getUserInfoStr();
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 3) {
				userName = userInfoArr[3];
			}
		}
		return userName;
	}



	/**
	 * 获取是否为超级管理员
	 */
	public static String getUserIsAdmin(String userInfoStr) {
		String userName = "";
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 3) {
				userName = userInfoArr[3];
			}
		}
		return userName;
	}


	/**
	 * 获取用户的租户id
	 */
	public static String getUserTenant(String userInfoStr) {
		String userName = "";
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 4) {
				userName = userInfoArr[4];
			}
		}
		return userName;
	}


	/**
	 * 获取是否为超级管理员
	 */
	public static String getUserTenant() {
		String userName = "";
		String userInfoStr = getUserInfoStr();
		if (BlankUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 4) {
				userName = userInfoArr[4];
			}
		}
		return userName;
	}




}
