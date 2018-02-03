package com.post2facebook.viewController;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FileValidator implements Validator {

	@Override
	public boolean supports(Class<?> paramClass) {
		return ReportFile.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ReportFile file = (ReportFile) obj;
		if (file.getReportFile().getSize() == 0){
			errors.rejectValue("reportFile", "valid.file");
		}
	}

}
