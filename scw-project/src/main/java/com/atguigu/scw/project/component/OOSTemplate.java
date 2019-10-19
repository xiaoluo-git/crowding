package com.atguigu.scw.project.component;

import java.io.InputStream;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OOSTemplate {
	
	String endpoint;
	String accessKeyId;
	String accessKeySecret;
	String bucket;
	
	public String upload(String fileName ,InputStream inputStream)  {
		
		OSS ossClient = null;
		try {

			ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

			ossClient.putObject(bucket, "pic/" + fileName, inputStream);
			
			String filePath = "https://" + bucket + "." + endpoint + "/pic/" + fileName;
			
			log.debug("{}上传成功",fileName);
			//https://xlscw.oss-cn-beijing.aliyuncs.com/pic/%E5%86%92%E6%B3%A1%E5%8A%A8%E7%94%BB.gif
			return filePath;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("{}上传失败",fileName);
			return null;
		}finally {
			
			if(ossClient != null) {
				ossClient.shutdown();
			}
		}

	}
	
}
