package com.goev.central.controller;

import com.goev.central.utilities.StorageUtils;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import com.goev.lib.enums.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/file-management")
public class FileController {

    @Autowired
    private StorageUtils storageUtils;

    @PostMapping(value = "/files")
    public ResponseDto<String> uploadFile(@RequestParam("file") MultipartFile file) {
            return new ResponseDto<>(
                    StatusDto.builder().message("SUCCESS").build(),200,
                    storageUtils.store(file)
            );
    }
}
