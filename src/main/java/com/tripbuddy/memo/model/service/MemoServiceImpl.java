package com.tripbuddy.memo.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripbuddy.memo.model.dto.MemoDto;
import com.tripbuddy.memo.model.mapper.MemoMapper;

@Service
public class MemoServiceImpl implements MemoService {

	@Autowired
	private MemoMapper memoMapper;

	@Override
	public void postMemo(MemoDto memoDto) throws Exception {
		memoMapper.postMemo(memoDto);
	}

	@Override
	public void deleteMemo(int memoId) throws Exception {
		memoMapper.deleteMemo(memoId);
	}

	@Override
	public List<MemoDto> getMemos(Map<String, Object> req) throws Exception {
		return memoMapper.getMemos(req);
	}

	@Override
	public int checkMemoAuthor(Map<String, Object> params) {
		return memoMapper.checkMemoAuthor(params);
	}

}
