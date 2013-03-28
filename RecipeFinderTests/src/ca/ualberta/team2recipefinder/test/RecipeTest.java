package ca.ualberta.team2recipefinder.test;


import java.util.List;

import junit.framework.TestCase;
import ca.ualberta.team2recipefinder.model.DuplicateIngredientException;
import ca.ualberta.team2recipefinder.model.Ingredient;
import ca.ualberta.team2recipefinder.model.Recipe;



public class RecipeTest extends TestCase {
	
	public void testGetRecipeID(){
		Recipe recipe = new Recipe();  
		assertNotNull("Recipe ID is null", recipe.getRecipeID());
	}

	public void testGetName() {
		Recipe recipe = new Recipe();  
		recipe.setName("test");  
		assertEquals("Name", "test", recipe.getName());
	}

	public void testToString() {
		Recipe recipe = new Recipe();  
		recipe.setName("test");  
		assertEquals("Name", "test", recipe.toString());
	}

	public void testSetName() {
		Recipe recipe = new Recipe();  
		recipe.setName("test");  
		assertEquals("Name", "test", recipe.getName());
	}

	public void testGetProcedure() {
		Recipe recipe = new Recipe();  
		recipe.setProcedure("test");  
		assertEquals("Procedure", "test", recipe.getProcedure());
	}

	public void testSetProcedure() {
		Recipe recipe = new Recipe();  
		recipe.setProcedure("test");  
		assertEquals("Procedure", "test", recipe.getProcedure());
	}

	public void testGetAuthor() {
		Recipe recipe = new Recipe();  
		recipe.setAuthor("test");  
		assertEquals("Author", "test", recipe.getAuthor());
	}

	public void testSetAuthor() {
		Recipe recipe = new Recipe();  
		recipe.setAuthor("test");  
		assertEquals("Author", "test", recipe.getAuthor());
	}

	public void testGetIngredients() {
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

	public void testRemoveIngredient() {
		Recipe recipe = new Recipe();  
		Ingredient ingredient1 = new Ingredient("test1", (double) 5, "test1");    
		Ingredient ingredient2 = new Ingredient("test2", (double) 6, "test2");
		
		try {
			recipe.addIngredient(ingredient1);
			recipe.addIngredient(ingredient2);
		} catch (DuplicateIngredientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recipe.removeIngredient(ingredient1);
		assertEquals("Remove Ingredient", ingredient2, recipe.getIngredients().get(0));		
	}

	public void testReplaceIngredient() {
		Recipe recipe = new Recipe();  
		Ingredient oldIngredient = new Ingredient("old", (double) 5, "old");
		Ingredient newIngredient = new Ingredient("new", (double) 6, "new");
		
		try {
			recipe.addIngredient(oldIngredient);
		} catch (DuplicateIngredientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recipe.replaceIngredient(oldIngredient, newIngredient);
		assertEquals("Replace Ingredient", newIngredient, recipe.getIngredients().get(0));
	}

	public void testAddIngredient() {

		Recipe recipe = new Recipe();  
		Ingredient ingredient = new Ingredient("test", (double) 5, "test");        
		try {
			recipe.addIngredient(ingredient);
		}
		catch (DuplicateIngredientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Get Ingredient", ingredient, recipe.getIngredient(ingredient.getType()));	
	}

	public void testGetIngredient() {
		Recipe recipe = new Recipe();  
		Ingredient ingredient = new Ingredient("test", (double) 5, "test");        
		try {
			recipe.addIngredient(ingredient);
		}
		catch (DuplicateIngredientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Get Ingredient", ingredient, recipe.getIngredient(ingredient.getType()));
	}

	public void testGetOnServer() {
		Recipe recipe = new Recipe();  
		recipe.setOnServer(true);  
		assertEquals("GetOnServer", true , recipe.getOnServer());
	}

	public void testSetOnServer() {
		Recipe recipe = new Recipe();  
		recipe.setOnServer(true);  
		assertEquals("GetOnServer", true , recipe.getOnServer());
	}
/*
	public void TestAddPhoto() {
		Recipe recipe = new Recipe();
		Bitmap bitmap = new Bitmap();
		Color color = Color.BLUE;
		bitmap.setPixel(0, 0, color);
		recipe.addPhoto(bitmap);
		assertEquals("Add Photo", color, recipe.getPhoto(0).getPixel(0, 0));
	}

	@Test
	public void TestGetAllPhotos() {
		Recipe recipe = new Recipe();
		Bitmap bitmap1 = new Bitmap();
		Bitmap bitmap2 = new Bitmap();
		Color color1 = Color.BLUE;
		Color color2 = Color.GREEN;
		bitmap1.setPixel(0, 0, color1);
		bitmap2.setPixel(0, 0, color2);
		recipe.addPhoto(bitmap1);
		recipe.addPhoto(bitmap2);
		List<Bitmap> bitmapList = recipe.getAllPhotos();
		assertTrue("Get All Photos", (color1 == bitmapList[0].getPixel(0,0) && color2 == bitmapList[1].getPixel(0,0));
	}

	@Test
	public void TestRemovePhoto() {
		Recipe recipe = new Recipe();
		Bitmap bitmap = new Bitmap();
		recipe.addPhoto(bitmap);
		recipe.removePhoto(0);
		assertTrue("Remove Photo", recipe.hasPhotos());
	}

	@Test
	public void TestGetPhoto() {
		Recipe recipe = new Recipe();
		Bitmap bitmap = new Bitmap();
		Color color = Color.BLUE;
		bitmap.setPixel(0, 0, color);
		recipe.addPhoto(bitmap);
		assertEquals("Get Photo", color, recipe.getPhoto(0).getPixel(0, 0));
	}

	
	public void testAddComment() {
		Recipe recipe = new Recipe();
		recipe.addComment("testing");
		assertEquals("Add Comment", "testing", recipe.getComment(0));
	}

	public void testGetAllComments() {
		Recipe recipe = new Recipe();
		recipe.addComment("testing1");
		recipe.addComment("testing2");
		List<String> comments = recipe.getAllComments();
		assertTrue("Get All Comments", (comments[0] == "testing1" && comments[1] == "testing2"));
	}
*/
	public void testGetComment() {
		Recipe recipe = new Recipe();
		recipe.addComment("testing");
		assertEquals("Get Comment", "testing", recipe.getComment(0));
	}

	public void testSetUserId() {
		Recipe recipe = new Recipe();
		recipe.setUserId("testing");
		assertEquals("Set User Id", "testing", recipe.getUserId());
	}

	public void TestGetUserId() {
		Recipe recipe = new Recipe();
		recipe.setUserId("testing");
		assertEquals("Get User Id", "testing", recipe.getUserId());
	}

	public void TestSetServerId() {
		Recipe recipe = new Recipe();
		recipe.setServerId("testing");
		assertEquals("Set Server Id", "testing", recipe.getServerId());
	}

	public void TestGetServerId() {
		Recipe recipe = new Recipe();
		recipe.setServerId("testing");
		assertEquals("Set Server Id", "testing", recipe.getServerId());
	}

}
