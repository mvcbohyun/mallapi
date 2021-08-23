package com.kinsight.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kinsight.bean.BoardReturnBean;
import com.kinsight.bean.CommentsReturnBean;
import com.kinsight.controller.RestCommentController;
import com.kinsight.domain.Comments;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.param.CommentsSaveParam;
import com.kinsight.param.CommentsSearchParam;
import com.kinsight.repository.BoardRepository;
import com.kinsight.repository.CommentsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentsService {
	
	private final CommentsRepository commentsRepository;
	
	public void save(CommentsSaveParam commentsSaveParam) {
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strNowDate = simpleDateFormat.format(new Date());
		
		Comments comments = Comments.builder().content(commentsSaveParam.getContent())
				  							  .boardid(commentsSaveParam.getBoardid())
				  							  .build();
		
		System.out.println("111111111111111111111111111111111111");
		System.out.println(commentsSaveParam.getId());
		if(commentsSaveParam.getId()==null || commentsSaveParam.getId() <= 0) {
			comments.setInid(commentsSaveParam.getSaveid());
			comments.setIndate(strNowDate);
			comments.setUpdid(commentsSaveParam.getSaveid());
			comments.setUpddate(strNowDate);
		commentsRepository.save(comments);
		}else {
			
			comments.setId(commentsSaveParam.getId());
			comments.setInid(commentsSaveParam.getSaveid());
			comments.setUpdid(commentsSaveParam.getSaveid());
			comments.setUpddate(strNowDate);
			
		
		commentsRepository.update(comments);
		}
		
	}

	public List<CommentsReturnBean> search(PageRequestParameter<CommentsSearchParam> pageRequestParameter) {
		List<CommentsReturnBean> result = commentsRepository.search(pageRequestParameter);
		
		
		return result ;
		
	}

	public int commentsave(CommentsSaveParam commentsSaveParam) {
		commentsRepository.sequpdate(commentsSaveParam.getCommentid());
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strNowDate = simpleDateFormat.format(new Date());
		commentsSaveParam.setIndate(strNowDate);
		commentsSaveParam.setUpddate(strNowDate);
		
		
		return commentsRepository.commentsave(commentsSaveParam);
		
	}

	public int delete(Long id, Long saveid) {
		 return commentsRepository.delete(id,saveid);		
	}
	
	
	

	
}
