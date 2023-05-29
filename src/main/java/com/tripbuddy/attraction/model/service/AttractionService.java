package com.tripbuddy.attraction.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tripbuddy.attraction.model.dto.AreaDto;
import com.tripbuddy.attraction.model.dto.AttractionDto;

public interface AttractionService {

	List<AreaDto> getSidoAreaCodes() throws Exception;
	List<AreaDto> getGugunAreaCodes(int sido) throws Exception;
	List<AreaDto> search( Map<String, Object> req) throws Exception;
	AttractionDto attractionInfo(int contentId) throws Exception;

}
