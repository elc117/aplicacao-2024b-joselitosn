package io.github.joselitosn;

import io.github.joselitosn.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class NotificationSchedulerGUI extends JFrame {

    private JTextField titleField;
    private JTextArea messageArea;
    private JSpinner dateTimeSpinner;

    public NotificationSchedulerGUI() {
        setTitle("Notification Scheduler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        titlePanel.add(titleLabel);
        titlePanel.add(titleField);

        // Message Panel
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel messageLabel = new JLabel("Message:");
        messageArea = new JTextArea(5, 20);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messagePanel.add(messageLabel);
        messagePanel.add(messageScrollPane);

        // Date/Time Panel
        JPanel dateTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateTimeLabel = new JLabel("Date/Time:");


        LocalDateTime now = LocalDateTime.now();
        Date toDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        dateTimeSpinner = new JSpinner(new SpinnerDateModel(toDate, null, null, java.util.Calendar.MINUTE));

        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTimeSpinner, "yyyy-MM-dd HH:mm"); // Format
        dateTimeSpinner.setEditor(dateEditor);


        dateTimePanel.add(dateTimeLabel);
        dateTimePanel.add(dateTimeSpinner);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveNotification();
            }
        });


        buttonPanel.add(saveButton);


        // Add panels to the main frame
        add(titlePanel, BorderLayout.NORTH);
        add(messagePanel, BorderLayout.CENTER);
        add(dateTimePanel, BorderLayout.EAST);


        add(buttonPanel, BorderLayout.PAGE_END);


        setVisible(true);
    }


    private void saveNotification() {
        String title = titleField.getText();
        String message = messageArea.getText();

        LocalDateTime scheduledTime = LocalDateTime.ofInstant(((java.util.Date) dateTimeSpinner.getValue()).toInstant(), ZoneId.systemDefault());

        String formattedDateTime = scheduledTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String sql = "INSERT INTO notification (title, message, execution_time) VALUES (?, ?, ?)";


        try {
            Connection connection = DatabaseManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, message);
            statement.setString(3, formattedDateTime);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Notification saved!"); // Confirmation dialog
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving notification: " + e.getMessage());
        }


//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//        System.out.println("Title: " + title);
//        System.out.println("Message: " + message);
//        System.out.println("Scheduled Time: " + scheduledTime.format(formatter));
    }
}
