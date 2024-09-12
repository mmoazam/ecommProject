package com.shopper.ecomm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FileService {
    String uploadImage(String path, MultipartFile file) throws IOException;

    void deleteImage(String path, String image) throws IOException;
}
