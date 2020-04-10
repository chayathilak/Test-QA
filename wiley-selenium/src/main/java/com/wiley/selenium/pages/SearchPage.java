package com.wiley.selenium.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.wiley.selenium.helpers.TestBase;

public class SearchPage extends TestBase {

	public SearchPage(WebDriver driver) {
		super(driver);
	}
	//search list for products in search page
	public static By search_list_for_products = By.xpath("//*[@id=\"search-result-page-row\"]/div[3]/div/div[3]/div/div/div/div[3]/section");

	// Validate that search navigate to the correct search page
	public boolean isSeachNavigatesToTheSearchPage(String searchPageUrl) {
		String currentUrl = getCurrentUrl();
		return currentUrl.equals(searchPageUrl);
	}

	// Validate search functionality (with search button) provides correct result count
	public boolean isSeachPageContainsCorrectSearchResultCount(String count) {
		List<WebElement> webElementListForSuggestions = getAllListItems(search_list_for_products);

		int searchcout = Integer.parseInt(count);
		int searchResultCount = webElementListForSuggestions.size();
		if (searchResultCount != searchcout) {
			return false;
		}
		return true;
	}

	// Validate search functionality (with search button) provides correct elementList
	public boolean isSearchResultsContainsSearchWord(String word) {
		List<WebElement> webElementListForSuggestions = getAllListItems(search_list_for_products);

		int searchResultCount = webElementListForSuggestions.size();
		if (searchResultCount == 0) {
			return false;
		}
		for (int i = 1; i <= searchResultCount; i++) {
			String xpathForSpan = "//*[@id=\"search-result-page-row\"]/div[3]/div/div[3]/div/div/div/div[3]/section["
					+ i + "]/div[2]/h3/a/span";
			String innerHtmlOfSearchResult = getInnerHtml(By.xpath(xpathForSpan));
			if (!innerHtmlOfSearchResult.equalsIgnoreCase(word)) {
				return false;
			}
		}

		return true;
	}
	
}
