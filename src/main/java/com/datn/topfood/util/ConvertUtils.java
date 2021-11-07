package com.datn.topfood.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.datn.topfood.data.model.File;
import com.datn.topfood.dto.request.FileRequest;
import com.datn.topfood.util.enums.FileType;

public class ConvertUtils {

	public static Set<File> convertArrFileReqToSetFile(List<String> arrFile){
		Set<File> files = new HashSet<>();
		for(String f:arrFile) {
			File file  = new File();
			file.setPath(f);
			file.setType(FileType.DEFAULT);
			files.add(file);
		}
		return files;
	}
	
	public static List<String> convertSetToArrFile(Set<File> files){
		return files.stream().map((f)->{
			return f.getPath();
		}).collect(Collectors.toList());
	}
}
