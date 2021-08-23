package com.kinsight.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.kinsight.bean.FileReturnBean;
import com.kinsight.domain.File;
import com.kinsight.param.FileParam;
import com.kinsight.properties.FileProperties;
import com.kinsight.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

 	@Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private AmazonS3 s3Client;
    
    private final FileRepository fileRepository;

	private final FileProperties fileProperties;
	
	@PostConstruct
	public void setS3Client() {
	        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

	        s3Client = AmazonS3ClientBuilder.standard()
	                .withCredentials(new AWSStaticCredentialsProvider(credentials))
	                .withRegion(this.region)
	                .build();
	 }
	
	
	public String[] upload(List<MultipartFile> multipartFile, Long boardid, String tabletype) throws IOException {
		 System.out.println(multipartFile.size()); 
		 int seq = 0 ;
		 	for (MultipartFile file : multipartFile) {
		    String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1,file.getOriginalFilename().length());
			String fileName = UUID.randomUUID().toString() + "." + prefix;
			SimpleDateFormat format = new SimpleDateFormat ( "yyyy");
			Date time = new Date();
			String year  = format.format(time);
			String path = bucket+fileProperties.getImageFilePath()+year;
			String key = fileProperties.getImageFilePath()+year+"/"+fileName;
			String fullpath = fileProperties.getFullfilePath()+year+"/"+fileName;
			String[] filenamepath = {fileName,path,fullpath};
			ObjectMetadata objMeta = new ObjectMetadata();

			byte[] bytes = IOUtils.toByteArray(file.getInputStream());
			objMeta.setContentLength(bytes.length);

	        s3Client.putObject(new PutObjectRequest(path, fileName,file.getInputStream(), objMeta)
	                .withCannedAcl(CannedAccessControlList.PublicRead));
	        
	        File filedomain = File.builder().typeid(boardid).uploadfile(fileName).path(path).fullpath(fullpath).filekey(key).seq(seq).filetype("Image").tabletype(tabletype).build();
	        
	        fileRepository.filesave(filedomain);
	        seq++;
	       
	       System.out.println(fullpath);
	   
		 	}
		 	return null;
	     
	 }
	
	public String[] vidioupload(MultipartFile multipartFile, Long id, String tabletype) throws IOException {
		
		 	int seq = 0 ;
		    String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1,multipartFile.getOriginalFilename().length());
			String fileName = UUID.randomUUID().toString() + "." + prefix;
			SimpleDateFormat format = new SimpleDateFormat ( "yyyy");
			Date time = new Date();
			String year  = format.format(time);
			String path = bucket+fileProperties.getImageFilePath()+year;
			String key = fileProperties.getImageFilePath()+year+"/"+fileName;
			String fullpath = fileProperties.getFullfilePath()+year+"/"+fileName;
			String[] filenamepath = {fileName,path,fullpath};
			ObjectMetadata objMeta = new ObjectMetadata();

			byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
			objMeta.setContentLength(bytes.length);

	        s3Client.putObject(new PutObjectRequest(path, fileName,multipartFile.getInputStream(), objMeta)
	                .withCannedAcl(CannedAccessControlList.PublicRead));
	        
	        File filedomain = File.builder().typeid(id).uploadfile(fileName).path(path).fullpath(fullpath).filekey(key).seq(seq).filetype("vidio").tabletype(tabletype).build();
	        
	        fileRepository.filesave(filedomain);
	      
		 	return null;
	     
	 }


	public List<FileReturnBean> detail(Long id) {
		List<FileReturnBean> fileList  = fileRepository.detail(id);
		if(fileList == null) {
			return null;
		}
		return fileList;
	}
	

	public void delete(Long id,String tabletype) {
		
		FileParam fileParam = new FileParam();
		
		fileParam.setTypeid(id);
		fileParam.setTabletype(tabletype);
		
		System.out.println("delete service");
		List<String> key = fileRepository.findkey(fileParam);
		
		System.out.println(key);
		
		for(String keylist : key) {
			s3Client.deleteObject(new DeleteObjectRequest("kinsightfile", keylist));
		}
		//s3Client.deleteObject(new DeleteObjectRequest("kinsightfile", "Image/2021/f9398c6d-9329-4257-9b3e-51c5656447f6.png"));
		System.out.println("11111111111111111111111111111111111");
		System.out.println(id);
		fileRepository.delete(fileParam);
		System.out.println("22222222222222222222222222222222222");
		
	}

}
