package ca.ualberta.team2recipefinder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ca.ualberta.team2recipefinder.DuplicateIngredientException;
import ca.ualberta.team2recipefinder.Ingredient;
import ca.ualberta.team2recipefinder.Recipe;



public class RecipeTest
{



  @Test
	public void testGetRecipeID(){
    Recipe recipe = new Recipe();  
	assertNotNull("Recipe ID is null", recipe.getRecipeID());
	}

	@Test
	public void testGetName()
	{
        Recipe recipe = new Recipe();  
        recipe.setName("test");  
        assertEquals("Name", "test", recipe.getName());
	}

	@Test
	public void testToString()
	{
        Recipe recipe = new Recipe();  
        recipe.setName("test");  
        assertEquals("Name", "test", recipe.toString());
	}

	@Test
	public void testSetName()
	{
        Recipe recipe = new Recipe();  
        recipe.setName("test");  
        assertEquals("Name", "test", recipe.getName());
	}

	@Test
	public void testGetProcedure()
	{
        Recipe recipe = new Recipe();  
        recipe.setProcedure("test");  
        assertEquals("Procedure", "test", recipe.getProcedure());
	}

	@Test
	public void testSetProcedure()
	{
        Recipe recipe = new Recipe();  
        recipe.setProcedure("test");  
        assertEquals("Procedure", "test", recipe.getProcedure());
	}

	@Test
	public void testGetAuthor()
	{
        Recipe recipe = new Recipe();  
        recipe.setAuthor("test");  
        assertEquals("Author", "test", recipe.getAuthor());
	}

	@Test
	public void testSetAuthor()
	{
        Recipe recipe = new Recipe();  
        recipe.setAuthor("test");  
        assertEquals("Author", "test", recipe.getAuthor());
	}

	@Test
	public void testGetIngredients()
	{
        Recipe recipe = new Recipe();  
        Ingredient ingredient = new Ingredient("test", (double) 5, "test");        
        try
		{
			recipe.addIngredient(ingredient);
		} catch (DuplicateIngredientException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals("Ingredient", ingredient, recipe.getIngredients().get(0));
	}

	@Test
	public void testRemoveIngredient()
	{
		Recipe recipe = new Recipe();  
        Ingredient ingredient1 = new Ingredient("test1", (double) 5, "test1");    
        Ingredient ingredient2 = new Ingredient("test2", (double) 6, "test2");
        try
		{
			recipe.addIngredient(ingredient1);
			recipe.addIngredient(ingredient2);
		} catch (DuplicateIngredientException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        recipe.removeIngredient(ingredient1);
        assertEquals("Remove Ingredient", ingredient2, recipe.getIngredients().get(0));		
	}

	@Test
	public void testReplaceIngredient()
	{
		Recipe recipe = new Recipe();  
        Ingredient oldIngredient = new Ingredient("old", (double) 5, "old");
        Ingredient newIngredient = new Ingredient("new", (double) 6, "new");
        try
		{
			recipe.addIngredient(oldIngredient);
		} catch (DuplicateIngredientException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        recipe.replaceIngredient(oldIngredient, newIngredient);
        assertEquals("Replace Ingredient", newIngredient, recipe.getIngredients().get(0));
	}

	@Test
	public void testAddIngredient()
	{
	
	    Recipe recipe = new Recipe();  
	    Ingredient ingredient = new Ingredient("test", (double) 5, "test");        
	    try
	    {
	    	recipe.addIngredient(ingredient);
		} catch (DuplicateIngredientException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        assertEquals("Get Ingredient", ingredient, recipe.getIngredient(ingredient.getType()));	
	}

	@Test
	public void testGetIngredient()
	{
        Recipe recipe = new Recipe();  
        Ingredient ingredient = new Ingredient("test", (double) 5, "test");        
        try
		{
			recipe.addIngredient(ingredient);
		} catch (DuplicateIngredientException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals("Get Ingredient", ingredient, recipe.getIngredient(ingredient.getType()));
	}

	@Test
	public void testGetOnServer()
	{
        Recipe recipe = new Recipe();  
        recipe.setOnServer(true);  
        assertEquals("GetOnServer", true , recipe.getOnServer());
	}

	@Test
	public void testSetOnServer()
	{
        Recipe recipe = new Recipe();  
        recipe.setOnServer(true);  
        assertEquals("GetOnServer", true , recipe.getOnServer());
	}

}
