package shopizer;

import org.slf4j.Logger;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.LoggerFactory;

import pageobject.PageCheckout;
import pageobject.PageIndex;
import pageobject.PageProceedOrder;


public class AUT001_UseCartTest extends AbstractTest {
	
	@Before
	public void setup() {
		selectBrowser(browser);
		
	}
	
	@Test
	public void addProductToCartTest() throws InterruptedException {
		Logger log = LoggerFactory.getLogger(AUT001_UseCartTest.class);
		driver.get(mainPageAdress);
		
		log.info("***1. Acces a Shopizer***");
		driver.get(mainPageAdress);
		Assert.assertTrue("La page ne s'est pas ouverte correctement", driver.getCurrentUrl().contains(mainPageAdress));
		
		log.info("***2. Ajout d'un produit aléatoire dans le panier***");
		PageIndex pageIndex = PageFactory.initElements(driver, PageIndex.class);
		//Formattage du prix attendu
		List<String> addedArticleInfo = pageIndex.addRandomArticleToCart(driver, wait);
		Double expectedPrice = Double.parseDouble(addedArticleInfo.get(1));
		Double doubleExpectedPrice = expectedPrice * 2;
		String formattedExpectedPrice = NumberFormat.getNumberInstance(Locale.US).format(expectedPrice);
		String formattedDoubleExpectedPrice = NumberFormat.getNumberInstance(Locale.US).format(doubleExpectedPrice);
		
		//wait.until(ExpectedConditions.invisibilityOf(pageIndex.getLoadingOverlay()));
		Thread.sleep(500);
		wait.until(ExpectedConditions.visibilityOf(pageIndex.getCartLink()));
		js.executeScript("arguments[0].scrollIntoView();", pageIndex.getCartLink());
		wait.until(ExpectedConditions.visibilityOf(pageIndex.getCartObjectsNr()));
		Assert.assertTrue("Erreur d'ajout du produit au panier", pageIndex.getCartObjectsNr().getText().equals("(1)"));
		
		log.info("***3. Selection du panier et clic sur [Paiement]***");
		PageProceedOrder proceedOrder = pageIndex.goToPageProceedOrder(driver);
		Assert.assertEquals("La page Passez votre commande n'a pas été atteinte", "Revoir votre commande", proceedOrder.getPageTitle().getText().trim());
		
		log.info("***4. Verification de la presence du produit ajoute sur la page***");
		wait.until(ExpectedConditions.elementToBeClickable(proceedOrder.getCheckOutButton()));
		//Tableau
		wait.until(ExpectedConditions.visibilityOf(proceedOrder.getOrderListTable()));
		Assert.assertTrue("Tableau d'elements n'est pas present sur la page", proceedOrder.getOrderListTable().isDisplayed());
		//Image
		Assert.assertTrue("L'image du produit n'est pas present sur la page", proceedOrder.getItemImage().isDisplayed());
		//Nom
		Assert.assertTrue("Le nom du produit ne correspond pas a celui du produit selectionne", addedArticleInfo.get(0).equals(proceedOrder.getItemName().getText()));
		//Quantite
		Assert.assertTrue("La quantite n'est pas egale a 1", proceedOrder.getItemQuantity().getAttribute("value").equals("1"));
		//Prix par article
		Assert.assertTrue("Le prix du produit ne correspond pas a celui du produit selectionne", formattedExpectedPrice.equals(proceedOrder.getItemRealPrice()));
		//Sous-total par section
		Assert.assertTrue("Le sous-total n'est pas correct", formattedExpectedPrice.equals(proceedOrder.getItemRealTotalPrice()));
		
		log.info("***5. Redoublement de la quantite du produit***");
		proceedOrder.doubleQuantity();
		Assert.assertTrue("Le prix du produit a change", formattedExpectedPrice.equals(proceedOrder.getItemRealPrice()));
		Assert.assertTrue("Le sous-total de section a change", formattedExpectedPrice.equals(proceedOrder.getItemRealTotalPrice()));
		Assert.assertTrue("Le sous-total de la commande a change", formattedExpectedPrice.equals(proceedOrder.getOrderRealTotalPrice()));
		
		log.info("***6. Clic sur le bouton [Recalculer] et verification du resultat***");
		proceedOrder.updateTotals();
		wait.until(ExpectedConditions.elementToBeClickable(proceedOrder.getCheckOutButton()));
		Thread.sleep(1000);
		
		Assert.assertTrue("Le prix du produit a change", formattedExpectedPrice.equals(proceedOrder.getItemRealPrice()));
		Assert.assertTrue("Le sous-total de la section n'a pas change", formattedDoubleExpectedPrice.equals(proceedOrder.getItemRealTotalPrice()));
		Assert.assertTrue("Le sous-total de la commande a change", formattedDoubleExpectedPrice.equals(proceedOrder.getOrderRealTotalPrice()));
		
		log.info("***7. Clic sur le bouton [Effectuer le paiement] et verification de l'ouverture de la page de paiement***");
		PageCheckout pageCheckout = proceedOrder.goToCheckout(driver);
		Assert.assertTrue(driver.getCurrentUrl().contains("checkout"));
		Assert.assertTrue(pageCheckout.getPageTitle().isDisplayed());
		
		log.info("***Tous les pas de test ont ete executes en succes***");
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}

}
