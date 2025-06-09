package Page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductListPage {
    @FindBy(xpath = "//div[contains(@class,'Showcase-mk__name') and contains(translate(., 'ARROZ', 'arroz'), 'arroz')]")
    private WebElement searchProduct;
    @FindBy(xpath = "//*[@id='search_box']")
    private WebElement buscadorMakroPLP;

    WebDriver driver;

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean validateProduct(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String actualProduct = wait.until(ExpectedConditions.visibilityOf(searchProduct)).getText();
        String typedProduct = buscadorMakroPLP.getAttribute("value");
        if(actualProduct.toLowerCase().contains(typedProduct.toLowerCase())){
            System.out.println("El producto coincide");
            return true;
        } else {
            System.out.println("El producto no coincide");
            System.out.println("Escribiste: " + typedProduct + "y el encontrado es: " + actualProduct);
            return false;
        }
    }
}
