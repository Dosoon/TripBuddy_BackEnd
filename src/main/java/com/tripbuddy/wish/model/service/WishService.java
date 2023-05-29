package com.tripbuddy.wish.model.service;

import java.util.List;
import java.util.Map;

import com.tripbuddy.attraction.model.dto.AttractionDto;
import com.tripbuddy.wish.model.dto.WishDto;

public interface WishService {

	void postWish(Map<String, Object> req) throws Exception;
	void deleteWish(Map<String, Object> req) throws Exception;
	List<AttractionDto> getWishes(Map<String, Object> req) throws Exception;
	int checkWish(Map<String, Object> req);

}
