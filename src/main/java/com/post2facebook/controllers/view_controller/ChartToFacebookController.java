package com.post2facebook.controllers.view_controller;

import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.post2facebook.chart_creation.pieChartCreation;
import com.post2facebook.claim_data.ClaimDataSummarizer;
import com.post2facebook.claim_data.ExcelReader;
import com.post2facebook.facebook.FacebookPost;

@Controller
@RequestMapping("/charttofacebook")
public class ChartToFacebookController {


	@Autowired
	FileValidator validator;

	@InitBinder("postreport")
	private void initBinder (WebDataBinder binder){
		binder.setValidator(validator);
	}

	@GetMapping("/")
	public String getForm(Model model){
		ReportFile fileModel=new ReportFile();
		model.addAttribute("reportFile", fileModel);
		return "postreport";
	}

	@PostMapping("/")
	public String fileUploaded(Model model, @Valid ReportFile reportFile,
			BindingResult result){
		
       validator.validate(reportFile, result);

		String returnVal = "publish-report";
		System.out.println("inside file uploaded");
		System.out.println(("file name: "+ reportFile.getReportFile().getOriginalFilename()));
		if (result.hasErrors()){
			System.out.println("has errors.");
			returnVal = "postreport";
		} else {

			try{
			MultipartFile multipartFile = reportFile.getReportFile();
			ExcelReader xlReader = new ExcelReader();
			ClaimDataSummarizer claimSummary = new ClaimDataSummarizer();
			pieChartCreation pieChart = new pieChartCreation();


			claimSummary.summerizeReport(xlReader.readReport(multipartFile));

			byte[] file =  pieChart.byteArrayfaultPieChartFromClaimData(claimSummary);
			byte[] encoded = Base64.encodeBase64(file);
			String encodedString = new String(encoded);

			model.addAttribute("chart", encodedString);
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		return returnVal;
	}

	@PostMapping("/posted")
	public String posted(Model model,
			@ModelAttribute("faultChart") String faultChart,
			@ModelAttribute("message") String message){

		String retVal="postedReportSuccess";
		byte[] imageAsbytes = Base64.decodeBase64(faultChart);
		FacebookPost fbPost = new FacebookPost();
		fbPost.createImagePostInGroup(message, fbPost.getJava101id(), imageAsbytes);


		return retVal;

	}

}
