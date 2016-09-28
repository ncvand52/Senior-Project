package seniorprojectparse;

//Using jsoup-1.9.2

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Using javax.json-1.0.4.jar

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
public class parser {
	public static void main(String[] args) throws IOException
	{	
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please enter the Walmart product url to parse: ");
		String website = scan.next();
		
		// Using JSoup, Parses the HTML to retrieve the JSON data
		
		Document doc = Jsoup.connect(website).maxBodySize(0).get();
		//String html = Jsoup.connect("https://www.walmart.com/ip/Refurbished-Fitbit-Charge-HR-Wireless-Activity-Wristband-Plum-Large/107277236").maxBodySize(0).get().html();
		
		Elements element = doc.select("script");	//script id="tb-djs-wml-base" type="application/json"
	    element = doc.getElementsByAttributeValue("type", "application/json");		

	    Element e = doc.getElementById("tb-djs-wml-base");
	    String jsonFromHTML = e.html();
	    
	    String ratings = doc.select("span[class=visuallyhidden]").text();
	    //System.out.println(ratings);
	    //System.out.println(jsonFromHTML);
	    
// Using Java JSON parser, Attempts (unsuccessfully) to parse JSON to retrieve attributes like price
	    
	 //Using javax.json-1.0.4.jar
	    
	    //JsonReader reader = Json.createReader(new StringReader(jsonFromHTML));
	    //JsonObject jsonInfo = reader.readObject();
	    
	    //reader.close();

	    //JsonObject jsonInfo = new JsonObject(jsonFromHTML);
	    //boolean priceIncluded = jsonInfo.getBoolean("adContextJSON");
	    //System.out.println("AdCOntext in there? " + priceIncluded);
	    //JsonString data = jsonInfo.getJsonString("adContextJSON");
	    //JsonString adContextJSON = .getString("adContextJSON");
	    //System.out.println(data);
	    
// Parses the ratings string for number of stars
	    
	    int starStringPosition = 0;
	    int starNumberPosition = 0;
	    String findStarString = "stars";
	    
	    starStringPosition = ratings.toLowerCase().indexOf(findStarString.toLowerCase());
		
	    starNumberPosition = starStringPosition - 4;
	    String ratingNumber=""; // rating decimal
	    char currentChar= ' ';
	    char nextChar = ' ';
	    
	  //Retrieves the ratings, stops before 's' (in stars) which ends the ratings number in the string
	  		for(int i=0; i<10 && nextChar!='s'; i++)
	  		{
	  			 currentChar = ratings.charAt(starNumberPosition + i);
	  			 ratingNumber += currentChar;
	  			 nextChar = ratings.charAt(starNumberPosition + i+1);
	  		}
	    
// Parses the JSON string for price using string manipulation		
		
		int priceStringPosition = 0;
		int priceNumberLocation = 0;
		String findPriceString = "price";
		
		priceStringPosition = jsonFromHTML.toLowerCase().indexOf(findPriceString.toLowerCase());
		
		priceNumberLocation = priceStringPosition+7; // walmart json structure has actual price start 7 spaces after the index of string price
		String number=""; // price of product
		char current=' ';
		char next = ' ';
		
		//Retrieves the price, stops before ',' which ends the price digits in the string
		for(int i=0; i<10 && next!=','; i++)
		{
			 current = jsonFromHTML.charAt(priceNumberLocation + i);
			 number += current;
			 next = jsonFromHTML.charAt(priceNumberLocation + i+1);
		}
		
// Parses the JSON string for Manufacturer/brand		

		int brandStringPosition = 0;
		int brandActualPosition = 0;
		String findBrandString = "brand";
	
		brandActualPosition = jsonFromHTML.toLowerCase().indexOf(findBrandString.toLowerCase()); // find "brand" string
		brandActualPosition = brandActualPosition+8; // walmart json structure has actual brand start 8 spaces after the index of string manufacturer
		
		String brand=""; // name of brand to return
		char brandCurrent=' ';
		char brandNext = ' ';
		
		//Retrieves the brand, stops before ',' which ends the brand chars in the string
		for(int i=0; i<50 && brandNext!='"'; i++)
		{
			 brandCurrent = jsonFromHTML.charAt(brandActualPosition + i);
			 brand += brandCurrent;
			 brandNext = jsonFromHTML.charAt(brandActualPosition + i+1);
		}		
		
		String title = doc.title();
		//ratingNumber = ratingNumber + "stars";
		
		// Prints the Page title, Price, Manufaturer/Brand, and Ratings		
		System.out.println("Title: " + title);
		System.out.println("Manufacturer/Brand: " + brand);
		System.out.println("Price: " + number);    
		System.out.println("Rating: " + ratingNumber);
	}
}