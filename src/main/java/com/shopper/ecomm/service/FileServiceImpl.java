package com.shopper.ecomm.service;

import com.shopper.ecomm.exceptions.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new ApiException("Image file cannot be blank. Only png, jpg and jpeg are allowed");
        }

        if(!isValidImageFile(Objects.requireNonNull(file.getOriginalFilename()))){
            throw new ApiException("Invalid file type. Only png, jpg and jpeg are allowed");
        }

        // check if file has extension
        if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpg") && !file.getContentType().equals("image/jpeg")){
            throw new ApiException("Invalid file type. Only png, jpg and jpeg are allowed");
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

    public boolean isValidImageFile(String filename) {
        return filename.toLowerCase().matches(".*\\.(png|jpg|jpeg)$");
    }
}
