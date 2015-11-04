package com.practice.project;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FirstTest {
	
	WebDriver driver = null;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@Parameters({"platform", "browser", "version", "url"})
	@BeforeTest(alwaysRun=true)
	public void setup(String platform, String browser, String version, String url) throws MalformedURLException {
		
		DesiredCapabilities caps = new DesiredCapabilities();
		
		//Platform
		if(platform.equalsIgnoreCase("Linux")) {
			caps.setPlatform(org.openqa.selenium.Platform.LINUX);
		}else if(platform.equalsIgnoreCase("Windows")){
			caps.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		}
					
		//Browser
		if(browser.equalsIgnoreCase("Chrome")) {
			caps = DesiredCapabilities.chrome();
		}
					
		//Version
		//caps.setVersion(version);
		
		driver = new RemoteWebDriver(new URL("http://ec2-54-173-219-98.compute-1.amazonaws.com:4444/wd/hub"), caps);
		
		driver.get(url);
		
	}
	
	@Test(description="Test Sample Form")
	public void testSampleForm() throws InterruptedException {
		
		WebElement name = driver.findElement(By.id("entry_785445797"));
		name.sendKeys("Halley");
		
		try{
			
			WebElement inputName = driver.findElement(By.id("entry_785445797"));
			Assert.assertEquals(inputName.getAttribute("value"),"Halley");
			
		}catch(Error e){
			verificationErrors.append(e.toString());
		}
	}
	
	@AfterTest
	public void afterTest(){
		
		driver.quit();
		
		String verificationErrorString = verificationErrors.toString();
		
		if(!"".equals(verificationErrorString)){
			Assert.fail(verificationErrorString);
		}
	}
}
