package com.nimbus_railway.models;

public class Eleve {
    private int id;
    private String nom;
    private String prenom;
    private Maison maison;

    public enum Maison {
        SERDAIGLE("SERDAIGLE"),
        GRYFFONDOR("GRYFFONDOR"),
        SERPENTARD("SERPENTARD"),
        POUFSOUFFLE("POUFSOUFFLE");

        private final String displayName;

        Maison(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static Maison fromDisplayName(String displayName) {
            for (Maison maison : Maison.values()) {
                if (maison.getDisplayName().equals(displayName)) {
                    return maison;
                }
            }
            throw new IllegalArgumentException("Maison inconnue: " + displayName);
        }
    }

    // Constructeur par défaut
    public Eleve() {
    }

    // Constructeur complet
    public Eleve(int id, String nom, String prenom, Maison maison) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.maison = maison;
    }

    // Constructeur sans id (pour les nouveaux élèves)
    public Eleve(String nom, String prenom, Maison maison) {
        this.nom = nom;
        this.prenom = prenom;
        this.maison = maison;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Maison getMaison() {
        return maison;
    }

    public void setMaison(Maison maison) {
        this.maison = maison;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + maison.getDisplayName() + ")";
    }
}