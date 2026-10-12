package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VentilatorControlApp {
    private JFrame frame;
    private JLabel statusLabel;
    private JLabel temperatureLabel;
    private JPanel mainPanel;
    private JPanel modePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentilatorControlApp app = new VentilatorControlApp();
            app.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        // Frame setup
        frame = new JFrame("Ventilator Control");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // Main panel setup
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Mode panel setup
        modePanel = new JPanel();
        modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));
        modePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the main panel to the frame
        frame.setLayout(new BorderLayout());
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Initialize main menu
        showMainMenu();
    }

    private void showMainMenu() {
        // Clear previous components
        mainPanel.removeAll();

        // Buttons for Manual and Automatic mode
        JButton manualButton = new JButton("Manual Mode");
        manualButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manualButton.addActionListener(e -> showManualMode());

        JButton automaticButton = new JButton("Automatic Mode");
        automaticButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        automaticButton.addActionListener(e -> showAutomaticMode());

        // Add buttons to main panel
        mainPanel.add(new JLabel("Select Ventilator Control Mode:", SwingConstants.CENTER));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(manualButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(automaticButton);

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }

    private void showManualMode() {
        // Clear previous components
        mainPanel.removeAll();

        JLabel manualLabel = new JLabel("Manual Control:");
        manualLabel.setFont(new Font("Arial", Font.BOLD, 16));
        manualLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton onButton = new JButton("Turn On");
        onButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        onButton.addActionListener(e -> updateVentilatorStatus("On"));

        JButton offButton = new JButton("Turn Off");
        offButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        offButton.addActionListener(e -> updateVentilatorStatus("Off"));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(e -> showMainMenu());

        mainPanel.add(manualLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(onButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(offButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(cancelButton);

        // Refresh the frame
        frame.revalidate();
        frame.repaint();
    }

    private void showAutomaticMode() {
        // Clear previous components
        mainPanel.removeAll();

        JLabel automaticLabel = new JLabel("Automatic Control:");
        automaticLabel.setFont(new Font("Arial", Font.BOLD, 16));
        automaticLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Ventilator will open if temperature > 20°C.");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize temperature label
        temperatureLabel = new JLabel("Fetching temperature...");
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        temperatureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.addActionListener(e -> showMainMenu());

        mainPanel.add(automaticLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(instructionLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(temperatureLabel); // Add the temperature label to the panel
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(cancelButton);

        // Refresh the frame
        frame.revalidate();
        frame.repaint();

        // Automatic ventilator control
        fetchTemperature();
    }

    private void fetchTemperature() {
        // Coordinates for Addis Ababa
        double latitude = 9.03;
        double longitude = 38.74;

        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude + "&current_weather=true";
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if (apiConnection.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(frame, "Unable to connect to weather API.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String jsonResponse = readApiResponse(apiConnection);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current_weather");

            double temperature = (double) currentWeatherJson.get("temperature");
            temperatureLabel.setText("Temperature: " + temperature + "°C"); // Update the label

            // Automatic control logic
            if (temperature > 20) {
                updateVentilatorStatus("On");
            } else {
                updateVentilatorStatus("Off");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "An error occurred while fetching weather data.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }




    private void updateVentilatorStatus(String status) {
        if (statusLabel == null) {
            statusLabel = new JLabel();
            mainPanel.add(statusLabel);
        }
        statusLabel.setText("Ventilator Status: " + status);
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
