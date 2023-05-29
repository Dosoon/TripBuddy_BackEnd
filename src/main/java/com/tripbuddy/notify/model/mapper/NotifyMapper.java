package com.tripbuddy.notify.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.notify.model.dto.NotifyDto;

@Mapper
public interface NotifyMapper {

	List<NotifyDto> getNotifys(Map<String, Object> req) throws SQLException;
	void updateRead(Map<String, Object> req) throws SQLException;
	void disableNotify(Map<String, Object> req);
	void postInviteNotify(Map<String, Object> req);
	int checkNotifyReceiver(Map<String, Object> params);
	void acceptNotify(Map<String, Object> req);
	List<NotifyDto> getRecentNotifys(Map<String, Object> req);
	int getNotreadNotifys(Map<String, Object> req);

}
