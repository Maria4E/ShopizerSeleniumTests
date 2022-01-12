package pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageCheckout {
	
	@FindBy (xpath = "//h1[contains(., 'Paiement')]")
	WebElement pageTitle;

	public WebElement getPageTitle() {
		return pageTitle;
	}

}
