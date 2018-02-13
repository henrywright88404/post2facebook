package com.post2facebook.viewController;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {
		return ReportFile.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ReportFile file = (ReportFile) obj;
		System.out.println("Inside validate.");
		if (file.getReportFile().getSize() == 0){
			errors.rejectValue("reportFile", "valid.file");
		}else if(!file.getReportFile().getOriginalFilename().endsWith(".csv")){
			errors.rejectValue("reportFile", "valid.type");
		}
	}

}
