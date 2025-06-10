package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Random random = new Random();
        double total = 0.0;

        List<WebElement> productos = driver.findElements(By.xpath("//div[contains(@class, 'Showcase-mk__content')]"));
        int maxIntentos = productos.size();

        Set<Integer> indicesUsados = new HashSet<>();

        while (total <= 60.0 && indicesUsados.size() < maxIntentos) {
            int index = random.nextInt(productos.size());

            if (indicesUsados.contains(index)) continue;

            WebElement producto = productos.get(index);
            indicesUsados.add(index);

            try {
                // Extrae precio localmente (usar .// para mantener contexto)
                WebElement precioElement = producto.findElement(By.xpath(".//div[contains(@class, 'Showcase-mk__unitPrice')]"));
                String precioTexto = precioElement.getText();
                String soloPrecio = precioTexto.replaceAll(".*S/\\s*", "").trim();
                // Usar regex para extraer valor decimal
                Pattern pattern = Pattern.compile("(\\d+[\\.,]?\\d*)");
                Matcher matcher = pattern.matcher(soloPrecio);
                double precio = 0.0;
                if (matcher.find()) {
                    String precioLimpio = matcher.group(1).replace(",", ".");
                    precio = Double.parseDouble(precioLimpio);
                } else {
                    System.out.println("❌ No se pudo extraer precio de: " + precioTexto);
                    continue;
                }

                // Scroll al producto
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", producto);
                Thread.sleep(1000); // esperar al scroll

                // Clic en botón "Agregar"
                WebElement botonAgregar = producto.findElement(By.xpath(".//div[contains(@class,'Showcase__buttonWrapperGrid__add')]"));
                wait.until(ExpectedConditions.elementToBeClickable(botonAgregar)).click();
                Thread.sleep(1000); // espera para simular usuario

                total += precio;
                System.out.println("✅ Producto agregado: S/ " + precio + " | Total acumulado: S/ " + total);

            } catch (Exception e) {
                System.out.println("⚠️ Error con producto " + index + ": " + e.getMessage());
            }
        }

        // ✅ Ingresar a la página de detalle de un producto aleatorio

        List<WebElement> productosClickables = driver.findElements(
                By.xpath("//a[contains(@class, 'Showcase-mk__link')]")
        );

        if (!productosClickables.isEmpty()) {
            int indexDetalle = random.nextInt(productosClickables.size());
            WebElement productoDetalle = productosClickables.get(indexDetalle);

            // Ir al detalle
            String href = productoDetalle.getAttribute("href");
            System.out.println("🔗 Ingresando al detalle del producto: " + href);
            driver.get(href);

            // Agregar desde la vista de detalle
            WebElement btnAgregarFinal = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//a[contains(@class, 'ProductCard__buttons__button') and contains(text(),'Agregar')])[1]")));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btnAgregarFinal);
            btnAgregarFinal.click();
            System.out.println("🎯 Producto adicional agregado desde vista de detalle.");
        } else {
            System.out.println("⚠️ No se encontró producto para ingresar al detalle.");
        }
    }


}
