package example;

import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import org.testng.Assert;
import org.testng.Reporter;

public class LiverpoolTest {
public static WebDriver driver;
public static List<WebElement> serchedArticles;

//Launching the Web Page
@Test (priority=0,groups = {"TC1","TC2"})  
  public void launchLiverpool() throws InterruptedException
  {
	String url="https://www.liverpool.com.mx/tienda/home";
	System.setProperty("webdriver.chrome.driver","E:\\chromedriver.exe");
	driver=new ChromeDriver();
	driver.get(url);
	Reporter.log("Browser Launch");
	driver.manage().window().maximize();
	Thread.sleep(1000);
  }
  
  //-------Verifying Lending page
  
  @Test (groups = {"TC1"})  
  public void verifyHomepage() throws InterruptedException
  {
	 String expected="Liverpool es parte de Mi vida";
	 String actual =driver.getTitle();
	 Assert.assertEquals(actual, expected, "Homepage title not matching");
	 System.out.println("--------Homepage Title verified-----");
	 
		Thread.sleep(1000);
  }
  
  //---------Search Article/product to buy
  
  @Test   (groups = {"TC2"},dependsOnMethods = { "launchLiverpool"}) 
  
  @Parameters ({"article"})
 
  	public void searcharticle(String article) throws InterruptedException
  		{
  		WebElement searchBox=driver.findElement(By.id("mainSearchbar"));
  		searchBox.sendKeys(article);
  		searchBox.sendKeys(Keys.ENTER);
  		System.out.println("Product searched");
  		Thread.sleep(1000);
  		}
  
  //---------Get various products based on searching matching 
  
  @Test   (groups = {"TC2"},dependsOnMethods = { "searcharticle"}) 
  public void viewArticle() throws InterruptedException
  {
	  	serchedArticles=driver.findElements(By.cssSelector("[id^=img_]"));
			for(WebElement a:serchedArticles)
			{
				Assert.assertEquals(a.isDisplayed(), true, "Element is not displayed");
				System.out.println("--------Searched product/article displayed-----");
				System.out.println(a.getAttribute("id")+"----"+a.getAttribute("alt") );
			}
		System.out.println("Number of product/article dislayed on page one : "+serchedArticles.size());		
		Thread.sleep(1000);	
  }
  
  //----------Select article to view specification
  
  @Test (groups = {"TC2"},dependsOnMethods= {"viewArticle"})
  @Parameters ({"productnum"})
		public void selectArticle(int x) throws InterruptedException
		{
		driver.findElement(By.cssSelector("[id^=img_"+(x-1)+"]")).click();
		Thread.sleep(10000);		
		}
  @BeforeTest (groups = {"TC1","TC2"})
  public void beforeSuite() {
    System.out.println("-----Starting Liverpool Shop----");
  }
  @AfterTest (groups = {"TC1","TC2"})
  public void tearDown() throws InterruptedException
  {
	  driver.quit();
	  Thread.sleep(5000);
	   System.out.println("---Browser is closed-----Closing Liverpool----");
	      
  }
}