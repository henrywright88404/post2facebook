package com.post2facebook.chartCreation;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.post2facebook.claim_data.ClaimDataSummarizer;

public class pieChartCreation {

	public String faultPieChartFromClaimData(ClaimDataSummarizer claimDataSummary){
	  
	    String chartDir = null;
	  
	  	DefaultPieDataset pieDataset = new DefaultPieDataset();
	  
	    pieDataset.setValue("Insured at Fault", claimDataSummary.getInsuredAtFault());
	    pieDataset.setValue("Third Party at Fault", claimDataSummary.getThirdPartyAtFault());
	    pieDataset.setValue("Unidentified Third Party", claimDataSummary.getUnidentifiedThirdParties());
	    pieDataset.setValue("Liability Not Determined", claimDataSummary.getNotYetDetermined());

	    JFreeChart chart = ChartFactory.createPieChart3D("Fault Representation", pieDataset, true, true, true);
	    
	    try {
			ChartUtils.saveChartAsPNG( new File("Fault Representation at " + Date.valueOf(LocalDate.now()) +".png"), chart, 400, 300);
			
			Path dir = Paths.get("");  // specify your directory

			Optional<Path> lastFilePath = Files.list(dir)    // here we get the stream with full directory listing
			    .filter(f -> !Files.isDirectory(f))  // exclude subdirectories from listing
			    .max(Comparator.comparingLong(f -> f.toFile().lastModified()));  // finally get the last file using simple comparator by lastModified field

			if ( lastFilePath.isPresent() ) // your folder may be empty
			{
			    chartDir = lastFilePath.get().toAbsolutePath().toString().replaceAll("/", "\\");
			}     
		} catch (IOException e) {
			System.out.println(chartDir);
			e.printStackTrace();
		}
		return chartDir;
  }
  

}
   




