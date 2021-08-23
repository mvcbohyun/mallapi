package com.kinsight.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kinsight.bean.BoardDetailReturnBean;
import com.kinsight.bean.BoardReturnBean;
import com.kinsight.bean.CategoryReturnBean;
import com.kinsight.domain.Board;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.param.BoardSearchParam;

@Repository
public interface BoardRepository {

	List<BoardReturnBean> search(PageRequestParameter<BoardSearchParam> pageRequestParameter);

	void save(Board board);

	BoardDetailReturnBean detail(@Param("id")Long id, @Param("saveid")Long saveid);

	void update(Board board);
	 
	int delete(@Param("id")Long id, @Param("saveid")Long saveid);

	CategoryReturnBean category();

	int allcount();

}
