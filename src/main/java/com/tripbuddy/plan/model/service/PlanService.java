package com.tripbuddy.plan.model.service;

import java.util.List;
import java.util.Map;

import com.tripbuddy.plan.model.dto.CourseDto;
import com.tripbuddy.plan.model.dto.PlanDto;

public interface PlanService {

	List<CourseDto> getCourseInfo(Map<String, Object> req);
	List<PlanDto> listPlanByUserWithThumbnail(int userId);
	List<PlanDto> listPlanByUser(int userId);
	PlanDto getPlanInfo(int planId);
	PlanDto postPlan(Map<String, Object> req);
	void deletePlanMember(Map<String, Object> req);
	void postPlanMember(Map<String, Object> req);
	void scrapPlan(Map<String, Object> req);
	void modifyPlan(Map<String, Object> req);
	int checkPlanMember(Map<String, Object> params);
	boolean getPlanJoinInfo(Map<String, Integer> req);
	List<PlanDto> getPlanList();

}
