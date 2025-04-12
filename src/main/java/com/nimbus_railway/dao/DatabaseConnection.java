package com.nimbus_railway.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Informations de connexion AlwaysData
    private static final String URL = "jdbc:mysql://mysql-adamsbr.alwaysdata.net:3306/adamsbr_nimbus_railway";
    private static final String USER = "adamsbr";  // Remplacez par votre nom d'utilisateur AlwaysData
    private static final String PASSWORD = "86714564Sl";  // Remplacez par votre mot de passe

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Chargement du pilote JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Établissement de la connexion
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base de données AlwaysData établie avec succès!");
            } catch (ClassNotFoundException e) {
                System.err.println("Le pilote JDBC MySQL est introuvable: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Erreur de connexion à la base de données AlwaysData: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connexion à la base de données fermée avec succès!");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}