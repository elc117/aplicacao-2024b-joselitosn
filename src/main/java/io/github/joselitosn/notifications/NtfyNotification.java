package io.github.joselitosn.notifications;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NtfyNotification extends NotificationHandler{
    @Override
    public void handle(Notification notification) {
        // send ntfy notification to destination
        String url = "https://ntfy.sh/";
        String topic = "notifications";
        String title = notification.getTitle();
        String message = notification.getMessage();

        try {
            URI uri = new URI(url + topic);
            URL urlConn = uri.toURL();
            String data = "{\"title\": \"" + title + "\", \"message\": \"" + message + "\"}";
            HttpURLConnection conn = (HttpURLConnection) urlConn.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8)) {
                writer.write(data);
            }
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }


        System.out.println("Sending ntfy notification: " + notification.getMessage());
        super.handle(notification);
    }
}
