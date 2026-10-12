package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherSwingApp {
    private JFrame frame;
    private JLabel temperatureLabel;
    private JLabel statusLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherSwingApp app = new WeatherSwingApp();
            app.createAndShowGUI();
            app.fetchWeatherData(); // Automatically fetch weather data on startup
        });
    }

    private void createAndShowGUI() {
        // Frame setup
        frame = new JFrame("Weather App - Addis Ababa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels
        JLabel cityLabel = new JLabel("Weather in Addis Ababa", SwingConstants.CENTER);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        temperatureLabel = new JLabel("Temperature: N/A", SwingConstants.CENTER);
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 16));
        temperatureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        statusLabel = new JLabel("Status: N/A", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to panel
        panel.add(cityLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(temperatureLabel);
        panel.add(statusLabel);

        // Add panel to frame and display
        frame.add(panel);
        frame.setVisible(true);
    }

    private double fetchWeatherData() {
        // Coordinates for Addis Ababa
        double latitude = 9.03;
        double longitude = 38.74;
        double temperature = 0;

        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude + "&current_weather=true";
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if (apiConnection.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(frame, "Unable to connect to weather API.", "Error", JOptionPane.ERROR_MESSAGE);
                return 0;
            }

            String jsonResponse = readApiResponse(apiConnection);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current_weather");

            temperature = (double) currentWeatherJson.get("temperature");
            temperatureLabel.setText("Temperature: " + temperature + "°C");

            String status = getTemperatureStatus(temperature);
            statusLabel.setText("Status: " + status);

            return temperature;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while fetching weather data.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }
        return temperature;
    }

    private String getTemperatureStatus(double temperature) {
        if (temperature > 25) {
            return "Hot";
        } else if (temperature < 10) {
            return "Cold";
        } else {
            return "Optimal";
        }
    }

    private String readApiResponse(HttpURLConnection apiConnection) {
        try {
            StringBuilder resultJson = new StringBuilder();
            Scanner scanner = new Scanner(apiConnection.getInputStream());
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            scanner.close();
            return resultJson.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpURLConnection fetchApiResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
















