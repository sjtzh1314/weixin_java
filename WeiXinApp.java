package com.example;

//import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.NoSuchElementException;

//java-client1.2.1版本用appiumDriver 2.2.0用AndroidDriver，以上版本会出错
public class WeiXinApp {
	
    public static void main(String[] args) throws MalformedURLException, InterruptedException{
       
    	AndroidDriver driver = init();
    	WebDriverWait wait = new WebDriverWait(driver, 300);
    	login("13691467901","sunjingtao1314",wait);
    	enter(wait);
    	crawl(wait,driver);
    }
    /**
     * 初始化
     * @return
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    private static  AndroidDriver init() throws MalformedURLException, InterruptedException{
    	//驱动配置初始化
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //设备名称
        capabilities.setCapability("deviceName", "Coolpad8675-0x03a2b1f1");
//        capabilities.setCapability("deviceName", "emulator-5554");
//        capabilities.setCapability("deviceName", "127.0.0.1:62001");
        capabilities.setCapability("automationName", "Appium");
        //平台
        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("platformVersion", "6.0");
        //app包名
        capabilities.setCapability("appPackage", "com.tencent.mm");
        //入口类名
        capabilities.setCapability("appActivity", ".ui.LauncherUI");
      //Appium地址
        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		return driver;
    }
    /**
     * 登录微信
     * @param username
     * @param password
     */
    private static void login(String username,String password,WebDriverWait wait){
    	
    	// 登录按钮
    	WebElement login = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tencent.mm:id/d75")));
        login.click();
        // 手机输入
        WebElement phone = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tencent.mm:id/hz")));
        phone.sendKeys(username);
        // 下一步
        WebElement next = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.tencent.mm:id/alr")));
        next.click();
        // 密码
        WebElement password1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@resource-id='com.tencent.mm:id/hz'][1]")));
        password1.sendKeys(password);
        // 提交
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.tencent.mm:id/alr")));
        submit.click();
    }
    /**
     * 进入朋友圈
     */
    private static void enter(WebDriverWait wait){
    	// 选项卡 发现
    	WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@resource-id='com.tencent.mm:id/b0w'][3]")));
        tab.click();
        // 朋友圈
        WebElement moments = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.tencent.mm:id/aab")));
        moments.click();
    }
    /**
     * 爬取
     */
    private static void crawl(WebDriverWait wait,AndroidDriver driver){
    	while(true){
            //当前页面显示的所有状态
    		List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@resource-id='com.tencent.mm:id/dkb']//android.widget.FrameLayout")));
             // 上滑
            driver.swipe(300, 300, 300, 300, 700);
            // 遍历每条状态
            for(WebElement item:items){
                try{
                    // 昵称
                    String nickname = item.findElement(By.id("com.tencent.mm:id/as6")).getText();
                    // 正文
                    String content = item.findElement(By.id("com.tencent.mm:id/ib")).getText();
                    // 日期
                    String date = item.findElement(By.id("com.tencent.mm:id/dki")).getText();
                    
                    // 处理日期
//                    date = self.processor.date(date)
                    System.out.println(nickname+","+content+","+date);
//                    data = {
//                        'nickname': nickname,
//                        'content': content,
//                        'date': date,
//                    }
                    // 插入MongoDB
//                    self.collection.update({'nickname': nickname, 'content': content}, {'$set': data}, True)
//                    sleep(SCROLL_SLEEP_TIME)
                }catch(Exception NoSuchElementException){
                	System.out.println("未找到该元素");
                }
                finally{
                	driver.quit();
                }
                }
    }
    }
}