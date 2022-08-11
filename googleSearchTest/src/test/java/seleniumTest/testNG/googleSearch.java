package seleniumTest.testNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;

import static org.testng.Assert.assertEquals;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class googleSearch {
	WebDriver driver;
	
	/**
	* Returns 2d String list contains all data in the data file. 
	* data are {Label,Value) pairs in first two columns in the sheet.
	* data used for select browser to execute, to provide URLs, Xpath for locators.
	* @param  filename  the file name of data file 
	* @param  sheetName the sheet name inside the data file
	* @return  2d array String includes all the data in data file
	*/
  	public String[][] getExcelData(String fileName, String sheetName) throws IOException{
        	  	  	
  	   	FileInputStream fis = new FileInputStream(fileName);
  	   	XSSFWorkbook wb = new XSSFWorkbook(fis);
  	   	XSSFSheet sh = wb.getSheet(sheetName);
  	   	String data[][] = new String[sh.getLastRowNum()][sh.getRow(0).getLastCellNum()];  	   	
  	   	
  	   	for(int i =0; i<sh.getPhysicalNumberOfRows()-1;i++){
  		     for(int j=0;j<sh.getRow(0).getLastCellNum();j++){
  		    	 data [i][j]=sh.getRow(i+1).getCell(j).toString();
  	   	 	   }
  	   	}
        	return data;
  	}
  	
	/**
	* Returns a String that match the search input field.
	* It search in the sheet for the label and returns the value found for it
	* if not found it will return empty string
	* @param  label label need to search with to get its value
	* @return  String array that has value related to the label in search
	*/
  	public String readData(String label) throws IOException
  	{
  		String [][] Data = getExcelData("TestData.xlsx","Sheet1");
  		for(int i=0; i<Data.length; i++)
		{
			if(Data[i][0].equals(label)) {
				return Data[i][1];
			}
		}
		return "";
  	}

  	@BeforeClass
	public void setup() throws IOException
	{
  		String Browser = readData("ExecutionBrowser"); // Chrome/ FireFox/ Edge
  		if(Browser.equals("Chrome"))
  		{
  			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
  			ChromeOptions options = new ChromeOptions();
  			driver = new ChromeDriver(options);
  		}
  		else if(Browser.equals("Edge"))
  		{
  	  		System.setProperty("webdriver.edge.driver", "D:\\EdgeDriver\\msedgedriver.exe");
  			driver = new EdgeDriver();
  			
  		}
  		else if (Browser.equals("FireFox"))
  		{
	  		System.setProperty("webdriver.gecko.driver","D:\\FireFoxDriver\\geckodriver.exe");
	  		driver = new FirefoxDriver();
  		}
	    driver.manage().window().maximize();
	    driver.get(readData("URL"));
	    
	}
  	
	@AfterClass
	public void close()
	{
		driver.quit();
	}
	
	@Test
	public void TestMethod() throws IOException {
		
		Reporter.log("Opening Google");
		String Title= driver.getTitle();
		assertEquals(Title, readData("PageTitle"),"Title is not correct.");

		Reporter.log("Find search box");
		WebElement searchBox = driver.findElement(By.name(readData("SearchBoxField")));
		
		Reporter.log("Enter Vodafone in search box");
		searchBox.sendKeys(readData("SeachData"));
		
		Reporter.log("Search for Vodafone");
		searchBox.sendKeys(Keys.ENTER);
		
		Reporter.log("Navigate to page 2 ");
		driver.findElement(By.xpath(readData("Page2"))).click();
		
		Reporter.log("Locate all titles in Page 3 ");
		List <WebElement> results = driver.findElements(By.xpath(readData("SearchResultLinks")));
		
		Reporter.log("Count page 2 Number of results");
		int pg2LinksOnlyNumber= results.size()-1; // excluding last data returned as it is for (Related searches) title not a real search result
		
		Reporter.log("Navigate to page 3 ");
		driver.findElement(By.xpath(readData("Page3"))).click();
		
		Reporter.log("Locate all titles in Page 3 ");
		List <WebElement> results3 = driver.findElements(By.xpath(readData("SearchResultLinks")));

		Reporter.log("Count page 3 Number of results");
		int pg3LinksOnlyNumber= results3.size()-1; // excluding last data returned as it is for (Related searches) title not a real search result
		
		Reporter.log("validate page 2 and page 3 search number count ");
		assertEquals(pg2LinksOnlyNumber-1,pg3LinksOnlyNumber-1,"Total search in page 2 and 3 are not the same.");
	}

}
