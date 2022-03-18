package com.portfolio.blog.util;

public enum Userflag {
	SALIES_CLIENT(1), 
	PURCHASE_CLIENT(3), 
	TRANSPORT(5);
	
	private final int value;
	
	Userflag(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
