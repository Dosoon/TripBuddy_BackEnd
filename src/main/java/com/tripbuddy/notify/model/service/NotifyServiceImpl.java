package com.tripbuddy.notify.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripbuddy.notify.model.dto.NotifyDto;
import com.tripbuddy.notify.model.mapper.NotifyMapper;

@Service
public class NotifyServiceImpl implements NotifyService {
	
	@Autowired
	private NotifyMapper notifyMapper;
	
	
	@Override
	public List<NotifyDto> getNotifys(Map<String, Object> req) throws Exception {
		return notifyMapper.getNotifys(req);
	}
	
	
	
	@Override
	public void updateRead(Map<String, Object> req) throws Exception {
		notifyMapper.updateRead(req);
	}



	@Override
	public void disableNotify(Map<String, Object> req) {
		notifyMapper.disableNotify(req);
	}



	@Override
	public void postInviteNotify(Map<String, Object> req) {
		notifyMapper.postInviteNotify(req);
	}



	@Override
	public int checkNotifyReceiver(Map<String, Object> params) {
		return notifyMapper.checkNotifyReceiver(params);
	}



	@Override
	public void acceptNotify(Map<String, Object> req) {
		notifyMapper.acceptNotify(req);
	}



	@Override
	public List<NotifyDto> getRecentNotifys(Map<String, Object> req) {
		return notifyMapper.getRecentNotifys(req);
	}



	@Override
	public int getNotreadNotifys(Map<String, Object> req) {
		return notifyMapper.getNotreadNotifys(req);
	}

}
