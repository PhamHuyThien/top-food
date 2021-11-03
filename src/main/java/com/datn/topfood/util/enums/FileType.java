package com.datn.topfood.util.enums;

public enum FileType {

	FILE("image"),VIDEO("video"),DEFAULT("default");
	
	public final String name;
	
	private FileType(String name) {
		this.name = name;
	}
	
	
}
