package com.tripbuddy.memo.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tripbuddy.memo.model.dto.MemoDto;

@Mapper
public interface MemoMapper {

	void postMemo(MemoDto memoDto) throws SQLException;
	void deleteMemo(int memoId) throws SQLException;
	List<MemoDto> getMemos(Map<String, Object> req) throws SQLException;
	int checkMemoAuthor(Map<String, Object> params);

}
