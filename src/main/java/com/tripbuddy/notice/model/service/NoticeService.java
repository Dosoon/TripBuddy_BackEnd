package com.tripbuddy.notice.model.service;

import java.util.List;
import java.util.Map;

import com.tripbuddy.notice.model.dto.NoticeDto;

public interface NoticeService {

	void postNotice(NoticeDto noticeDto) throws Exception;

	void modifyNotice(NoticeDto noticeDto) throws Exception;

	List<NoticeDto> getNotices(Map<String, Object> req) throws Exception;

	NoticeDto viewNotice(int noticeId) throws Exception;

	void deleteNotice(int noticeId) throws Exception;

	int checkNoticeAuthor(Map<String, Object> params);

	int totalNotices(Map<String, Object> req);


}
