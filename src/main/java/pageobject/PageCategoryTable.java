package pageobject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageCategoryTable {

	@FindBy (xpath = "//div[@id='productsContainer']/div")
	List<WebElement> itemContainers;
	
	@FindBy (xpath = "//div[@id='productsContainer']/div//img")
	List<WebElement> itemImages;
	
	@FindBy (xpath = "//div[@id='productsContainer']/div//*[@itemprop='name']")
	List<WebElement> itemNames;
	
	@FindBy (xpath = "//div[@id='productsContainer']/div//h3")
	List<WebElement> itemLinks;
	
	@FindBy (xpath = "//div[@id='productsContainer']/div//*[@itemprop='price']")
	List<WebElement> itemPrices;
	
	@FindBy (xpath = "//div[@id='productsContainer']/div//a[@class='addToCart']")
	List<WebElement> addToCartButtons;
	
	@FindBy (xpath = "//a[contains(., 'Asian Wood')]")
	WebElement asianWoodsFilterLink;
	
	@FindBy (xpath = "//div[@id='productsContainer']//h3[contains(., 'Natural root console')]")
	WebElement debugLink;

	public List<WebElement> getItemContainers() {
		return itemContainers;
	}

	public List<WebElement> getItemDetailsLinks(){
		return itemLinks;
	}
	
	public List<WebElement> getAddToCartButtons() {
		return addToCartButtons;
	}
	
	public WebElement getFilterLink() {
		return asianWoodsFilterLink;
	}
	
		
	public List<String> getItemImagesSrc(){
		
		List<String> itemImagesSrc = new ArrayList<>();
		for(WebElement we : itemImages) {
			itemImagesSrc.add(we.getAttribute("src"));
		}
		return itemImagesSrc;		
	}
	
	public List<String> getItemsDisplayedNames(){
		
		List<String> itemsDisplayedNames = new ArrayList<>();
		for(WebElement we : itemNames) {
			itemsDisplayedNames.add(we.getText().trim());
		}
		return itemsDisplayedNames;
	}
	
	public List<String> getItemsDisplayedPrices(){
		
		List<String> itemsDisplayedPrices = new ArrayList<>();
		for(WebElement we : itemPrices) {
			itemsDisplayedPrices.add(we.getText());
		}
		return itemsDisplayedPrices;
	}
	
	public void clickFilterLink() {
		asianWoodsFilterLink.click();
	}
	
	public PageProductDetails debugClick(WebDriver driver, WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(debugLink));
		debugLink.click();
		return PageFactory.initElements(driver, PageProductDetails.class);
	}
	
	public PageProductDetails goToProductDetails(WebDriver driver, int productNumber) {
		//if(productNumber > 2) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", itemLinks.get(productNumber));
		//}
		Actions action = new Actions(driver);
		action.moveToElement(itemLinks.get(productNumber)).build().perform();
		itemLinks.get(productNumber).click();
		return PageFactory.initElements(driver, PageProductDetails.class);
	}
	
}
