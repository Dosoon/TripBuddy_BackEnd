package com.tripbuddy.memo.model.service;

import java.util.List;
import java.util.Map;

import com.tripbuddy.memo.model.dto.MemoDto;

public interface MemoService {

	void postMemo(MemoDto memoDto) throws Exception;
	void deleteMemo(int memoId) throws Exception;
	List<MemoDto> getMemos(Map<String, Object> req) throws Exception;
	int checkMemoAuthor(Map<String, Object> params);

}
