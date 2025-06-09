package Page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.Key;
import java.time.Duration;

public class HomePage {
    WebDriver driver;

    @FindBy(xpath = "//img[contains(@src, 'logo-makro-mobile.svg')]")
    private WebElement logoMakro;
    @FindBy(xpath = "//*[@id='search_box']")
    private WebElement buscadorMakro;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openHomePage() {
        driver.get("https://www.makro.plazavea.com.pe/");
    }

    public boolean isLogoDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement logo = wait.until(ExpectedConditions.visibilityOf(logoMakro));
            return logo.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("No se encontró el logo de Makro.");
            return false;
        }
    }
    public void writeProduct (String product){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement numberButton = wait.until(ExpectedConditions.elementToBeClickable(buscadorMakro));
        numberButton.sendKeys(product);
        numberButton.sendKeys(Keys.ENTER);
    }



}
