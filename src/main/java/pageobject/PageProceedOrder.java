package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageProceedOrder {
	
	@FindBy (xpath = "//table[@id='mainCartTable']")
	WebElement orderListTable;
	
	@FindBy (xpath = "//td[@data-th='Article']//img")
	WebElement itemImage;
	
	@FindBy (xpath = "//td[@data-th='Article']//span")
	WebElement itemName;
	
	@FindBy (xpath = "//input[@name='quantity']")
	WebElement itemQuantity;
	
	@FindBy (xpath = "//td[@data-th='Prix']")
	WebElement itemPrice;
	
	@FindBy (xpath = "//td[@data-th='Total']")
	WebElement itemTotalPrice;
	
	@FindBy (xpath = "//tr[@class='cart-subtotal']/th[contains(.,'Total')]/following-sibling::td")
	WebElement orderTotal;
	
	@FindBy (xpath = "//div[@class='buttons-cart']/a[contains(.,'Recalculer')]")
	WebElement recalculateButton;
	
	@FindBy (xpath = "//a[contains(@href, 'checkout')]")
	WebElement proceedToCheckOutButton;

	public WebElement getOrderListTable() {
		return orderListTable;
	}

	public WebElement getItemImage() {
		return itemImage;
	}

	public WebElement getItemName() {
		return itemName;
	}

	public WebElement getItemQuantity() {
		return itemQuantity;
	}
	
	public WebElement getCheckOutButton() {
		return proceedToCheckOutButton;
	}

	public String getItemRealPrice() {
		String itemRealPrice = itemPrice.getText().trim().substring(3);
		return itemRealPrice;
	}

	public String getItemRealTotalPrice() {
		String priceRealTotal = itemTotalPrice.getText().trim().substring(3);
		return priceRealTotal;
	}
	
	public String getOrderRealTotalPrice() {
		String orderRealTotalPrice = orderTotal.getText().trim().substring(3);
		return orderRealTotalPrice;
	}
	
	public void doubleQuantity() {
		itemQuantity.clear();
		itemQuantity.sendKeys("2");
	}
	
	public void updateTotals() {
		recalculateButton.click();
	}
	
	public PageCheckout goToCheckout(WebDriver driver) {
		
		proceedToCheckOutButton.click();
		return PageFactory.initElements(driver, PageCheckout.class);
	}

}
