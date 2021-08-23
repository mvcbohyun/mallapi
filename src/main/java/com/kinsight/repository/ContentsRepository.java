package com.kinsight.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kinsight.bean.CategoryReturnBean;
import com.kinsight.bean.ContentsDetailReturnBean;
import com.kinsight.bean.ContentsReturnBean;
import com.kinsight.domain.Contents;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.param.ContentsSearchParam;

@Repository
public interface ContentsRepository {

	void save(Contents contents);

	List<ContentsReturnBean> search(PageRequestParameter<ContentsSearchParam> pageRequestParameter);

	ContentsDetailReturnBean detail(Long id);

	int delete(@Param("id")Long id, @Param("saveid")Long saveid);

	int update(Contents contents);

	List<CategoryReturnBean> categoryl();

	List<CategoryReturnBean> categorym();

}
