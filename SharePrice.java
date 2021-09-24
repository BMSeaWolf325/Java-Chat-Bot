// First API to get open, high, low, close, volume of a stock
// API key QY9VUQMLHZJS9XJ6
// https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol={TICKER}}&interval={minutes}min&apikey={API_KEY}

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.net.*;
import java.io.BufferedReader;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SharePrice
{
   public static void SharePriceAPI (String args[]) throws IOException
   {
      Scanner input = new Scanner(System.in);
      System.out.println("Enter the stock ticker symbol: ");
      String stock = input.nextLine();
      System.out.println("Enter 1 for open, 2 for high, 3 for low, 4 for close, or 5 for volume: ");
      int menu_choice = input.nextInt();
      System.out.println("Enter the month, day, and year (e.g. 06 09 2021): ");
      int intMonth = input.nextInt();
      int intDay = input.nextInt();
      int intYear = input.nextInt();

      String year = Integer.toString(intYear);
      String month = Integer.toString(intMonth);
      String day = Integer.toString(intDay);

      if (intMonth < 10)
      {
         month = "0" + month;
      }

      if (intDay < 10)
      {
         day = "0" + day;
      }

      String date = year + "-" + month + "-" + day;
      String urlStr = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stock + "&apikey=QY9VUQMLHZJS9XJ6";

      URL url = new URL(urlStr);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
      httpURLConnection.setRequestProperty("Content-Type", "application/json");
      BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

      String resultStream, result = "", choice = "", choicePrint = "";
      while ((resultStream = in.readLine()) != null)
         result += resultStream;
      in.close();

      switch(menu_choice)
      {
         case 1:
            choice = "1. open";
            choicePrint = "open";
            break;
         case 2:
            choice = "2. high";
            choicePrint = "high";
            break;
         case 3:
            choice = "3. low";
            choicePrint = "low";
            break;
         case 4:
            choice = "4. close";
            choicePrint = "close";
            break;
         case 5:
            choice = "5. volume";
            choicePrint = "volume";
            break;
      }

      System.out.println("\nThe " + choicePrint + " for " + stock + " was " + parse(result, date, choice) + " on " + date + ".");
   }

   public static String parse(String jsonLine, String date, String choice) {
      JsonElement jelement = new JsonParser().parse(jsonLine);
      JsonObject jobject = jelement.getAsJsonObject();
      jobject = jobject.getAsJsonObject("Time Series (Daily)");
      jobject = jobject.getAsJsonObject(date);
      String result = jobject.get(choice).getAsString();
      return result;
   }
}
