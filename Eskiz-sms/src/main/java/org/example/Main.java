package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String token = getToken();
        if (token != null) {
            String phone = "998917382004"; // Замените PHONE_NUMBER на фактический номер телефона
            String code = "35131"; // Замените 123456 на фактический код верификации
            sendVerifyCode(phone, code, token);
        }
    }

    private static String getToken() {
        String email = System.getenv("");
        String password = System.getenv("");
        String baseUrl = "https://notify.eskiz.uz/api/auth/login";

        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = "";
                String line;
                while ((line = in.readLine()) != null) {
                    response += line;
                }
                in.close();

                // Parse the JSON response and extract the token
                String token = ""; // Variable to store the token
                // Parse the JSON response and extract the token value
                // Assuming the JSON response has the following structure:
                // {"data": {"token": "your_token_value"}}
                // You can use a JSON parsing library like Gson or Jackson for this.

                return token;
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void sendVerifyCode(String phone, String code, String token) {
        String baseUrl = "https://notify.eskiz.uz/api/message/sms/send";

        try {
            URL url = new URL(baseUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.setDoOutput(true);

            String requestBody = "{\"mobile_phone\": \"" + phone + "\", \"message\": \"" + code + "\", \"from\": \"4546\"}";

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = "";
                String line;
                while ((line = in.readLine()) != null) {
                    response += line;
                }
                in.close();

                System.out.println("Message sent successfully!");
            } else {
                System.out.println("Error: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
