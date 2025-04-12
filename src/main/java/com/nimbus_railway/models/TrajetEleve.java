package com.nimbus_railway.models;

public class TrajetEleve {
    private int id;
    private int idEleve;
    private int idTrajet;

    // Variables pour stocker les objets liés (non stockés en base)
    private Eleve eleve;
    private Trajet trajet;

    // Constructeur par défaut
    public TrajetEleve() {
    }

    // Constructeur complet
    public TrajetEleve(int id, int idEleve, int idTrajet) {
        this.id = id;
        this.idEleve = idEleve;
        this.idTrajet = idTrajet;
    }

    // Constructeur sans id (pour les nouvelles associations)
    public TrajetEleve(int idEleve, int idTrajet) {
        this.idEleve = idEleve;
        this.idTrajet = idTrajet;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public int getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(int idTrajet) {
        this.idTrajet = idTrajet;
    }

    // Getters et Setters pour les objets liés
    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
        if (eleve != null) {
            this.idEleve = eleve.getId();
        }
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
        if (trajet != null) {
            this.idTrajet = trajet.getId();
        }
    }

    @Override
    public String toString() {
        String eleveStr = eleve != null ? eleve.getPrenom() + " " + eleve.getNom() : "Élève #" + idEleve;
        String trajetStr = trajet != null ? trajet.toString() : "Trajet #" + idTrajet;

        return eleveStr + " - " + trajetStr;
    }
}