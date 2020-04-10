package com.wiley.selenium.testcases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import com.wiley.selenium.pages.MainPage;
import com.wiley.selenium.pages.SearchPage;
import com.wiley.selenium.utils.ReadFromExcel;

import junit.framework.Assert;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestCase_1 {

	WebDriver driver;
	MainPage mainPage;
	SearchPage searchPage;

	@BeforeMethod
	public void beforeMethod(Method method) {
		File file = new File("lib" + File.separator + "chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		System.out.println(("Test name:" + method.getName()));
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		mainPage = new MainPage(driver);
		searchPage = new SearchPage(driver);
	}

	//Part 01: Test 1
	@Test(dataProvider = "dpWhoWeServeElementList",description="Validate whether 'Who We Serve' contains correct number of elements",priority = 1)
	public void testWhoWeServeNoOfElements(String elements)throws InterruptedException {
		//Navigate to main page, confirm 'Undetected location pop-up &  mouse hover 'Who We Serve'
		mainPage.mouseHoverWhoWeServe();
		//Check whether Who We Serve contains correct number of elements
		int numberOfElements = Integer.parseInt(elements);
		System.out.println("Number of elements :-" + elements);
		Assert.assertTrue(mainPage.isWhoWeServerNavBarContainsCorrectNumberOfElements(numberOfElements));
	}

	//Part 01: Test 1.1
	@Test(description="Validate whether 'Who We Serve' contains correct element list",priority = 2)
	public void testWhoWeServeContainsCorrectElementList()throws InterruptedException {
		//Navigate to main page, confirm 'Undetected location pop-up &  mouse hover 'Who We Serve'
		mainPage.mouseHoverWhoWeServe();
		ArrayList<String> list = new ArrayList<String>();
		list.add("Students");
		list.add("Instructors");
		list.add("Book Authors");
		list.add("Professionals");
		list.add("Researchers");
		list.add("Institutions");
		list.add("Librarians");
		list.add("Corporations");
		list.add("Societies"); 
		list.add("Journal Editors");
		list.add("Government");
		//Validate whether 'Who We Serve' contains correct elements list 
		Assert.assertTrue(mainPage.isWhoWeServeListContainsCorrectSubElements(list));
	}

	//Part 02
	@Test(dataProvider = "dpTestSearchWithoutSearchButtonElementList",description="Validate whether Search functionality (without using search button) provides correct element list",priority = 3)
	public void testSearchWithoutSearchButtonElementList(String keyword){
		//Navigate to main page & confirm 'Undetected location pop-up
		mainPage.openPage();
		//type on search tab
		mainPage.typeOnSearch(keyword);
		//Validate whether search functionality (without search button) provides correct elementList
		Assert.assertTrue(mainPage.isSeachSubSectionsContainsCorrectSubElements(keyword));		
	}

	//Part 04: Test 1
	@Test(description="Validate whether'Education' exist",priority = 4)
	public void testWhetherEducationLabelExist(){
		//Navigate to main page & confirm Undetected location pop-up
		mainPage.openPage();
		//Mouse hover 'Subject' & then 'Education 
		mainPage.navigateToEducationSubMenu();
		//Check whether 'Education' exist 
		Assert.assertTrue(mainPage.isEducationLabelExists("Education"));

	}

	//Part 04: Test 1.1
	@Test(description="Validate whether 'Education' contains correct element list",priority = 5)
	public void testEducationContainsCorrectElementList() throws InterruptedException {
		//Navigate to main page & confirm 'Undetected location pop-up
		mainPage.openPage();
		//Mouse hover 'Subject' & then 'Education 
		mainPage.navigateToEducationSubMenu();
		ArrayList<String> list = new ArrayList<String>();
		list.add("Information &amp; Library Science");
		list.add("Education &amp; Public Policy");
		list.add("K-12 General");
		list.add("Higher Education General");
		list.add("Vocational Technology");
		list.add("Conflict Resolution &amp; Mediation (School settings)");
		list.add("Curriculum Tools - General");
		list.add("Special Educational Needs");
		list.add("Theory of Education"); 
		list.add("Education Special Topics");
		list.add("Educational Research &amp; Statistics");
		list.add("Literacy &amp; Reading");
		list.add("Classroom Management");
		//Validate whether 'Education' contains correct element list
		AssertJUnit.assertTrue(mainPage.isEducationContainsCorrectSubElements(list));	
	}
	
	//Part 03: Test 1
	@Test(dataProvider = "dpTestSearchWithSearchIconClick",description="Validate whether search button provides correct element list",priority = 6)
	public void testSearchWithSearchIconClick(String keyword, String count,String word) throws InterruptedException {
		//Navigate to main page & confirm 'Undetected location pop-up 
		mainPage.openPage();
		//type on search tab
		mainPage.typeOnSearch(keyword);
		//Click search icon
		mainPage.clickSearchIcon();
		//Validate search functionality (with search button) provides correct result count
		Assert.assertTrue(searchPage.isSeachPageContainsCorrectSearchResultCount(count));
		//Validate search functionality (with search button) provides correct elementList
		Assert.assertTrue(searchPage.isSearchResultsContainsSearchWord(word));
	}
	
	//Part 03: Test 1.1
	@Test(dataProvider = "dpTestSearchButtonNavigatesToSearchPage",description="validate click search button navigates to search page",priority = 7)
	public void testSearchButtonNavigatesToSearchPage(String keyword,String searchPageUrl) throws InterruptedException {
		//Navigate to main page & confirm 'Undetected location pop-up 
		mainPage.openPage();
		//type on search tab
		mainPage.typeOnSearch(keyword);
		//Click search icon
		mainPage.clickSearchIcon();
		//Validate that search navigate to the correct search page
		Assert.assertTrue(searchPage.isSeachNavigatesToTheSearchPage(searchPageUrl));
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@DataProvider
	public Object[][]  dpWhoWeServeElementList() {
		ReadFromExcel excelReader = new ReadFromExcel("data"+File.separator+"WileyTestData.xlsx", "Sheet1");
		Object[][] data =  excelReader.getSheetData();
		return data;
	}

	@Test
	@DataProvider
	public Object[][]  dpTestSearchWithoutSearchButtonElementList() {
		ReadFromExcel excelReader = new ReadFromExcel("data"+File.separator+"WileyTestData.xlsx", "Sheet2");
		Object[][] data =  excelReader.getSheetData();
		return data;
	}
	//
	@Test
	@DataProvider
	public Object[][]  dpTestSearchWithSearchIconClick() {
		ReadFromExcel excelReader = new ReadFromExcel("data"+File.separator+"WileyTestData.xlsx", "Sheet3");
		Object[][] data =  excelReader.getSheetData();
		return data;
	}

	@Test
	@DataProvider
	public Object[][]  dpTestSearchButtonNavigatesToSearchPage() {
		ReadFromExcel excelReader = new ReadFromExcel("data"+File.separator+"WileyTestData.xlsx", "Sheet4");
		Object[][] data =  excelReader.getSheetData();
		return data;
	}
}