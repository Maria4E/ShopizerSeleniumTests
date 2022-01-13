package shopizer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pageobject.PageCategoryTable;
import pageobject.PageIndex;
import pageobject.PageProductDetails;

public class AUT002_VerifyTableCategoryTest extends AbstractTest {
	
	private String currency = "US$";
	
	@Before
	public void setup() {
		selectBrowser(browser);
		
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}
	
	@Test
	public void verifyTableCategoryTest() throws InterruptedException {
		
		Logger log = LoggerFactory.getLogger(AUT002_VerifyTableCategoryTest.class);
		
		
		log.info("***1. Acces a Shopizer, selection de la categorie Tables et verification de la presence des elements***");
		driver.get(mainPageAdress);
		PageIndex pageIndex = PageFactory.initElements(driver, PageIndex.class);
		PageCategoryTable categoryTable = pageIndex.goToPageCategoryTable(driver);
		//Calcul du nombre d'elements sur la page
		int initialItemQuantity = categoryTable.getItemContainers().size();
		Assert.assertTrue("Il n'y a pas d'elements sur la page", initialItemQuantity!=0);
		
		log.info("***2. Verification de la presence des informations des elements***");
		//Creation des listes de donnees initiales
		List<String> initialItemsImagesSrc = categoryTable.getItemImagesSrc();
		List<String> initialItemsDisplayedNames = categoryTable.getItemsDisplayedNames();
		List<String> initialItemsDisplayedPrices = categoryTable.getItemsDisplayedPrices();
		
		//Presence d'images
		Assert.assertTrue("Certaines images manquent", initialItemQuantity==initialItemsImagesSrc.size());
		Assert.assertFalse("Certaines images ne sont pas affichees", initialItemsImagesSrc.contains(""));
		//Presence de noms
		Assert.assertTrue("Certains noms manquent", initialItemQuantity==initialItemsDisplayedNames.size());
		Assert.assertFalse("Certains noms ne sont pas affiches", initialItemsDisplayedNames.contains(""));
		//Presence de prix
		Assert.assertTrue("Certains prix manquent", initialItemQuantity==initialItemsDisplayedPrices.size());
		Assert.assertFalse("Certains prix ne sont pas affiches", initialItemsDisplayedPrices.contains(""));
		//Presence d'une devise pour chaque prix
		for(String s : initialItemsDisplayedPrices) {
			
			Assert.assertTrue("La devise n'est pas affichee pour certains prix", currency.equals(s.substring(0,3)));
			
		}
		//Presence des boutons [Ajouter au panier]
		Assert.assertTrue("Certains boutons Ajouter au panier manquent", initialItemQuantity==categoryTable.getAddToCartButtons().size());
		
		log.info("***3. Selection du filtre Asian Woods et verification de son fonctionnement***");
		/*if(pageIndex.getLoadingOverlay().getSize()!=null) {
			System.out.println("Yes");
			wait.until(ExpectedConditions.invisibilityOf(pageIndex.getLoadingOverlay()));
		}*/
		wait.until(ExpectedConditions.elementToBeClickable(categoryTable.getFilterLink()));
		categoryTable.clickFilterLink();
		wait.until(ExpectedConditions.elementToBeClickable(categoryTable.getAddToCartButtons().get(0)));
		//Verification du fonctionnement du filtre
		int filteredItemsQuantity = categoryTable.getItemContainers().size();
		Assert.assertTrue("Le filtre n'a pas fonctionne", filteredItemsQuantity<initialItemQuantity);
		
		//Verification de la presence de l'article sur la page initiale
		//Image
		for(String s : categoryTable.getItemImagesSrc()) {
			Assert.assertTrue("Image incorrecte du produit filtre", initialItemsImagesSrc.contains(s));
		}
		//Nom
		for(String s : categoryTable.getItemsDisplayedNames()) {
			Assert.assertTrue("Nom incorrect du produit filtre", initialItemsDisplayedNames.contains(s));
		}
		
		log.info("***4-6. Vérification du detail des articles***");
		
		pageIndex.goToPageCategoryTable(driver);
		Thread.sleep(500);
		//PageProductDetails productDetails = categoryTable.debugClick(driver, wait);
		
		for(int i = 0; i < categoryTable.getItemContainers().size(); i++) {
			Thread.sleep(500);
			System.out.println("Article : " + i );
			wait.until(ExpectedConditions.elementToBeClickable(categoryTable.getItemDetailsLinks().get(i)));
			//js.executeScript("arguments[0].scrollIntoView(true);", categoryTable.getItemDetailsLinks().get(i));
			PageProductDetails productDetails = categoryTable.goToProductDetails(driver, i);
			//Meme image?
			Assert.assertTrue("Image differente dans le detail du produit", initialItemsImagesSrc.get(i).equals(productDetails.getProductImage().getAttribute("src")));
			//Meme nom?
			Assert.assertTrue("Nom different dans le detail du produit", initialItemsDisplayedNames.get(i).equalsIgnoreCase(productDetails.getProductName().getText().trim()));
			//Meme prix?
			Assert.assertTrue("Prix different dans le detail du produit", initialItemsDisplayedPrices.get(i).equals(productDetails.getProductPrice().getText()));
			//Meme devise?
			Assert.assertTrue("Devise incorrecte dans le detail du produit", currency.equals(productDetails.getProductPrice().getText().substring(0, 3)));
			//Bouton [Ajouter au panier] ?
			Assert.assertTrue("Bouton Ajouter au panier absent", productDetails.getAddToCartButton().isDisplayed());
			//Etoiles ?
			Assert.assertTrue("Toutes les etoiles ne sont pas affichees", productDetails.getProductStars().size()==5);
			driver.navigate().back();
		}
		
		log.info("***Tous les pas de test executes en succes***");
	}
		
}
	
