package io.github.joselitosn.notifications;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NtfyNotification extends NotificationHandler{
    String destination;
    String topic;

    public NtfyNotification(String destination, String topic) {
        this.destination = destination;
        this.topic = topic;
    }
    @Override
    public void handle(Notification notification) {
        try {
            URI uri = new URI(this.destination + this.topic);
            URL urlConn = uri.toURL();
            String data = "{\"title\": \"" + notification.getTitle() + "\", \"message\": \"" + notification.getMessage() + "\"}";
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
