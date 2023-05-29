package com.tripbuddy.plan.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.plan.model.dto.CourseDto;
import com.tripbuddy.plan.model.dto.PlanDto;

@Mapper
public interface PlanMapper {

	List<CourseDto> getCourseInfo(Map<String, Object> req);
	List<PlanDto> listPlanByUserWithThumbnail(int userId);
	List<PlanDto> listPlanByUser(int userId);
	PlanDto getPlanInfo(int planId);
	void postPlan(Map<String, Object> req);
	void postPlanMember(Map<String, Object> req);
	void deletePlanMember(Map<String, Object> req);
	void scrapPlan(Map<String, Object> req);
	void postPlanWithTitle(Map<String, Object> req);
	void resetPlan(Map<String, Object> req);
	void modifyPlan(Map<String, Object> req);
	void updatePlanInfo(Map<String, Object> req);
	int checkPlanMember(Map<String, Object> params);
	int getPlanJoinInfo(Map<String, Integer> req);
	List<PlanDto> getPlanList();
	
}
