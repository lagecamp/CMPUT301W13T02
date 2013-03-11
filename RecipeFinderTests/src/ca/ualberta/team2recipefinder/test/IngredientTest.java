package ca.ualberta.team2recipefinder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.lang.Double;

import ca.ualberta.team2recipefinder.Ingredient;

public class IngredientTest{
	
	@Test
	public void testGetType() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals("Type", "test", ingredient.getType());
	}
	
	@Test
	public void testGetAmount() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals("Amount", Double.valueOf(0.0), ingredient.getAmount());
	}
	
	@Test
	public void testGetUnit() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals("Amount", "gram", ingredient.getUnit());
	}
	
	@Test
	public void testModifyAmount() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		ingredient.modifyAmount(Double.valueOf(0.0));
		assertEquals("Amount", Double.valueOf(0.0), ingredient.getAmount());
	}
	
	@Test
	public void testToString() {
		Ingredient ingredient = new Ingredient("test", 0.0, "gram");
		assertEquals("Amount", Double.valueOf(0.0).toString() + " gram test", ingredient.toString());
	}
}