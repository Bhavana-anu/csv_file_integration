package com.ty.csvfile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ty.csvfile.dto.ResponseMessage;
import com.ty.csvfile.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		if(fileService.hasCsvFormat(file)) {
			fileService.processAndSaveData(file);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("uploaded file successfully: "+file.getOriginalFilename()));
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("please upload CSV file"));
	}

}
