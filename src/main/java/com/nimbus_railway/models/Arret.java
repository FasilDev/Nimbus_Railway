package com.nimbus_railway.models;

public class Arret {
    private int id;
    private String nom;
    private TypeArret typeArret;

    public enum TypeArret {
        DEPART("DEPART"),
        TERMINUS("TERMINUS");

        private final String displayName;

        TypeArret(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static TypeArret fromDisplayName(String displayName) {
            for (TypeArret type : TypeArret.values()) {
                if (type.getDisplayName().equals(displayName)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Type d'arrêt inconnu: " + displayName);
        }
    }

    // Constructeur par défaut
    public Arret() {
    }

    // Constructeur complet
    public Arret(int id, String nom, TypeArret typeArret) {
        this.id = id;
        this.nom = nom;
        this.typeArret = typeArret;
    }

    // Constructeur sans id (pour les nouveaux arrêts)
    public Arret(String nom, TypeArret typeArret) {
        this.nom = nom;
        this.typeArret = typeArret;
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

    public TypeArret getTypeArret() {
        return typeArret;
    }

    public void setTypeArret(TypeArret typeArret) {
        this.typeArret = typeArret;
    }

    @Override
    public String toString() {
        return nom + " (" + typeArret.getDisplayName() + ")";
    }
}