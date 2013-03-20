package ca.ualberta.team2recipefinder.model;

import java.io.Serializable;

/**
 * Ingredient is the basic unit for storing a type of ingredient to file.
 * One ingredient contains three attributes, type, amount and unit.
 * Allow the user to get those attributes in <code>String</code>.
 * Only the amount attribute is allowed to be modified once it's created.
 * @author cmput-301 team 2
 * @version 1.00 07/03/13
 * @see ca.ualberta.team2recipefinder.model.MyKitchen
 * @see ca.ualberta.team2recipefinder.model.Recipe
 */
public class Ingredient implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;// the type of the ingredient
	private Double amount;// the amount of the ingredient
	private String unit;// the unit to measure the ingredient

	public Ingredient(String type, Double amount, String unit) {
		this.type = type;
		this.amount = amount;
		this.unit = unit;
	}
	
	public Ingredient() {
		type = "Unknown";
		amount = 0.0;
		unit = "Unknown";
	}
	/**
	 * Returns the type of the ingredient in <code>String</code>
	 * @return	the type of the ingredient
	 */
	public String getType() {
		return type;
	}
	/**
	 * Returns the amount of the ingredient in <code>Double</code>
	 * @return	the amount of the ingredient
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * Returns the unit of the ingredient in <code>String</code>
	 * @return	the unit of the ingredient
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * To modify the amount of the ingredient
	 * @param	the new amount of the ingredient
	 */
	public void modifyAmount(Double amount) {
		this.amount = amount;
	}
	
	/**
	 * Converts and returns all the information of the ingredient in formatted <code>String</code>
	 */
	@Override
	public String toString() {
		return amount + " " + unit + " " + type;
	}
}
