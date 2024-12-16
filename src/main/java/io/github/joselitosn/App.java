package io.github.joselitosn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.github.joselitosn.db.DatabaseManager;
import io.github.joselitosn.log.LogEventListener;
import io.github.joselitosn.log.LogManager;
import io.github.joselitosn.notifications.Notification;
import io.github.joselitosn.notifications.NotificationHandler;
import io.github.joselitosn.notifications.NtfyNotification;
import io.github.joselitosn.notifications.SMTPNotification;

import javax.swing.*;

/**
 * Classe principal da aplicação.
 */
public class App {
    public static void main(String[] args) {
        LogManager logManager = LogManager.getInstance();
        // instâncias singleton, opcionalmente pode-se fornecer um logger personalizado
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dbManager.registerSubscriber(new LogEventListener(logManager.getLogger()));

        Connection connection = dbManager.getConnection();

        Notification notification = new Notification("Test", "Primeiro Teste");

        NotificationHandler chain = new SMTPNotification("joselitostrike@gmail.com");
        chain.setNextHandler(new NtfyNotification("https://ntfy.sh/", "notifications"));

        chain.handle(notification);

        // create table
        try {
            createTables(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // close connection
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NotificationSchedulerGUI();
            }
        });
    }

    /**
     * Cria as tabelas do banco de dados.
     */
    public static void createTables(Connection connection) throws SQLException {
        // create table if not exists using prepared statement in sqlite
        String sqlNotification = "CREATE TABLE IF NOT EXISTS notification (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, message TEXT, creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, execution_time TIMESTAMP)";
        PreparedStatement statementNotification = connection.prepareStatement(sqlNotification);
        statementNotification.execute();
    }
}
