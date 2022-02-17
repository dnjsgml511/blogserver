package com.portfolio.blog.config.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @apiNote jwt 유틸
 * @since 2021-11-30
 * @author lwh
 */

@Component
public class JwtTokenUtil {

	private static final String secret = "board";

	public static final long JWT_TOKEN_VALIDITY = 365 *24 * 60 * 60;
	public static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60;

	// jwt token으로부터 username을 획득한다.
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getId);
	}

	// jwt token으로부터 username을 획득한다.
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// jwt token으로부터 payload를 획득한다.
	public Claims getDatasFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims;
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// 만료된 토큰 payload 복호화
	public Map<String, Object> decodeExpiredTokens(String token) throws JsonMappingException, JsonProcessingException {

		ObjectMapper om = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = om.readValue(JwtHelper.decode(token).getClaims(), Map.class);

		map.remove("exp");
		map.remove("iat");

		return map;
	}

	// request에서 토큰 뽑기
	public String popJWTtoken(HttpServletRequest request) {
		return (String) request.getHeader("Authorization").replace("Bearer ", "");
	}

	// 토큰에서 데이터 뽑기
	public String popJWTData(String token, String pop) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> map = decodeExpiredTokens(token);
		return (String) map.get(pop);
	}

	// 토큰이 만료되었는지 확인한다.
	public Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// jwt token으로부터 만료일자를 알려준다.
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// 유저를 위한 토큰을 발급해준다.
	public String generateToken(String id) {
		return generateToken(id, new HashMap<>());
	}

	// 유저를 위한 토큰을 발급해준다.
	public String generateToken(String id, Map<String, Object> claims) {
		return doGenerateToken(id, claims);
	}

	// 토큰 만료시간 설정
	private String doGenerateToken(String id, Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setId(id).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// refreshtoekn 발급
	public String generateRefreshToken(String id, Map<String, Object> claims) {
		return doGenerateRefreshToken(id, claims);
	}

	// refresh 토큰 만료시간 설정
	private String doGenerateRefreshToken(String id, Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setId(id).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// 토큰 검증
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

}