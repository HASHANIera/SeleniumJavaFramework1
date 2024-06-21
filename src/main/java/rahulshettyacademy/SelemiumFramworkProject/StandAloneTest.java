package rahulshettyacademy.SelemiumFramworkProject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		
		String ProductName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client");
        
        //Login
        driver.findElement(By.id("userEmail")).sendKeys("anshika@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
        driver.findElement(By.id("login")).click();
        
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        
        WebElement prod = products.stream().filter(product 
        		->product.findElement(By.cssSelector("b")).getText().equals(ProductName) ).findFirst().orElse(null);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        //ng-animating 
       // wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        prod.findElement(By.cssSelector("button[class='btn w-10 rounded']")).click();
        
      
        String overlayXPath = "//div[contains(@class, 'ngx-spinner-overlay')]";
        String buttonXPath = "/html/body/app-root/app-dashboard/app-sidebar/nav/ul/li[4]/button";
        
         // Wait for the overlay to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(overlayXPath)));

        // Wait for the button to be clickable and then click it
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonXPath)));
        element.click();
        //checking if there product in my cart
        List<WebElement> cartproducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartproducts.stream().anyMatch(product -> product.getText().equals(ProductName));
        Assert.assertTrue(match);
        
        //Click Checkout button
        driver.findElement(By.xpath("//ul/li[3]/button[@class='btn btn-primary']")).click();
        
        //Select country
        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country'] ")),"India").build().perform();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results ")));
        
        driver.findElement(By.xpath("//button[@type='button'][2]")).click();
        
       // Wait for the element to be visible
        WebElement buttonelement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".btnn")));

        // Scroll the element into view using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonelement);

        // Wait for the element to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(buttonelement));

        // Click the element using JavaScript as a fallback
        try {
        	buttonelement.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buttonelement);
        }
    
        
        //Check confirm message 
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        
      //  driver.close();
        
        
        
        
	}

}
