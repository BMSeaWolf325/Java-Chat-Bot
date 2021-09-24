import org.jibble.pircbot.*;

public class MyBotMain
{
   public static void main(String[] args) throws Exception
   {
      MyBot bot = new MyBot();
      bot.setVerbose(true);

      try {
         bot.connect("irc.freenode.net");
      }
      catch (Exception e) {
         System.out.println("Can't connect: " + e);
         return;
      }
      String channelName = "#temp";
      bot.joinChannel(channelName);
      bot.sendMessage(channelName,"Hey! Enter any message and I'll respond!");

   }
}