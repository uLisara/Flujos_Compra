package Page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DeliveryPage {
    WebDriver driver;

    @FindBy(xpath = "//button[contains(@class, 'preferences-data__button preferences-data__tooltip-button gtm_element')]")
    private WebElement preferencia;
    @FindBy(xpath = "//input[@aria-describedby='emailHelp']")
    private WebElement correo;
    @FindBy(xpath = "//span[contains(@class, 'ModalEmail__checkmark')]")
    private WebElement check;
    @FindBy(xpath = "//button[contains(@class, 'button-modal is-active')]")
    private WebElement btnpreferencias;
    @FindBy(xpath = "//input[@id='searchAddressField']")
    private WebElement addressF;
    @FindBy(xpath = "//div[@id='gm-autocomplete-container']//ul/li")
    private WebElement autocompleteaddress;
    @FindBy (xpath = "//input[@type='number']")
    private WebElement numberaddress;
    @FindBy (xpath = "//div[contains(@class, 'Select__control')]")
    private WebElement city;
    @FindBy(xpath = "//div[contains(@class, 'Select__control')]//input")
    private WebElement citylist;
    @FindBy(xpath = "//div[contains(@class, 'ModalForm__form-control-Select__menu')]//div")
    private List<WebElement> citysuggest;
    @FindBy (xpath = "//button[@class='button-modal is-active']")
    private WebElement btnaddress;


    public DeliveryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickloginAdress(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement preferenciaButton = wait.until(ExpectedConditions.elementToBeClickable(preferencia));
        preferenciaButton.click();
    }

    public void enterEmail(String email){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement correoButton = wait.until(ExpectedConditions.elementToBeClickable(correo));
        correoButton.sendKeys(email);
    }
    public void clickCheck() {
        check.click();
    }
    public void clickContinue(){
        btnpreferencias.click();
    }
    public void enterAddress(String address){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement addressButton = wait.until(ExpectedConditions.elementToBeClickable(addressF));
        addressButton.sendKeys(address);
        WebElement suggestaddress = wait.until(ExpectedConditions.visibilityOf(autocompleteaddress));
        wait.until(ExpectedConditions.visibilityOf(autocompleteaddress));
        WebElement firstsuggestAddress =  wait.until(ExpectedConditions.elementToBeClickable(autocompleteaddress));
        firstsuggestAddress.click();
        wait.until(ExpectedConditions.attributeToBeNotEmpty(addressButton, "value"));

    }

    public void enterAddressNumber(String number) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement numberButton = wait.until(ExpectedConditions.elementToBeClickable(numberaddress));
        numberButton.sendKeys(number);
    }
    public void enterDistrict(String district) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement cityButton = wait.until(ExpectedConditions.elementToBeClickable(city));
        cityButton.click();
        WebElement writeCity = wait.until(ExpectedConditions.elementToBeClickable(citylist));
        writeCity.sendKeys(district);
        wait.until(ExpectedConditions.visibilityOfAllElements(citysuggest));

        if (!citysuggest.isEmpty()){
            citysuggest.get(0).click();
        }


    }
    public void clickSaveAddress(){
        btnaddress.click();
    }

}
