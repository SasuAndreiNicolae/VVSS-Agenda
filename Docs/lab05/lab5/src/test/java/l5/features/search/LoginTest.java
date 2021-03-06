package l5.features.search;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import l5.steps.serenity.EndUserSteps;
import org.openqa.selenium.WebElement;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/features/search/ticketsLoginData.csv")
public class LoginTest
{
    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email;
        System.out.println("email:"+email);
    }

    public void setPassword(String password) {
        this.password = password;
        System.out.println("password:"+password);
    }

    static
    {
        System.setProperty("webdriver.chrome.driver","C:\\drivers\\chromedriver.exe");
        System.setProperty("webdriver.gecko.driver","C:\\drivers\\geckodriver.exe");
    }
    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps tickets;

    @Test
    public void loginValid()
    {
        tickets.openLoginPage();
        webdriver.findElement(By.id("Email")).sendKeys(email);
        webdriver.findElement(By.id("Password")).sendKeys(password);
        WebElement webElement= webdriver.findElement( By.xpath("//input[@type='submit' and @value='Log in']"));
        webElement.submit();

        try {
            Thread.sleep(1400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webdriver.get("https://www.bilete.ro/");
        try
        {
            WebElement webElement1 = webdriver.findElement(By.id("logoutForm"));
            System.out.println(webElement1.getTagName());
            assert true;
        }
        catch (Throwable t)
        {
            assert false;
            System.out.println(t.getMessage());
        }
    }

    @Test
    public void loginInvalid()
    {
        tickets.openLoginPage();
        webdriver.findElement(By.id("Email")).sendKeys("");
        webdriver.findElement(By.id("Password")).sendKeys("");
        WebElement webElement= webdriver.findElement( By.xpath("//input[@type='submit' and @value='Log in']"));
        webElement.submit();

        try {
            Thread.sleep(1400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webdriver.get("https://www.bilete.ro/");
        try
        {
            webdriver.findElement(By.id("logoutForm"));

            assert false;

        }
        catch (Throwable th)
        {
            assert true;
            System.out.println(th.getMessage());
        }
    }

} 