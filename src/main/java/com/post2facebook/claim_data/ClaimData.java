package com.post2facebook.claim_data;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClaimData {
	
	private LocalDateTime claimRecivedDate;
	private LocalDateTime claimClosedDate;
	private Fault fault;
	private Double grossReserve;


	public ClaimData(LocalDateTime claimRecivedDate, LocalDateTime claimClosedDate, Fault fault,
			Double grossReserve) {
		this.claimRecivedDate = claimRecivedDate;
		this.claimClosedDate = claimClosedDate;
		this.fault = fault;
		this.grossReserve = grossReserve;
	}
	
	public Double getGrossReserve() {
		return grossReserve;
	}
	public void setGrossReserve(Double grossReserve) {
		this.grossReserve = grossReserve;
	}
	public LocalDateTime getClaimRecivedDate() {
		return claimRecivedDate;
	}
	public void setClaimRecivedDate(LocalDateTime claimRecivedDate) {
		this.claimRecivedDate = claimRecivedDate;
	}
	public LocalDateTime getClaimClosedDate() {
		return claimClosedDate;
	}
	public void setClaimClosedDate(LocalDateTime claimClosedDate) {
		this.claimClosedDate = claimClosedDate;
	}
	public Fault getFault() {
		return fault;
	}
	public void setFault(Fault fault) {
		this.fault = fault;
	}
	public int daysClaimWasOpen(){
		return (int) ChronoUnit.DAYS.between(claimRecivedDate, claimClosedDate);
		
	}

	@Override
	public String toString() {
		return "ClaimData [claimRecivedDate=" + claimRecivedDate + ", claimClosedDate=" + claimClosedDate + ", fault="
				+ fault + ", grossReserve=" + grossReserve + "]";
	}
}
