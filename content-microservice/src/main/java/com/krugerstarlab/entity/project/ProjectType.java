package com.krugerstarlab.entity.project;

public enum ProjectType {
	
	GROUP("GROUP"),
	INDIVIDUAL("INDIVIDUAL");
	
	private String disc;
	
	 ProjectType (String disc) {
		this.disc=disc;
	}
	 
	 public String getDisc() {
		 return disc;
	 }

}
