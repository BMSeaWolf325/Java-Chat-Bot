// First API to get weather information
// Weather API Key = 846087dec6a7cef11c885198a6999774
// For city api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
// For zip api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}&appid={API key}
// Reconfigured code to ask input from webchat

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.io.BufferedReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
// import java.util.Scanner;
// import org.jibble.pircbot.PircBot;

public class Weather
{
   public static String WeatherAPI(int choice, String message) throws IOException
   {
      int menu_choice = 0;
      menu_choice = choice;
      String urlStr = "", city = "", zip = "";
      if(menu_choice == 1)
      {
         //System.out.println("Enter city name: ");
         //input.nextLine();
         city = message;
         urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                 "&appid=846087dec6a7cef11c885198a6999774&units=imperial";
      }
      else if(menu_choice == 2)
      {
         //System.out.println("Enter zip code: ");
         //input.nextLine();
         zip = message;
         urlStr = "https://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us" +
                 "&appid=846087dec6a7cef11c885198a6999774&units=imperial";
      }

      URL url = new URL(urlStr);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
      httpURLConnection.setRequestProperty("Content-Type", "application/json");
      BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

      String resultStream, result = "";
      while ((resultStream = in.readLine()) != null)
         result += resultStream;
      in.close();

      if(menu_choice == 1)
      {
         return("The temperature in " + city + " is " + parse(result) + " degrees Fahrenheit.");
      }
      else if(menu_choice == 2)
      {
         return("The temperature in " + zip + " is " + parse(result) + " degrees Fahrenheit.");
      }
      else
      {
         return("0");
      }
   }

   public static String parse(String jsonLine) {
      JsonElement jelement = new JsonParser().parse(jsonLine);
      JsonObject jobject = jelement.getAsJsonObject();
      jobject = jobject.getAsJsonObject("main");
      String result = jobject.get("temp").getAsString();
      return result;
   }
}
