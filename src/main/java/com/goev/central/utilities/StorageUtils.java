package com.goev.central.utilities;

import com.goev.lib.exceptions.ResponseException;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
public class StorageUtils {
    private final Path rootLocation = Paths.get("/tmp/central");

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
            return destinationFile.toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Error in storing file : {}", file.getName(), e);
            throw new ResponseException("Failed to store file.");
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new ResponseException("Could not initialize storage");
        }
    }
}
