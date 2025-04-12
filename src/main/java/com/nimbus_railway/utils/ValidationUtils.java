package com.nimbus_railway.utils;

/**
 * Classe utilitaire pour valider les entrées utilisateur
 */
public class ValidationUtils {

    /**
     * Vérifie si une chaîne est vide ou null
     * @param str Chaîne à vérifier
     * @return true si la chaîne est vide ou null, false sinon
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Vérifie si une chaîne est un nombre entier valide
     * @param str Chaîne à vérifier
     * @return true si la chaîne est un nombre entier valide, false sinon
     */
    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Vérifie si une chaîne est un nombre entier positif valide
     * @param str Chaîne à vérifier
     * @return true si la chaîne est un nombre entier positif valide, false sinon
     */
    public static boolean isPositiveInteger(String str) {
        if (!isInteger(str)) {
            return false;
        }

        return Integer.parseInt(str) > 0;
    }

    /**
     * Vérifie si une chaîne est un nombre entier valide dans une plage donnée
     * @param str Chaîne à vérifier
     * @param min Valeur minimale (inclue)
     * @param max Valeur maximale (inclue)
     * @return true si la chaîne est un nombre entier valide dans la plage, false sinon
     */
    public static boolean isIntegerInRange(String str, int min, int max) {
        if (!isInteger(str)) {
            return false;
        }

        int value = Integer.parseInt(str);
        return value >= min && value <= max;
    }

    /**
     * Vérifie si une chaîne respecte une longueur minimale et maximale
     * @param str Chaîne à vérifier
     * @param minLength Longueur minimale (inclue)
     * @param maxLength Longueur maximale (inclue)
     * @return true si la chaîne respecte la longueur, false sinon
     */
    public static boolean isLengthValid(String str, int minLength, int maxLength) {
        if (str == null) {
            return minLength == 0;
        }

        return str.length() >= minLength && str.length() <= maxLength;
    }

    /**
     * Vérifie si une adresse email est valide
     * @param email Adresse email à vérifier
     * @return true si l'adresse email est valide, false sinon
     */
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}