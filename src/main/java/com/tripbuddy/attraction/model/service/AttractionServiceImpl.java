package com.tripbuddy.attraction.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripbuddy.attraction.model.dto.AreaDto;
import com.tripbuddy.attraction.model.dto.AttractionDto;
import com.tripbuddy.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {

	@Autowired
	AttractionMapper attractionMapper;
		
	@Override
	public List<AreaDto> getSidoAreaCodes() throws Exception {
		return attractionMapper.getSidoAreaCodes();
	}
	
	@Override
	public List<AreaDto> getGugunAreaCodes(int sido) throws Exception {
		return attractionMapper.getGugunAreaCodes(sido);
	}
   
	
	@Override
	public List<AreaDto> search(Map<String, Object> req) throws Exception {
		req.put("offset", ((Integer.parseInt((String) req.get("page")))-1)*10);
		
		return attractionMapper.search(req);
	}
	
	
	@Override
	public AttractionDto attractionInfo(int contentId) throws Exception {
		return attractionMapper.attractionInfo(contentId);
	}
	
	
}
