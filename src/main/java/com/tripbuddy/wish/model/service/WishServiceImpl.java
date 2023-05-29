package com.tripbuddy.wish.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripbuddy.attraction.model.dto.AttractionDto;
import com.tripbuddy.wish.model.dto.WishDto;
import com.tripbuddy.wish.model.mapper.WishMapper;

@Service
public class WishServiceImpl implements WishService {

	@Autowired
	private WishMapper wishMapper;
	
	@Override
	public void postWish(Map<String, Object> req) throws Exception {
		wishMapper.postWish(req);
	}

	@Override
	public void deleteWish(Map<String, Object> req) throws Exception {
		wishMapper.deleteWish(req);
	}

	@Override
	public List<AttractionDto> getWishes(Map<String, Object> req) throws Exception {
		return wishMapper.getWishes(req);
	}

	@Override
	public int checkWish(Map<String, Object> req) {
		return wishMapper.checkWish(req);
	}

}
