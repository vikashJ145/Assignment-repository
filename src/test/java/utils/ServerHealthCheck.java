package utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerHealthCheck {

  public static boolean isServerRunning(String urlString) {
    try {
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(2000);
      connection.connect();
      int code = connection.getResponseCode();
      return (code >= 200 && code < 400);
    } catch (IOException e) {
      return false;
    }
  }
}
