package pageobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageIndex {

	@FindBy (xpath = "//a[@class='addToCart']")
	List<WebElement> addToCartLinks;
	
	@FindBy (xpath = "//div[@id='miniCartSummary']/a")
	WebElement cartLink;
	
	@FindBy (xpath = "//div[@id='miniCartSummary']/a/font")
	WebElement cartObjectsNr;
	
	@FindBy (xpath = "//ul[@id='miniCartDetails']//a[contains(text(),'Paiement')]")
	WebElement proceedToPaymentLink;
	
	@FindBy (xpath = "//div[contains(@class, 'mainmenu')]//a[contains(@href, 'category/tables')]")
	WebElement tablesCategoryLink;
	
	@FindBy (xpath = "//div[@class='loadingoverlay']")
	WebElement loadingOverlay;
	
	
	
	public WebElement getCartLink() {
		return cartLink;
	}

	public WebElement getCartObjectsNr() {
		return cartObjectsNr;
	}
	
	public WebElement getLoadingOverlay() {
		return loadingOverlay;
	}

	public List<String> addRandomArticleToCart(WebDriver driver, WebDriverWait wait) {
		Random rand = new Random();
		wait.until(ExpectedConditions.elementToBeClickable(addToCartLinks.get(addToCartLinks.size()-1)));
		int randomElement = rand.nextInt(addToCartLinks.size());
		WebElement articleToAdd = addToCartLinks.get(randomElement);
	
		if(randomElement > 3) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", articleToAdd);
		}
		String articleToAddId = articleToAdd.getAttribute("productid");
		WebElement articleToAddDiv = driver.findElement(By.xpath("//div[@data-id='"+articleToAddId+"']"));
		List<String> articleToAddInfo = new ArrayList<>();
		articleToAddInfo.add(0, articleToAddDiv.getAttribute("item-name"));
		articleToAddInfo.add(1, articleToAddDiv.getAttribute("item-price"));
		
		Actions action = new Actions(driver);
		action.moveToElement(articleToAdd).perform();
		articleToAdd.click();
		return articleToAddInfo;
		
	}
	
	public PageProceedOrder goToPageProceedOrder(WebDriver driver) throws InterruptedException {
		
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		wait.until(ExpectedConditions.visibilityOf(cartLink));
		action.moveToElement(cartLink).build().perform();
		Thread.sleep(500);
		//wait.until(ExpectedConditions.elementToBeClickable(proceedToPaymentLink));
		action.moveToElement(proceedToPaymentLink).build().perform();
		proceedToPaymentLink.click();
		return PageFactory.initElements(driver, PageProceedOrder.class);
	}
	
	public PageCategoryTable goToPageCategoryTable(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(tablesCategoryLink));
		Actions action = new Actions(driver);
		action.moveToElement(tablesCategoryLink).perform();
		tablesCategoryLink.click();
		
		return PageFactory.initElements(driver, PageCategoryTable.class);
	}
	
}
