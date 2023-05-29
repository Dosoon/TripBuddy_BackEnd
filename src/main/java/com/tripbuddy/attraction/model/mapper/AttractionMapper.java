package com.tripbuddy.attraction.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.attraction.model.dto.AreaDto;
import com.tripbuddy.attraction.model.dto.AttractionDto;

@Mapper
public interface AttractionMapper {

	List<AreaDto> getSidoAreaCodes() throws SQLException;
	List<AreaDto> getGugunAreaCodes(int sido) throws SQLException;
	List<AreaDto> search(Map<String, Object> req) throws SQLException;
	AttractionDto attractionInfo(int contentId) throws SQLException;
}
