package com.sources;

public class Xpaths {

	public static String MMT_LOGO = "//a[@class='chMmtLogo']";
	public static String FLIGHT_MAIN_OPTION = "//li[contains(@class,'Flights')]/a";
	public static String SPINNING_LOADING_ICON = "//span[contains(@class,'spinning-loader')]";
	public static String LOGIN_BUTTON = "//li[contains(@class,'User')]/div[1]";
	public static String LOGIN_MODAL = "//div[contains(@class,'autopop')]";

	// xpaths for main page
	public static String LOADING_ICON_HOME = "//p[contains(@class,'ladingState')]";
	public static String TRIP_TYPE = "//div[@class='makeFlex']//span[contains(@class,'tabsCircle')]/parent::li[@data-cy=':tripType:']";
	public static String FROM_INPUT_CLICK = "//input[@id='fromCity']";
	public static String TO_INPUT_CLICK = "//label[@for='toCity']/input";
	public static String DATE_FROM_CLICK = "//label[@for='departure']/span";
	public static String DATE_RETURN_CLICK = "//span[text()='RETURN']";
	public static String TRAVELLERS_CLICK = "//label[@for='travellers']/span";
	public static String INPUT_TEXTBOX = "//div[@role='combobox']/input";
	public static String SUGGESTIONS_COMBOBOX = "//div[@role='combobox']/div//li[Row]";
	public static String DATE_PICKER_VISIBLE_MONTHS = "//div[@class='DayPicker-Caption']";
	public static String DATE_PICKER_NEXT_SET_OF_MONTHS = "//span[@aria-label='Next Month']";
	public static String DATE_PICKER_DATE_TO_SELECT = "//div[@class='DayPicker-Day' and contains(@aria-label,':dateIn-MMM dd yyyy:')]";
	public static String PASSENGER_TYPE_QUANT = "//div[contains(@class,'travellers ')]//li[contains(@data-cy,':passengerType:')][Row]";
	public static String APPLY_TRAVELLERS = "//div[contains(@class,'travellers ')]//button";
	public static String SEARCH_BUTTON = "//a[contains(@class,'SearchBtn')]";
	public static String CANCEL_RETURN_CROSS_BTN = "//span[contains(@class,'returnCross ')]";

	// xpaths for flight option page One Way
	public static String LOADING_ICON_SEARCH1 = "//div[@class='loadingContent']/img";
	public static String LOADING_ICON_SEARCH2 = "//div[@class='fillingLoader']";
	public static String AIRLINE_FILTER = "//div[@class='filtersOuter']/p[text()='Airlines']/following-sibling::div/label//span[contains(text(),':flightName:')]";
	public static String APPLIED_FILTER = "//ul[@class='appliedFilter']";
	public static String FLIGHT_SORTER = "//div[@id='sorting-togglers']//div[contains(@class,':sortWith:_sorter')]/span/span[last()]";
	public static String FLIGHT_LIST_ONEWAY = "//div[contains(@id,'flight_list')]";
	public static String NTH_FLIGHT_VIEW_FARE_BTN = "//div[contains(@id,'flight_list')][:N:]//button[contains(@class,'ViewFareBtn')]";
	public static String NTH_FLIGHT_FARE_TYPE_BOOK_BTN = "//div[contains(@id,'flight_list')][:N:]//div[contains(@class,'viewFareRowWrap')]//p[contains(text(),':fareType:')]/ancestor::div[@class='fareName']/following-sibling::div/button[contains(@id,'bookbutton')]";
	public static String CURRENT_SELECT_DATE = "//div[@class='weeklyFareItems active']//p[1]";
	public static String SELECTED_DEPT_DATE = "//input[@id='departure']";
	public static String DIRECT_BOOK_NOW = "(//button[contains(@id,'bookbutton') and text()='Book Now'])[:N:]";

	// xpaths for flight option page Round Trip
	public static String FLIGHT_LIST_ROUND = "//div[@class='listingCard']";
	public static String DEPART_DATE = "//div[@class='paneView'][1]";
	public static String RETURN_DATE = "//div[@class='paneView'][last()]";
	public static String NTH_FLIGHT_DEPART_CHCK = "(//div[@class='paneView'][1]//div[@class='listingCard']//span[@class='customRadioBtn'])[:N:]";
	public static String NTH_FLIGHT_RETURN_CHCK = "(//div[@class='paneView'][last()]//div[@class='listingCard']//span[@class='customRadioBtn'])[:N:]";
	public static String BOOK_ROUND_TRIP_BTN = "//button[contains(@id,'bookbutton')]";
	public static String DEPART_FLIGHT_FARE_TYPE = "//span[@class='multifareDateTag' and text()='DEPART']/ancestor::div[@class='multifareContentLeft']/following-sibling::div//span[@class='customRadioBtn']/following-sibling::div//p[contains(text(),':fareType:')]";
	public static String RETURN_FLIGHT_FARE_TYPE = "//span[@class='multifareDateTag' and text()='RETURN']/ancestor::div[@class='multifareContentLeft']/following-sibling::div//span[@class='customRadioBtn']/following-sibling::div//p[contains(text(),':fareType:')]";
	public static String CONTINUE_WITH_FARE_BTN = "//button[text()='Continue']";
	public static String CLOSE_FARE_SELECTION_MODAL_BTN = "//span[@class='multifareCross']";

	// xpaths for Review booking page
	public static String PAGE_LOADING_ICON1 = "(//div[contains(@class,'loader')])[last()]";
	public static String PAGE_LOADING_ICON2 = "(//span[contains(@class,'loading')])[last()]";
	public static String DEPART_FLIGHT_DETAILS = "//p[contains(text(),'DEPART')]/ancestor::div[contains(@class,'itnry-flt-header')]/..//div[contains(@class,'itnry-flt-body')]";
	public static String RETURN_FLIGHT_DETAILS = "//p[contains(text(),'RETURN')]/ancestor::div[contains(@class,'itnry-flt-header')]/..//div[contains(@class,'itnry-flt-body')]";
	public static String TRAVLLERS_DETAILS = "//div[contains(@class,'fareSmry-wrap')]";
	public static String CATEGORY_AMT = "//div[contains(@class,'fareSmry-wrap')]";
	public static String EXPAND_ICON_BASE_FARE = "//span[contains(text(),'Base Fare')]/../span[contains(@class,'fareSmry-expand-icon')]";
	public static String EXPAND_ICON_OTHER_SERVICES = "//span[contains(text(),'Other Services')]/../span[contains(@class,'fareSmry-expand-icon')]";
	public static String CHARITY_CHECKBOX = "//input[@id='charity']/following-sibling::label/span[1]";
	public static String CONTINUE_BUTTON = "//button[@id='review-continue']";
	public static String OPT_OUT_OF_INSURANCE = "//span[contains(text(),'No')]/ancestor::label/input";
	public static String OPT_FOR_INSURANCE = "//span[contains(text(),'Yes')]/ancestor::label/input";
	public static String FARE_TYPE_COST = "//span[contains(text(),':FareType:')]/../following-sibling::span";
	public static String TRAVELLER_TYPE_DETAILS = "//span[contains(text(),':travlrType:')]";
	public static String TOTAL_AMOUNT = "//span[contains(text(),'Total Amount')]/../..//following-sibling::span";
}
