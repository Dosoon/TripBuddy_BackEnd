package com.tripbuddy.notice.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripbuddy.notice.model.dto.NoticeDto;
import com.tripbuddy.notice.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public void postNotice(NoticeDto noticeDto) throws Exception {
		noticeMapper.postNotice(noticeDto);
	}
	
	@Override
	public void modifyNotice(NoticeDto noticeDto) throws Exception {
		noticeMapper.modifyNotice(noticeDto);
	}
	
	@Override
	public List<NoticeDto> getNotices(Map<String, Object> req) throws Exception {
		return noticeMapper.getNotices(req);
	}
		
	@Override
	public NoticeDto viewNotice(int noticeId) throws Exception {
		return noticeMapper.viewNotice(noticeId);
	}
	
	@Override
	public void deleteNotice(int noticeId) throws Exception {
		noticeMapper.deleteNotice(noticeId);
	}

	@Override
	public int checkNoticeAuthor(Map<String, Object> params) {
		return noticeMapper.checkNoticeAuthor(params);
	}

	@Override
	public int totalNotices(Map<String, Object> req) {
		return noticeMapper.totalNotices(req);
	}
	
}