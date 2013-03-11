package ca.ualberta.team2recipefinder.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.ualberta.team2recipefinder.*;

/**
 * Tests RecipeModel class
 */
public class RecipeModelTest
{
	
	RecipeModel model;
	
	MyKitchen kitchen;

	@Before
	public void setUp() throws Exception
	{
		model = new RecipeModel();
		
		// we make sure we start with an empty model
		model.getAllRecipes().removeAll(model.getAllRecipes());
	}

	@Test
	public void testAdd()
	{
		Recipe recipe = new Recipe("spaghetti", "test", "test", new ArrayList<Ingredient>(), false);
		
		model.add(recipe);
		
		// make sure there is at least one recipe
		assertEquals(model.getAllRecipes().size(), 1);
		
		// make sure it is the recipe we just added
		assertEquals(model.searchRecipe(new String[] { "spaghetti" }).size(), 1);
		
		// make sure it is the recipe we just added
		assertEquals(model.getRecipe(0).getName(), "spaghetti");
		
		// make sure it is the recipe we just added
		assertEquals(model.getRecipeById(recipe.getRecipeID()).getRecipeID(), recipe.getRecipeID());
	}
	
	@Test
	public void testRemove()
	{
		Recipe recipe1 = new Recipe("spaghetti", "test", "test", new ArrayList<Ingredient>(), false);
		Recipe recipe2 = new Recipe("rice", "test", "test", new ArrayList<Ingredient>(), false);
		
		model.add(recipe1);
		model.add(recipe2);
		
		model.remove(recipe1);
		
		// make sure there is one recipe
		assertEquals(model.getAllRecipes().size(), 1);
		
		// make sure the recipe that is in the list is the "rice"
		assertEquals(model.searchRecipe(new String[] { "rice" }).size(), 1);
		
		// make sure the recipe that is the list is the "rice"
		assertEquals(model.getRecipe(0).getName(), "rice");
	}
	
	@Test
	public void testAddRecipeTwice()
	{
		Recipe recipe1 = new Recipe("spaghetti", "test", "test", new ArrayList<Ingredient>(), false);
		Recipe recipe2 = new Recipe("spaghetti", "test", "test", new ArrayList<Ingredient>(), false);
		
		model.add(recipe1);
		model.add(recipe2);
				
		// make sure there is one recipe
		assertEquals(model.getAllRecipes().size(), 2);
		
		// make sure there are two spaghetti's in the list
		assertEquals(model.searchRecipe(new String[] { "spaghetti" }).size(), 2);
		
		// make sure both sppaghetti's are in the list
		if (model.getRecipe(0).equals(recipe1)) {
			assertEquals(model.getRecipe(1), recipe2);
		}
		else {
			assertEquals(model.getRecipe(0), recipe2);
			assertEquals(model.getRecipe(1), recipe1);
		}
	}
	
	@Test
	public void testSearchKeyInName()
	{
		Recipe recipe1 = new Recipe("spaghetti", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe2 = new Recipe("spaghetti", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe3 = new Recipe("tomato_spaghetti_tomato", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe4 = new Recipe("tomato", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe5 = new Recipe("paghett", "", "", new ArrayList<Ingredient>(), false);
		
		model.add(recipe1);
		model.add(recipe2);
		model.add(recipe3);
		model.add(recipe4);
		model.add(recipe5);
						
		List<Recipe> spaghettis = model.searchRecipe(new String[] { "spaghetti" });
		List<Recipe> tomatos = model.searchRecipe(new String[] { "tomato" });
		
		// if you search for "spaghetti", there must be three results
		assertEquals(spaghettis.size(), 3);
		
		// and they should be recipes 1, 2 and 3
		List<Recipe> expectedResults = new LinkedList<Recipe>();
		expectedResults.add(recipe1);
		expectedResults.add(recipe2);
		expectedResults.add(recipe3);
		checkExpectedResults(spaghettis, expectedResults);
		
		// if you search for "tomato", there must be two results
		assertEquals(tomatos.size(), 2);
		
		// and they should be recipes 3 and 4
		expectedResults.add(recipe3);
		expectedResults.add(recipe4);
		checkExpectedResults(tomatos, expectedResults);
	}
	
	@Test
	public void testSearchWithIngredients()
	{
		Ingredient ingredient1 = new Ingredient("tomato", 12.0, "unit");
		Ingredient ingredient2 = new Ingredient("toma", 12.0, "unit"); // "toma" ==> substring of "tomato"
		Ingredient ingredient3 = new Ingredient("bacon", 1.0, "unit");
		Ingredient ingredient4 = new Ingredient("rice", 1.0, "unit");
		Ingredient ingredient5 = new Ingredient("tomato", 5.0, "kg");
		
		Recipe recipe1 = new Recipe("spaghetti", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe2 = new Recipe("spaghetti", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe3 = new Recipe("spaghetti", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe4 = new Recipe("rice", "", "", new ArrayList<Ingredient>(), false);
		Recipe recipe5 = new Recipe("spaghetti", "", "", new ArrayList<Ingredient>(), false);
		
		// there is no rice and no toma's in the kitchen
		ArrayList<Ingredient> kitchen = new ArrayList<Ingredient>();
		kitchen.add(ingredient1); // there are 12 tomatos in the kitchen
		kitchen.add(ingredient3);
		
		try {
			// recipe1 ==> a spaghetti with 12 tomato's and 12 toma's
			// shouldn't be a result -> there is no toma in the kitchen
			recipe1.addIngredient(ingredient1);
			recipe1.addIngredient(ingredient2);
			
			// recipe2 ==> a spaghetti with rice and 5 kg of tomato
			// shouldn't be a result -> there is no rice in the kitchen
			recipe2.addIngredient(ingredient4);
			recipe2.addIngredient(ingredient5);
			
			// recipe3 ==> a spaghetti with nothing
			// should be a result
			// if the recipe has nothing, so we'll always have the ingredients to cook it

			// recipe4 ==> rice that doesn't take rice, but 5 kg of tomato
			// we're looking for rice (the recipe, not the ingredient)
			// so this should be a result, because we have tomato's in the kitchen
			recipe4.addIngredient(ingredient5);
			
			// recipe5 ==> a spaghetti that takes tomato's and no toma's
			// should be a result ==> we have tomatos
			recipe5.addIngredient(ingredient1);
		} catch (DuplicateIngredientException e) {
			e.printStackTrace();
		}
		
		model.add(recipe1);
		model.add(recipe2);
		model.add(recipe3);
		model.add(recipe4);
		model.add(recipe5);
						
		List<Recipe> spaghettis = model.searchWithIngredient(new String[] { "spaghetti" }, kitchen);
		List<Recipe> rice = model.searchRecipe(new String[] { "rice" });
		
		// if you search for "spaghetti", there must be two results
		assertEquals(spaghettis.size(), 2);
		
		// and they should be recipes 1, 2 and 3
		List<Recipe> expectedResults = new LinkedList<Recipe>();
		expectedResults.add(recipe3);
		expectedResults.add(recipe5);
		checkExpectedResults(spaghettis, expectedResults);
		
		// if you search for "rice", there must be one result
		assertEquals(rice.size(), 1);
		
		// and it should be recipe 4
		expectedResults.add(recipe4);
		checkExpectedResults(rice, expectedResults);
	}
	
	private void checkExpectedResults(List<Recipe> results, List<Recipe> expectedResults) {
		for (Recipe recipe : results) {
			assertTrue(expectedResults.contains(recipe));
			expectedResults.remove(recipe);
		}
	}

}
