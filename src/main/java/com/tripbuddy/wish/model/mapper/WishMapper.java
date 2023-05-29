package com.tripbuddy.wish.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.attraction.model.dto.AttractionDto;
import com.tripbuddy.wish.model.dto.WishDto;

@Mapper
public interface WishMapper {

	void postWish(Map<String, Object> req) throws SQLException;
	void deleteWish(Map<String, Object> req) throws SQLException;
	List<AttractionDto> getWishes(Map<String, Object> req) throws SQLException;
	int checkWish(Map<String, Object> req);

}
