package pageobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
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
	
	
	
	public WebElement getCartLink() {
		return cartLink;
	}

	public WebElement getCartObjectsNr() {
		return cartObjectsNr;
	}

	public List<String> addRandomArticleToCart(WebDriver driver) {
		Random rand = new Random();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		WebElement articleToAdd = addToCartLinks.get(rand.nextInt(addToCartLinks.size()));
		String articleToAddId = articleToAdd.getAttribute("productid");
		WebElement articleToAddDiv = driver.findElement(By.xpath("//div[@data-id='"+articleToAddId+"']"));
		List<String> articleToAddInfo = new ArrayList<>();
		articleToAddInfo.add(0, articleToAddDiv.getAttribute("item-name"));
		articleToAddInfo.add(1, articleToAddDiv.getAttribute("item-price"));
		articleToAdd.click();
		return articleToAddInfo;
		
	}
	
	public PageProceedOrder goToPageProceedOrder(WebDriver driver) {
		
		Actions action = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		wait.until(ExpectedConditions.visibilityOf(cartLink));
		action.moveToElement(cartLink).perform();
		wait.until(ExpectedConditions.visibilityOf(proceedToPaymentLink));
		action.moveToElement(proceedToPaymentLink).perform();
		proceedToPaymentLink.click();
		return PageFactory.initElements(driver, PageProceedOrder.class);
	}
	
}
