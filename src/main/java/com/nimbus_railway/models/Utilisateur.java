package com.nimbus_railway.models;

public class Utilisateur {
    private int id;
    private String identifiant;
    private String motDePasse;
    private String nom;
    private String prenom;
    private Role role;

    public enum Role {
        ADMIN, CONTROLEUR
    }

    // Constructeur par défaut
    public Utilisateur() {
    }

    // Constructeur complet
    public Utilisateur(int id, String identifiant, String motDePasse, String nom, String prenom, Role role) {
        this.id = id;
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    // Constructeur sans id (pour les nouveaux utilisateurs)
    public Utilisateur(String identifiant, String motDePasse, String nom, String prenom, Role role) {
        this.identifiant = identifiant;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + role + ")";
    }
}