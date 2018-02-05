package com.post2facebook.claim_data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class ExcelReader {
	
	
	public List<ClaimData> readReport(){
		final String FILE_NAME ="C:\\Users\\Marc\\Interview WorkSpace\\Gmail (Oauth)\\gmail-demo\\Java101\\src\\main\\java\\claim_data\\reportExport.csv";
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy =",";
		
		List<ClaimData> claimData = new ArrayList<>();
		
		try {
			
			br = new BufferedReader(new FileReader(FILE_NAME));
		
			try {
				//debug line 
				int lineN = 1;
				while((line = br.readLine())!=null){
					

					System.out.println(lineN++);
					
					String[] claimInfo = line.split(cvsSplitBy);
					
					LocalDateTime claimRecivedDate = extractTime(claimInfo[0]);
					
					LocalDateTime claimClosedDate = null;
					if (claimInfo[1] != null && !claimInfo[1].isEmpty()){
						claimClosedDate = extractTime(claimInfo[1]);
						
					}
					
					claimInfo[2] = claimInfo[2].replace("\"", "");
					
					double grossReserve = 0;
					if (claimInfo[2].isEmpty()){
						
					}else if (claimInfo[2].contains("-")){
						claimInfo[2] = claimInfo[2].replace("-", "").trim();
						
						if(!claimInfo[2].isEmpty()){
							grossReserve =- Double.parseDouble(claimInfo[2]);
						}
					}else{
						grossReserve = Double.parseDouble(claimInfo[2]);
					}
					
					Fault fault = Fault.NOTYETDETERMINED;
					
					if(claimInfo.length > 3){
						fault = extractFault(claimInfo[3]);
					}
					ClaimData cd = new ClaimData(claimRecivedDate,claimClosedDate,fault,grossReserve);
					
					claimData.add(cd);
				}
			} finally{
				br.close();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("No File found: ");
			e.printStackTrace();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return claimData;
		
	}
	
	public List<ClaimData> readReport(MultipartFile report){
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy =",";
		
		List<ClaimData> claimData = new ArrayList<>();
		
		try {
			InputStream is = report.getInputStream();
			
			
			File convFile = new File(report.getOriginalFilename());
			report.transferTo(convFile);
			br = new BufferedReader(new InputStreamReader(is));
		
			try {
				//debug line 
				int lineN = 1;
				while((line = br.readLine())!=null){
					

					System.out.println(lineN++);
					
					String[] claimInfo = line.split(cvsSplitBy);
					
					LocalDateTime claimRecivedDate = extractTime(claimInfo[0]);
					
					LocalDateTime claimClosedDate = null;
					if (claimInfo[1] != null && !claimInfo[1].isEmpty()){
						claimClosedDate = extractTime(claimInfo[1]);
						
					}
					
					claimInfo[2] = claimInfo[2].replace("\"", "");
					
					double grossReserve = 0;
					if (claimInfo[2].isEmpty()){
						
					}else if (claimInfo[2].contains("-")){
						claimInfo[2] = claimInfo[2].replace("-", "").trim();
						
						if(!claimInfo[2].isEmpty()){
							grossReserve =- Double.parseDouble(claimInfo[2]);
						}
					}else{
						grossReserve = Double.parseDouble(claimInfo[2]);
					}
					
					Fault fault = Fault.NOTYETDETERMINED;
					
					if(claimInfo.length > 3){
						fault = extractFault(claimInfo[3]);
					}
					ClaimData cd = new ClaimData(claimRecivedDate,claimClosedDate,fault,grossReserve);
					
					claimData.add(cd);
				}
			} finally{
				br.close();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("No File found: ");
			e.printStackTrace();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return claimData;
		
	}
	
	
	private Fault extractFault(String string) {
		
		switch (string.trim()) {
		case "Insured":
			return Fault.INSURED;
		case "Third party – unknown":
			return Fault.THIRDPARTYUNKNOWN;
		case "Third party - identified":
			return Fault.THIRDPARTYIDENTIFIED;
		default:
			return Fault.NOTYETDETERMINED;
		}
	}

	public LocalDateTime extractTime(String DateTime){
		
		if (DateTime != null || DateTime == ""){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(DateTime, formatter);
			return dateTime;
		}
		return null;
	}

}
