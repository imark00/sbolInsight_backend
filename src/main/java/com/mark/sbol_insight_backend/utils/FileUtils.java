package com.mark.sbol_insight_backend.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FileUtils {
    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, file);
        return file;
    }
}
