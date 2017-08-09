package com.jt.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Controller
public class FileUploadController {
	
	private static final Logger logger=Logger.getLogger(FileUpload.class);
	/**
	 * 文件上传的步骤
	 * 1.采用文件正确的接收方式接收(修改3处 配置文件/接口类型等)
	 * 2.判断是否为图片，0表示无异常，1代表异常(jpg|gif|png)
	 * 3.判断是不是一个"正经"的图片 判断是否有width和height
	 * 4.编辑磁盘目录D:/jt-upload/images/yyyy/MM/dd/HH/mm/文件的名称
	 * 5.编辑相对路径url:image.jt.com/images/yyyy/MM/dd/HH/mm/文件的名称
	 * 6.将文件保存
	 * @param uploadFile
	 * @return
	 */
		
	@ResponseBody
	@RequestMapping("/pic/upload")
	public PicUploadResult fileUpload(MultipartFile uploadFile){
		PicUploadResult picUpload=new PicUploadResult();
		//1.获取文件的名称
		String fileName=uploadFile.getOriginalFilename();
		//2.获取后缀名称
		String endName=fileName.substring(fileName.lastIndexOf("."));
		//3.判断是否为图片格式
		if(!endName.matches("^.*(jpg|png|gif)$")){
			picUpload.setError(1);
			logger.error("~~~~~~文件后缀名不符合格式");
			return picUpload;
		}
		//4.判断是否为一个正确的图片
		try {
			BufferedImage bufferedImage=ImageIO.read(uploadFile.getInputStream());
			//获取宽度和高度，如果获取时有问题 则会报异常
			int width=bufferedImage.getWidth();
			int height=bufferedImage.getHeight();
			picUpload.setWidth(width+"");
			picUpload.setHeight(height+"");
			
			String localPath="D:/jt-upload/images/";
			String datePath=new SimpleDateFormat("yyyy/MM/dd/HH/mm").format(new Date());
			//D:/jt-upload/images/2017/7/25/15/40/文件名称
			String urlPath="http://image.jt.com/images/";
			localPath+=datePath+"/"+fileName;
			urlPath+=datePath+"/"+fileName;
			
			File file=new File(localPath);
			//判断文件是否存在
			if(!file.exists()){
				file.mkdirs();//创建多个文件夹				
			}
			//将文件写入
			uploadFile.transferTo(file);
			picUpload.setUrl(urlPath);
			logger.info("文件写入成功"+localPath);
						
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("~~~~该文件为一个非法文件");
			picUpload.setError(1);
			return picUpload;
		}
						
		return picUpload;
	}
}
