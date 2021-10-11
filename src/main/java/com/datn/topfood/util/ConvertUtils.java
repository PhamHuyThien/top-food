package com.datn.topfood.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.datn.topfood.data.model.File;
import com.datn.topfood.dto.request.FileRequest;
import com.datn.topfood.util.enums.FileType;

public class ConvertUtils {

	public static Set<File> convertArrFileReqToSetFile(List<FileRequest> arrFile){
		Set<File> files = new HashSet<>();
		for(FileRequest f:arrFile) {
			File file  = new File();
			file.setPath(f.getPath());
			file.setType(FileType.valueOf(f.getType().toUpperCase()));
			files.add(file);
		}
		return files;
	}
	
	public static List<FileRequest> convertSetToArrFile(Set<File> files){
		return files.stream().map((f)->{
			return new FileRequest(f.getPath(), f.getType().name);
		}).collect(Collectors.toList());
	}
}
