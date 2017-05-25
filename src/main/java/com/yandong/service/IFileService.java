package com.yandong.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yandong on 2017/5/22.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
