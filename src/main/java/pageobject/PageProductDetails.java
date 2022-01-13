package pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageProductDetails {
	
	@FindBy (xpath = "//a[@class='image-link']/img")
	WebElement productImage;
	
	@FindBy (xpath = "//div[@class='sinple-c-title']")
	WebElement productName;
	
	@FindBy (xpath = "//div[@id='productRating']/img")
	List<WebElement> productStars;
	
	@FindBy (xpath = "//span[@itemprop='price']")
	WebElement productPrice;
	
	@FindBy (xpath = "//button[contains(@class, 'addToCart')]")
	WebElement addToCartButton;

	public WebElement getProductImage() {
		return productImage;
	}

	public WebElement getProductName() {
		return productName;
	}

	public List<WebElement> getProductStars() {
		return productStars;
	}

	public WebElement getProductPrice() {
		return productPrice;
	}

	public WebElement getAddToCartButton() {
		return addToCartButton;
	}
	
	

}
