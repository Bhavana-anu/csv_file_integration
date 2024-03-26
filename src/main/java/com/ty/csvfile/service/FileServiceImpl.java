package com.ty.csvfile.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ty.csvfile.entity.Users;
import com.ty.csvfile.repository.UserRepository;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private UserRepository repository;

	@Override
	public boolean hasCsvFormat(MultipartFile file) {
		String type = "text/csv";
		if (!type.equals(file.getContentType()))
			return false;
		return true;
	}

	@Override
	public void processAndSaveData(MultipartFile file) {
		try {
			List<Users> users = csvToUsers(file.getInputStream());
			repository.saveAll(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<Users> csvToUsers(InputStream inputStream) {
		try(BufferedReader fileReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		CSVParser csvparser=new CSVParser(fileReader,CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());){
			List<Users> users=new ArrayList<Users>();
			List<CSVRecord> records=csvparser.getRecords();
			for(CSVRecord csvRecord:records) {
				Users user=new Users(Long.parseLong(csvRecord.get("id")),csvRecord.get("name"),csvRecord.get("email"),csvRecord.get("password"));
				users.add(user);
			}
			return users;
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return null;
	}

}
