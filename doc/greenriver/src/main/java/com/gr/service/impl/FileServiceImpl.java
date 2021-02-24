package com.gr.service.impl;

import com.google.common.collect.Lists;
import com.gr.service.IFileService;
import com.gr.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by 18334 on 2019/6/13.
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path){//MultipartFile接受前端传过来的文件
        String fileName = file.getOriginalFilename();

        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名：{}，上传的路径：{}，新文件名：{}",fileName, path, uploadFileName);

        File fileDir = new File(path);//定义路径文件
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);//定义目标路径文件

        try{
            file.transferTo(targetFile);

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            targetFile.delete();
        }catch (IOException e){
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
