package com.subang.bean;

public class StatItem<T> {
	
	private String name;
	private T quantity;
	
	public StatItem() {
	}

	public StatItem(String name, T quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getQuantity() {
		return quantity;
	}

	public void setQuantity(T quantity) {
		this.quantity = quantity;
	}	
}
