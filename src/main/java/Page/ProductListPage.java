package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    public void agregarProductosAleatoriosHasta50YUnoExtra() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        Random random = new Random();
        double total = 0.0;

        List<WebElement> productos = driver.findElements(By.xpath("//div[contains(@class, 'Showcase-mk__details__text')]"));
        int maxIntentos = productos.size();

        Set<Integer> indicesUsados = new HashSet<>();

        // Agregar productos aleatorios hasta superar S/ 50
        while (total <= 50.0 && indicesUsados.size() < maxIntentos) {
            int index = random.nextInt(productos.size());

            if (indicesUsados.contains(index)) continue;

            WebElement producto = productos.get(index);
            indicesUsados.add(index);

            try {
                WebElement precioElement = producto.findElement(By.xpath("//div[contains(@class, 'Showcase-mk__unitPrice')]"));
                String precioTexto = precioElement.getText().replace("S/ ", "").replace(",", ".").trim();
                double precio = Double.parseDouble(precioTexto);

                WebElement botonAgregar = producto.findElement(By.xpath("//div[contains(@class, 'Showcase__buttonWrapperGrid__add')]"));
                botonAgregar.click();
                Thread.sleep(2000); // O usa un wait explícito

                total += precio;
                System.out.println("Producto aleatorio agregado: S/ " + precio + " | Total acumulado: S/ " + total);
            } catch (Exception e) {
                System.out.println("Error con producto " + index + ": " + e.getMessage());
            }
        }

        // Escoger otro producto aleatorio y entrar a su detalle
        List<WebElement> productosClickables = driver.findElements(By.xpath("//div[contains(@class, 'Showcase-mk__details__text')]//a[@href]"));
        if (!productosClickables.isEmpty()) {
            int indexDetalle = random.nextInt(productosClickables.size());
            WebElement productoDetalle = productosClickables.get(indexDetalle);

            String href = productoDetalle.getAttribute("href");
            System.out.println("Ingresando al producto: " + href);
            driver.get(href);

            // Agregar desde el detalle del producto
            WebElement btnAgregarFinal = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//a[contains(@class, 'ProductCard__buttons__button') and contains(text(),'Agregar')])[1]")));
            btnAgregarFinal.click();
            System.out.println("Producto adicional agregado desde detalle.");
        } else {
            System.out.println("No se encontró producto para ingresar al detalle.");
        }
    }


}
