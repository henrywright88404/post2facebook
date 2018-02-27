package com.post2facebook.controllers.view_controller;

import org.springframework.web.multipart.MultipartFile;

public class ReportFile {
	MultipartFile file;

	public MultipartFile getReportFile() {
		return file;
	}

	public void setReportFile(MultipartFile file) {
		this.file = file;
	}

}
