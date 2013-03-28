package ca.ualberta.team2recipefinder.test;

import junit.framework.TestCase;

import java.lang.Double;

import ca.ualberta.team2recipefinder.model.Ingredient;

public class IngredientTest extends TestCase {
	
	public void testGetType() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals("test", ingredient.getType());
	}
	
	public void testGetAmount() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals(Double.valueOf(0.0), ingredient.getAmount());
	}
	
	public void testGetUnit() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals("gram", ingredient.getUnit());
	}
	
	public void testModifyAmount() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		ingredient.modifyAmount(Double.valueOf(0.0));
		assertEquals(Double.valueOf(0.0), ingredient.getAmount());
	}
	
	public void testToString() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals(Double.valueOf(0.0).toString() + " gram test", ingredient.toString());
	}
	
	public void testDefaultConstructor() {
		Ingredient ingredient = new Ingredient();
		assertEquals(Double.valueOf(0.0).toString() + " Unknown Unknown", ingredient.toString());
	}
}