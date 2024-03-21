package com.goev.central.utilities;

import com.goev.central.constant.ApplicationConstants;
import com.goev.lib.exceptions.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class StorageUtils {
    private final Path rootLocation = Paths.get("/tmp/central/");

    @Autowired
    private S3Utils s3;

    public String store(MultipartFile file) {
        try {
            if (file == null)
                throw new ResponseException("No file present.");
            if (file.isEmpty()) {
                throw new ResponseException("Failed to store empty file.");
            }
            if (file.getOriginalFilename() == null) {
                throw new ResponseException("No filename present");
            }
            Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new ResponseException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return s3.uploadFileOnS3(destinationFile.toFile().getAbsolutePath(), ApplicationConstants.S3_BUCKET_NAME);
        } catch (IOException e) {
            log.error("Error in storing file : {}", file.getName(), e);
            throw new ResponseException("Failed to store file.");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new ResponseException("Could not initialize storage");
        }
    }
}
