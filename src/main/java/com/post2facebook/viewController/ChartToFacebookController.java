package com.post2facebook.viewController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Executor;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.post2facebook.chartCreation.pieChartCreation;
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
	public String fileUploaded(Model model, @Validated ReportFile reportFile,
			BindingResult result){


		String returnVal = "publish-report";
		System.out.println("inside file uploaded");
		System.out.println(("file name: "+ reportFile.getReportFile().getOriginalFilename()));
		if (result.hasErrors()){
			returnVal = "postreport";
		} else {

			try{
			MultipartFile multipartFile = reportFile.getReportFile();
			ExcelReader xlReader = new ExcelReader();
			ClaimDataSummarizer claimSummary = new ClaimDataSummarizer();
			pieChartCreation pieChart = new pieChartCreation();

			Runnable reportSummary = () -> {
				claimSummary.summerizeReport(xlReader.readReport(multipartFile));
			};
			
			reportSummary.run();
			Thread reportThread = new Thread(reportSummary);
			
			reportThread.start();
			reportThread.join();
			
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
	@ResponseBody
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
