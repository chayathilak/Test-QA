package com.wiley.selenium.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Strings;
import com.wiley.selenium.helpers.TestBase;

public class MainPage extends TestBase {

	public MainPage(WebDriver driver) {
		super(driver);
	}

	// Undetected location pop-up (to change location) : click confirm button
	public static By btn_confirm = By.xpath("//*[@id=\"country-location-form\"]/div[3]/button[2]");

	// Nav bar Who we serve
	public static By nav_whoweserve = By.linkText("WHO WE SERVE");

	// Items under Who we server
	public static By list_whoweserve = By.xpath("//*[@id=\"Level1NavNode1\"]/ul/li");

	// subjects
	public static By nav_subjects = By.linkText("SUBJECTS");

	// Sub menu of subject : Education
	public static By sub_menu_education = By.linkText("Education");

	// Items under Education-sub menu
	public static By list_Educationsubmenu = By.xpath("//*[@id=\"Level1NavNode2\"]/ul/li[9]/div/ul/ul/li");

	// Search tab
	public static By txt_search = By.id("js-site-search-input");

	// Search-List for suggestions
	public static By search_list_for_suggestions = By.xpath("//*[@id=\"ui-id-2\"]/section[1]/div/div");

	// Search-List for Products
	public static By search_list_for_Products = By.xpath("//*[@id=\"ui-id-2\"]/section[2]/div/div");

	// Search icon
	public static By btn_searchicon = By
			.xpath("//*[@id=\"main-header-container\"]/div/div[2]/div/form/div/span/button");

	// Navigate to main page & confirm 'Undetected location pop-up
	public void openPage() {
		Open("wileyMainPage");
		Click(btn_confirm);
	}

	// Navigate to main page, confirm 'Undetected location pop-up' & mouse hover Who We Serve
	public void mouseHoverWhoWeServe() {
		Open("wileyMainPage");
		Click(btn_confirm);
		onMouseHover(nav_whoweserve);
	}

	// Check whether 'Who We Serve contains' correct number of elements
	public boolean isWhoWeServerNavBarContainsCorrectNumberOfElements(int elements) {
		List<WebElement> list = getAllListItems(list_whoweserve);
		return list.size() == elements;
	}

	// Check whether 'Who We Serve contains' correct sub elements
	public boolean isWhoWeServeListContainsCorrectSubElements(ArrayList<String> existingNameList) {
		boolean isPassed = true;
		List<WebElement> webElementList = getAllListItems(list_whoweserve);

		if (webElementList.size() == 0) {
			System.out.println("incorrect number of sub elements");
			isPassed = false;
		}

		for (WebElement element : webElementList) {
			if (element != null && !Strings.isNullOrEmpty(element.getText())) {
				if (!existingNameList.contains(element.getText())) {
					System.out.println("Missing element in the actual list:" + element.getText());
					isPassed = false;
				}
			}
		}
		return isPassed;
	}

	// Mouse hover 'Subject' & then 'Education
	public void navigateToEducationSubMenu() {
		onMouseHover(nav_subjects);
		onMouseHover(sub_menu_education);
	}

	// Check whether 'Education' label exist
	public boolean isEducationLabelExists(String expectedString) {
		String actualString = getText(sub_menu_education);
		boolean result = actualString.contains(expectedString);
		return result;
	}

	// Validate whether 'Education' contains correct element list
	public boolean isEducationContainsCorrectSubElements(ArrayList<String> existingNameList) {
		boolean isPassed = true;
		List<WebElement> webElementList = getAllListItems(list_Educationsubmenu);

		if (webElementList.size() == 0) {
			System.out.println("incorrect number of sub elements");
			isPassed = false;
		}

		ArrayList<String> subElementList = new ArrayList<String>();
		for (int i = 1; i <= webElementList.size(); i++) {
			String innerHtmlOfEducationalSubElements = getInnerHtmlForStaleElements(
					By.xpath("//*[@id=\"Level1NavNode2\"]/ul/li[9]/div/ul/ul/li[" + i + "]/a"));
			String innerHtmlText = innerHtmlOfEducationalSubElements.trim();
			subElementList.add(innerHtmlText);
			if (!existingNameList.contains(innerHtmlText)) {
				isPassed = false;
				System.out.println(innerHtmlText + " is incorrect");
			}
		}

		for (int i = 0; i < existingNameList.size(); i++) {
			if (!subElementList.contains(existingNameList.get(i))) {
				System.out.println(existingNameList.get(i) + " missed");
				isPassed = false;
			}
		}
		return isPassed;
	}

	// type on search tab
	public void typeOnSearch(String keyword) {
		Type(txt_search, keyword);
	}

	// Validate whether search functionality (without search button) provides correct elementList
	public boolean isSeachSubSectionsContainsCorrectSubElements(String word) {
		List<WebElement> webElementListForSuggestions = getAllListItems(search_list_for_suggestions);
		List<WebElement> webElementListForProducts = getAllListItems(search_list_for_Products);

		int searchResultCount = webElementListForProducts.size() + webElementListForSuggestions.size();
		if (searchResultCount == 0) {
			return false;
		}
		for (int i = 5; i < searchResultCount + 5; i++) {
			String innerHtmlOfSearchResult = getInnerHtmlForStaleElements(
					By.xpath("//*[@id=\"ui-id-" + i + "\"]/span"));
			if (!innerHtmlOfSearchResult.equalsIgnoreCase(word)) {
				return false;
			}
		}

		return true;
	}

	// Click search icon
	public void clickSearchIcon() {
		Click(btn_searchicon);
	}

}
