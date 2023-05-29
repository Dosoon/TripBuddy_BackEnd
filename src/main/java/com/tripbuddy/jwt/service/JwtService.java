package com.tripbuddy.jwt.service;

import java.util.Map;

public interface JwtService {

	<T> String createAccessToken(String key, T data);

	<T> String createRefreshToken(String key, T data);

	<T> String create(String key, T data, String subject, long expir);

	Map<String, Object> get(String key) throws Exception;

	boolean checkToken(String jwt);

}
