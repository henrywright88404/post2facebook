package com.post2facebook.claim_data;

import java.util.List;

public class ClaimDataSummarizer {
	
	// take list of claim data and summarise for a brief status update. 
	
	// claims open
	// average length of time claim remains open. 
	// average claim cost
	// number of times insd @ fault vs TP. 
	private int insuredAtFault = 0;
	private int ThirdPartyAtFault = 0;
	private int unidentifiedThirdParties = 0;
	private int notYetDetermined = 0;
	private int claimsOpen = 0;
	private int claimsClosed = 0;
	private int averageDaysClaimIsOpen = 0;
	private double averageClaimCost = 0;
	
	public int getInsuredAtFault() {
		return insuredAtFault;
	}

	public int getThirdPartyAtFault() {
		return ThirdPartyAtFault;
	}

	public int getUnidentifiedThirdParties() {
		return unidentifiedThirdParties;
	}

	public int getNotYetDetermined() {
		return notYetDetermined;
	}

	public int getClaimsOpen() {
		return claimsOpen;
	}

	public int getClaimsClosed() {
		return claimsClosed;
	}

	public int getAverageDaysClaimIsOpen() {
		return averageDaysClaimIsOpen;
	}

	public double getAverageClaimCost() {
		return averageClaimCost;
	}

	public void summerizeReport (List<ClaimData> report){

		double totalGrossReserve = 0;
		int totalDaysClaimsOpen = 0;
		
		for (ClaimData claim : report ){
			if (claim.getClaimClosedDate() != null){
				claimsClosed += 1;
				totalDaysClaimsOpen += claim.daysClaimWasOpen();
			}else{
				claimsOpen++;
			}
			
			switch (claim.getFault()) {
			case INSURED:
				insuredAtFault += 1;
				break;
			case NOTYETDETERMINED:
				notYetDetermined +=1;
				break;
			case THIRDPARTYUNKNOWN:
				unidentifiedThirdParties +=1;
				break;
			case THIRDPARTYIDENTIFIED:
				ThirdPartyAtFault +=1;
				break;
			}
			
			totalGrossReserve += claim.getGrossReserve();
			
			
			averageClaimCost = totalGrossReserve / report.size();
			averageDaysClaimIsOpen = totalDaysClaimsOpen / report.size();
			
			
		}
				
	}

	@Override
	public String toString() {
		return "ClaimDataSummarizer [insuredAtFault=" + insuredAtFault + ", ThirdPartyAtFault=" + ThirdPartyAtFault
				+ ", unidentifiedThirdParties=" + unidentifiedThirdParties + ", notYetDetermined=" + notYetDetermined
				+ ", claimsOpen=" + claimsOpen + ", claimsClosed=" + claimsClosed + ", averageDaysClaimIsOpen="
				+ averageDaysClaimIsOpen + ", averageClaimCost=" + averageClaimCost + "]";
	}
	
	public String returnReportString() {
		return "insuredAtFault=" + insuredAtFault + ",\n ThirdPartyAtFault=" + ThirdPartyAtFault
				+ ",\n unidentifiedThirdParties=" + unidentifiedThirdParties + ",\n notYetDetermined=" + notYetDetermined
				+ ",\n claimsOpen=" + claimsOpen + ",\n claimsClosed=" + claimsClosed + ",\n averageDaysClaimIsOpen="
				+ averageDaysClaimIsOpen + ",\n averageClaimCost=" + averageClaimCost;
	}

}
