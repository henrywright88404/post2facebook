package com.post2facebook.chart_creation;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.post2facebook.claim_data.ClaimDataSummarizer;

public class pieChartCreation {
	
	public byte[] byteArrayfaultPieChartFromClaimData(ClaimDataSummarizer claimDataSummary){
		  
	    byte[] imageByteArray = null;
	  	DefaultPieDataset pieDataset = new DefaultPieDataset();
	  	ByteArrayOutputStream out = new ByteArrayOutputStream();
	    pieDataset.setValue("Insured at Fault", claimDataSummary.getInsuredAtFault());
	    pieDataset.setValue("Third Party at Fault", claimDataSummary.getThirdPartyAtFault());
	    pieDataset.setValue("Unidentified Third Party", claimDataSummary.getUnidentifiedThirdParties());
	    pieDataset.setValue("Liability Not Determined", claimDataSummary.getNotYetDetermined());

	    JFreeChart chart = ChartFactory.createPieChart3D("Fault Representation", pieDataset, true, true, true);
	    
	    try {
			ChartUtils.writeChartAsPNG( out , chart, 400, 300);
			imageByteArray = out.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageByteArray;
  }
  

}
   




