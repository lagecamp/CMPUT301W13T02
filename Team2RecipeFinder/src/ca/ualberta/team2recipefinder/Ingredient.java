package ca.ualberta.team2recipefinder;

import java.io.Serializable;

public class Ingredient implements Serializable{
	private static final long serialVersionUID = 1L;
	private String type;
	private Integer amount;
	private String unity;
	
	public Ingredient(String type, Integer amount, String unity){
		this.type=type;
		this.amount=amount;
		this.unity=unity;
	}
	
	public Ingredient(){
		type="Unknow";
		amount=0;
		unity="Unkonw";
	}
	
	public String getType(){
		return type;
	}
	public Integer getAmount(){
		return amount;
	}
	public String getUnity(){
		return unity;
	}
	public void modifyAmount(Integer amount){
		this.amount= amount;
	}
}