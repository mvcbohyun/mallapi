package com.kinsight.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kinsight.bean.CommentsReturnBean;
import com.kinsight.domain.Comments;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.param.CommentsSaveParam;
import com.kinsight.param.CommentsSearchParam;

@Repository
public interface CommentsRepository {

	void save(Comments comments);

	List<CommentsReturnBean> search(PageRequestParameter<CommentsSearchParam> pageRequestParameter);

	void sequpdate(Long commentid);

	int commentsave(CommentsSaveParam commentsSaveParam);

	void update(Comments comments);

	int delete(@Param("id")Long id, @Param("saveid")Long saveid);

	void boardiddelete(Long id);
	
	

}
