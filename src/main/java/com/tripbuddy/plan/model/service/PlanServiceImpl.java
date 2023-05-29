package com.tripbuddy.plan.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripbuddy.plan.model.dto.CourseDto;
import com.tripbuddy.plan.model.dto.PlanDto;
import com.tripbuddy.plan.model.mapper.PlanMapper;

@Service
public class PlanServiceImpl implements PlanService {

	@Autowired
	private PlanMapper planMapper;

	@Override
	public List<CourseDto> getCourseInfo(Map<String, Object> req) {
		return planMapper.getCourseInfo(req);
	}

	@Override
	public List<PlanDto> listPlanByUserWithThumbnail(int userId) {
		return planMapper.listPlanByUserWithThumbnail(userId);
	}

	@Override
	public List<PlanDto> listPlanByUser(int userId) {
		return planMapper.listPlanByUser(userId);
	}

	@Override
	public PlanDto getPlanInfo(int planId) {
		return planMapper.getPlanInfo(planId);
	}

	@Transactional
	@Override
	public PlanDto postPlan(Map<String, Object> req) {
		planMapper.postPlan(req);
		planMapper.postPlanMember(req);
		return planMapper.getPlanInfo((int) req.get("planId"));
	}

	@Override
	public void deletePlanMember(Map<String, Object> req) {
		planMapper.deletePlanMember(req);
	}

	@Override
	public void postPlanMember(Map<String, Object> req) {
		planMapper.postPlanMember(req);
	}

	@Transactional
	@Override
	public void scrapPlan(Map<String, Object> req) {
		planMapper.postPlanWithTitle(req);
		int originPlan = (int) req.get("planId");
		int copyPlan = (int) req.get("newPlanId");
		req.put("planId", copyPlan);
		planMapper.postPlanMember(req);
		req.put("planId", originPlan);
		planMapper.scrapPlan(req);
	}

	@Transactional
	@Override
	public void modifyPlan(Map<String, Object> req) {
		planMapper.resetPlan(req);
		if (req.get("title") != null || req.get("startDate") != null || req.get("endDate") != null)
			planMapper.updatePlanInfo(req);
		if (((List<Object>) req.get("courses")).size() > 0)
			planMapper.modifyPlan(req);
	}

	@Override
	public int checkPlanMember(Map<String, Object> params) {
		return planMapper.checkPlanMember(params);
	}

	@Override
	public boolean getPlanJoinInfo(Map<String, Integer> req) {
		if (planMapper.getPlanJoinInfo(req) == 0)
			return false;
		return true;
	}
	
	@Override
	public List<PlanDto> getPlanList() {
		return  planMapper.getPlanList();
	}
}
