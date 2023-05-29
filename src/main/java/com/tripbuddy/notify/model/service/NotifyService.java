package com.tripbuddy.notify.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tripbuddy.notify.model.dto.NotifyDto;


public interface NotifyService {

	List<NotifyDto> getNotifys(Map<String, Object> req) throws Exception;
	void updateRead(Map<String, Object> req) throws Exception;
	void disableNotify(Map<String, Object> req);
	void postInviteNotify(Map<String, Object> req);
	int checkNotifyReceiver(Map<String, Object> params);
	void acceptNotify(Map<String, Object> req);
	List<NotifyDto> getRecentNotifys(Map<String, Object> req);
	int getNotreadNotifys(Map<String, Object> req);
	
}
