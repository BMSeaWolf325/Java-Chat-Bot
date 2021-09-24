import org.jibble.pircbot.*;

import java.io.IOException;

public class MyBot extends PircBot
{
   private boolean weatherFlag = false;
   private int choice = 0;
   private String cityzip = "", weather = "";
   public MyBot()
   {
      this.setName("MyBot");
   }

   public void onMessage(String channel, String sender,
                         String login, String hostname, String message)
   {
      if (message.equalsIgnoreCase("Weather") && !weatherFlag)
      {
         weatherFlag = true;
         sendMessage(channel, "Enter 1 if you want to find the weather by city, or 2 if you want to find it by zip");
      }
      else if (message.equalsIgnoreCase("time") && !weatherFlag)
      {
         String time = new java.util.Date().toString();
         sendMessage(channel, sender + ": The time is now " + time);
      }
      else if (message.equalsIgnoreCase("Hello") && !weatherFlag)
      {
         String time = new java.util.Date().toString();
         sendMessage(channel, "Hey " + sender + "!");
      }
      else if (message.equalsIgnoreCase("1") && weatherFlag && choice == 0)
      {
         choice = 1;
         sendMessage(channel,"Enter city name: ");
      }
      else if (message.equalsIgnoreCase("2") && weatherFlag && choice == 0)
      {
         choice = 2;
         sendMessage(channel,"Enter zip code: ");
      }
      else if (weatherFlag && choice == 1)
      {
         Weather w = new Weather();
         try
         {
            weather = w.WeatherAPI(choice, message);
            sendMessage(channel, weather);
            weatherFlag = false;
            choice = 0;
            cityzip = "";
            weather = "";
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
      else if (weatherFlag && choice == 2)
      {
         Weather w = new Weather();
         try
         {
            weather  = w.WeatherAPI(choice, message);
            sendMessage(channel, weather);
            weatherFlag = false;
            choice = 0;
            cityzip = "";
            weather = "";
         } catch (IOException e)
         {
            e.printStackTrace();
         }
      }
   }

   public void onKick(String channel, String kickerNick,
                      String login, String hostname,
                      String recipientNick, String reason)
   {
      if (recipientNick.equalsIgnoreCase(getNick()))
      {
         joinChannel(channel);
      }
   }

   public void onDisconnect()
   {
      while (!isConnected())
      {
         try
         {
            reconnect();
         } catch (Exception e)
         {
            // Couldn’t reconnect.
            // Pause for a short while before retrying?
         }
      }
   }

   public void onUserList(String channel, User[] users) {
      for (int i = 0; i < users.length; i++) {
         User user = users[i];
         String nick = user.getNick();
         System.out.println(nick);
      }
   }
}