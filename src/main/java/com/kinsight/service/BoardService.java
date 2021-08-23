package com.kinsight.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.kinsight.bean.BoardDetailReturnBean;
import com.kinsight.bean.BoardReturnBean;
import com.kinsight.bean.FileReturnBean;
import com.kinsight.domain.Board;
import com.kinsight.domain.PageRequestParameter;
import com.kinsight.exception.BaseException;
import com.kinsight.exception.BaseResponseCode;
import com.kinsight.param.BoardSaveParam;
import com.kinsight.param.BoardSearchParam;
import com.kinsight.properties.FileProperties;
import com.kinsight.repository.BoardRepository;
import com.kinsight.repository.CommentsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	
	 	@Value("${cloud.aws.credentials.accessKey}")
	    private String accessKey;

	    @Value("${cloud.aws.credentials.secretKey}")
	    private String secretKey;

	    @Value("${cloud.aws.s3.bucket}")
	    private String bucket;

	    @Value("${cloud.aws.region.static}")
	    private String region;

	    private AmazonS3 s3Client;
	    
	    @Autowired
	    private ResourceLoader resourceLoader;


	

	private final BoardRepository boardRepository ;
	private final FileProperties fileProperties;
	private final FileService fileService;
	private final CommentsRepository commentsRepository;
	@PostConstruct
	public void setS3Client() {
	        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

	        s3Client = AmazonS3ClientBuilder.standard()
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .withRegion(this.region)
	                .build();
	 }


	// 게시물 조회
	public List<BoardReturnBean> search(PageRequestParameter<BoardSearchParam> pageRequestParameter) {	
		
		return boardRepository.search(pageRequestParameter);
	}
	
	// 파일 저장 
	private String[] saveUploadFile(MultipartFile multipartFile) {

        String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1,multipartFile.getOriginalFilename().length());
		String filename = UUID.randomUUID().toString() + "." + prefix;
		String uploadFilePath = fileProperties.getUploadFilePath();
		String pathname = uploadFilePath +filename;
	
		String[] filenamepath = {filename,uploadFilePath,pathname};
		
		File file =new File(pathname);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return filenamepath;
  }
	
	

	
	// 게시물 저장
	public Long save(BoardSaveParam boardSaveParam) {
		System.out.println("111111111111111");
		Board board = new Board();
		if(boardSaveParam.getBoardid() ==null || boardSaveParam.getSaveid() <= 0) {
			board=boardSaveParam.saveEntity(boardSaveParam);
			boardRepository.save(board);
		
		}else {
			System.out.println("11111111111111111111111111111");
		    board = boardSaveParam.updateEntity(boardSaveParam);
			fileService.delete( boardSaveParam.getBoardid(), "board");
			boardRepository.update(board);
		}
		if(boardSaveParam.getUploadfile() != null) {
		try {
			if(boardSaveParam.getUploadfile().get(0).getSize()>0) {
				
			fileService.upload(boardSaveParam.getUploadfile() , board.getId(),"board");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return board.getId();
		
		

	}
	 public String[] upload(List<MultipartFile> multipartFile) throws IOException {
		 System.out.println(multipartFile.size()); 
		 	for (MultipartFile file : multipartFile) {
		    String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
			String fileName = UUID.randomUUID().toString() + "." + prefix;
			SimpleDateFormat format = new SimpleDateFormat ( "yyyy");
			Date time = new Date();
			String year  = format.format(time);
			String path = bucket+fileProperties.getImageFilePath()+year;
			String fullpath = fileProperties.getFullfilePath()+year+"/"+fileName;
			String[] filenamepath = {fileName,path,fullpath};
			ObjectMetadata objMeta = new ObjectMetadata();

			byte[] bytes = IOUtils.toByteArray(file.getInputStream());
			objMeta.setContentLength(bytes.length);


	        s3Client.putObject(new PutObjectRequest(path, fileName,file.getInputStream(), objMeta)
	                .withCannedAcl(CannedAccessControlList.PublicRead));
	        
			
	       
	        
	       System.out.println(fullpath);
	   
		 	}
		 	return null;
	     
	 }
	 
	 public int delete(Long id, Long saveid) {
		
		 fileService.delete(id,"board");
		 commentsRepository.boardiddelete(id);
		 return boardRepository.delete(id,saveid);		 
	 }


	public BoardDetailReturnBean detail(Long id ,Long saveid) {
		BoardDetailReturnBean detailBaen  = boardRepository.detail(id,saveid);
		
		if(detailBaen== null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL);
		}
		List<FileReturnBean> fileList  =fileService.detail(id) ;
		
		
		if(fileList != null) {
			detailBaen.setFilelist(fileList);
		}
		
		return detailBaen;
	}
	
	
	

}
