package com.bridgelabz.employeepayroll;

public class Company {
	private String companyName;
	private String companyId;
	
	public Company(String name, String id) {
		this.setCompanyId(id);
		this.setCompanyName(name);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
