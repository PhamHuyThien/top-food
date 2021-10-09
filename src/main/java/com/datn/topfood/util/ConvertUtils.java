package com.datn.topfood.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.datn.topfood.data.model.File;
import com.datn.topfood.dto.request.FileRequest;
import com.datn.topfood.util.enums.FileType;

public class ConvertUtils {

	public static Set<File> convertArrFileReqToSetFile(ArrayList<FileRequest> arrFile){
		Set<File> files = new HashSet<>();
		for(FileRequest f:arrFile) {
			File file  = new File();
			file.setPath(f.getPath());
			file.setType(FileType.valueOf(f.getType().toUpperCase()));
			files.add(file);
		}
		return files;
	}
}
