package com.tripbuddy.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.notice.model.dto.NoticeDto;

@Mapper
public interface NoticeMapper {

	void postNotice(NoticeDto noticeDto) throws SQLException;

	void modifyNotice(NoticeDto noticeDto) throws SQLException;

	List<NoticeDto> getNotices(Map<String, Object> req) throws SQLException;

	NoticeDto viewNotice(int noticeId) throws SQLException;

	void deleteNotice(int noticeId) throws SQLException;

	int checkNoticeAuthor(Map<String, Object> params);

	int totalNotices(Map<String, Object> req);

}
