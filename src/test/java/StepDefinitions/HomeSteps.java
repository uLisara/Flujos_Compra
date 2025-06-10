package StepDefinitions;

import Page.ProductListPage;
import io.cucumber.java.en.*;
import org.testng.Assert;
import Page.HomePage;
import Page.DeliveryPage;
import Utils.BaseTest;


public class HomeSteps {

    private HomePage homePage;
    private DeliveryPage deliveryPage;
    private ProductListPage productListPage;

    @Given("el usuario abre el navegador")
    public void elUsuarioAbreElNavegador() {
        BaseTest.initializeDriver();
        homePage = new HomePage(BaseTest.getDriver());
    }

    @When("el usuario navega a la página de Makro PlazaVea")
    public void elUsuarioNavegaALaPáginaDeMakroPlazaVea() {
        homePage.openHomePage();
    }

    @Then("la página debe mostrar el logo de Makro")
    public void laPáginaDebeMostrarElLogoDeMakro() {
        Assert.assertTrue(homePage.isLogoDisplayed(), "El logo de Makro no se encontró.");
        //BaseTest.closeDriver();
    }

    @And("el usuario ingresa sus preferencias de entrega")
    public void elUsuarioIngresaSusPreferenciasDeEntrega() {
        deliveryPage = new DeliveryPage(BaseTest.getDriver());
        deliveryPage.clickloginAdress();
        deliveryPage.enterEmail("yanira.ayala@indigitalxp.pe");
        deliveryPage.clickCheck();
        deliveryPage.clickContinue();
        deliveryPage.enterAddress("Av. Dionisio Derteano");
        deliveryPage.enterAddressNumber("18");
        deliveryPage.enterDistrict("San Isidro");
        deliveryPage.clickSaveAddress();

    }

    @And("el usuario busca un producto")
    public void elUsuarioBuscaUnProducto() {
        productListPage = new ProductListPage(BaseTest.getDriver());
        homePage.isLogoDisplayed();
        homePage.writeProduct("arroz");
        homePage.isLogoDisplayed();
        Assert.assertTrue(productListPage.validateProduct(),"Los productos no coinciden");
    }


    @And("agrega el producto al carrito")
    public void agregaElProductoAlCarrito() {
            System.out.println("🛒 Entró al step de agregar producto al carrito");
            productListPage.agregarProductosAleatoriosHasta50YUnoExtra();

    }
}
