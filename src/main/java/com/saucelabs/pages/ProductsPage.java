package com.saucelabs.pages;

import com.github.javafaker.Faker;
import com.saucelabs.enitities.Item;
import com.saucelabs.pages.config.Header;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.saucelabs.pages.config.ItemContainer.*;

public class ProductsPage extends Header {

    @FindBy(className = "title")
    WebElement title;

    @FindBy(className = "inventory_item")
    WebElement itemContainer;

    @FindBy(className = "inventory_item")
    List<WebElement> itemContainers;

    public ProductsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WebElement getTitle() {
        return title;
    }

    public Item addRandomItemToCart() {
        waitToBeVisible(itemContainer);

        WebElement targetElement = itemContainers.get(
                new Faker().number().numberBetween(0, itemContainers.size() - 1)
        );

        String itemName = readFrom(targetElement.findElement(ITEM_NAME));
        String itemDesc = readFrom(targetElement.findElement(ITEM_DESC));
        String itemPrice = readFrom(targetElement.findElement(ITEM_PRICE));

        click(targetElement.findElement(ADD_TO_CART_BTN));

        return new Item.ItemBuilder(itemName)
                .desc(itemDesc)
                .price(itemPrice)
                .build();
    }
}
