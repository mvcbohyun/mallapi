package com.kinsight.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kinsight.bean.CategoryReturnBean;
import com.kinsight.bean.ContentsDetailReturnBean;
import com.kinsight.bean.ContentsReturnBean;
import com.kinsight.domain.Contents;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.param.ContentsSaveParam;
import com.kinsight.param.ContentsSearchParam;
import com.kinsight.repository.ContentsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentsService {

	private final ContentsRepository contentsRepository;
	
	private final FileService fileService;
	
	public Long save(ContentsSaveParam contentsSaveParam) {
		Contents contents = new Contents();
		if(contentsSaveParam.getId() ==null ||contentsSaveParam.getId() <=0) {
			contents=  contentsSaveParam.saveEntity(contentsSaveParam);		 
			System.out.println("contents :"+ contents);
			contentsRepository.save(contents);
		}else {
			contents=  contentsSaveParam.updateEntity(contentsSaveParam);		 
			int result =contentsRepository.update(contents);
			if(result <1) {
				throw new BaseException(BaseResponseCode.ROLENOTADMIN);
			}
			fileService.delete(contentsSaveParam.getId() , "contents");
			contents.setId(contentsSaveParam.getId());
		}
		if(contentsSaveParam.getUploadfile() !=null) {
			try {
				
				fileService.vidioupload(contentsSaveParam.getUploadfile(), contents.getId(), "contents");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		return contents.getId();
	}

	public List<ContentsReturnBean>search(PageRequestParameter<ContentsSearchParam> pageRequestParameter) {
		// TODO Auto-generated method stub
		
		return contentsRepository.search(pageRequestParameter);
	}

	public ContentsDetailReturnBean detail(Long id) {
		ContentsDetailReturnBean contentsDetailReturnBean= contentsRepository.detail(id);
		
		if(contentsDetailReturnBean == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		
		return contentsDetailReturnBean;
		
	}

	public void delete(Long id, Long saveid) {
		int result = contentsRepository.delete(id,saveid);
		
		if(result <1) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		
		
	}



}
