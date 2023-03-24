package com.xwh.core.utils;

import com.xwh.core.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 * jwt token工具类
 * </p>
 *
 * <pre>
 *     jwt的claim里一般包含以下几种数据:
 *         1. iss -- token的发行者
 *         2. sub -- 该JWT所面向的用户
 *         3. aud -- 接收该JWT的一方
 *         4. exp -- token的失效时间
 *         5. nbf -- 在此时间段之前,不会被处理
 *         6. iat -- jwt发布时间
 *         7. jti -- jwt唯一标识,防止重复使用
 * </pre>
 *
 * @Date 2019/11/25 10:59
 */
@Component
public class JwtTokenUtil {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private JwtProperties jwtProperties;

	/**
	 * 获取用户名从token中
	 */
	public String getUsernameFromToken(String token) {
		String userName = "";
		String userInfoStr = getUserInfoStrFromToken(token);
		if (StringUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 1) {
				userName = userInfoArr[1];
			}
		}
		return userName;
	}

	/**
	 * 获取用户id从token中
	 */
	public String getUserIdFromToken(String token) {
		String userId = "";
		String userInfoStr = getUserInfoStrFromToken(token);
		if (StringUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 0) {
				userId = userInfoArr[0];
			}
		}
		return userId;
	}

	/**
	 * 获取用户登录类型从token中
	 */
	public String getLoginTypeFromToken(String token) {
		String loginType = "";
		String userInfoStr = getUserInfoStrFromToken(token);
		if (StringUtils.isNotBlank(userInfoStr)) {
			String[] userInfoArr = userInfoStr.split(";;");
			if (userInfoArr.length > 2) {
				loginType = userInfoArr[2];
			}
		}
		return loginType;
	}

	/**
	 * 获取用户信息从token中
	 */
	public String getUserInfoStrFromToken(String token) {
		return getClaimFromToken(token).getSubject();
	}

	/**
	 * 获取jwt发布时间
	 */
	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token).getIssuedAt();
	}

	/**
	 * 获取jwt失效时间
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token).getExpiration();
	}

	/**
	 * 获取jwt接收者
	 */
	public String getAudienceFromToken(String token) {
		return getClaimFromToken(token).getAudience();
	}

	/**
	 * 获取私有的jwt claim
	 */
	public String getPrivateClaimFromToken(String token, String key) {
		return getClaimFromToken(token).get(key).toString();
	}

	/**
	 * 获取md5 key从token中
	 */
	public String getMd5KeyFromToken(String token) {
		return getPrivateClaimFromToken(token, jwtProperties.getMd5Key());
	}

	/**
	 * 获取jwt的payload部分
	 */
	public Claims getClaimFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
	}

	/**
	 * 解析token是否正确,不正确会报异常<br>
	 */
	public void parseToken(String token) throws JwtException {
		Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
	}

	/**
	 * <pre>
	 *  验证token是否失效
	 *  true:过期   false:没过期
	 * </pre>
	 */
	public Boolean isTokenExpired(String token) {
		boolean flag=true;
		try {
			final Date expiration = getExpirationDateFromToken(token);
			flag= expiration.before(new Date());
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("验证失败");
		}
		return flag;

	}

	/**
	 * 生成token(通过用户信息和签名时候用的随机数)
	 */
	public String generateToken(String userInfoStr, String randomKey) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(jwtProperties.getMd5Key(), randomKey);
		return doGenerateToken(claims, userInfoStr);
	}

	/**
	 * 生成token
	 */
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + jwtProperties.getExpiration() * 1000);

		byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
		Key key = Keys.hmacShaKeyFor(keyBytes);

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(key,SignatureAlgorithm.HS512).compact();
	}

	/**
	 * 获取混淆MD5签名用的随机字符串
	 */
	public String getRandomKey() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
