package com.gr.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 18334 on 2019/6/13.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
