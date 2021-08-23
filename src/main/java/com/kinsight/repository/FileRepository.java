package com.kinsight.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kinsight.bean.FileReturnBean;
import com.kinsight.domain.File;
import com.kinsight.param.FileParam;

@Repository
public interface FileRepository {

	void filesave(File filedomain);

	List<FileReturnBean> detail(Long id);

	void delete(FileParam fileParam);

	List<String> findkey(FileParam fileParam);

	
}
