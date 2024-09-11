package com.shopper.ecomm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("file not specified");
        }

        // check if file has extension
        if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpg") && !file.getContentType().equals("image/jpeg")){
            throw new RuntimeException("invalid file type");
        }

        String originalFilename = file.getOriginalFilename();
        String randomName =  UUID.randomUUID().toString();

        String newFilename = randomName.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));

        String filePath = path + File.separator + newFilename;

        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newFilename;
    }
}
