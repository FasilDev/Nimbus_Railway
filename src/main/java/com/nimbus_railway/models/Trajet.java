package com.nimbus_railway.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trajet {
    private int id;
    private LocalDateTime horaireDepart;
    private LocalDateTime horaireArrivee;
    private int trainId;
    private int arretDepartId;
    private int arretArriveeId;

    // Variables pour stocker les objets liés (non stockés en base)
    private Train train;
    private Arret arretDepart;
    private Arret arretArrivee;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Constructeur par défaut
    public Trajet() {
    }

    // Constructeur complet
    public Trajet(int id, LocalDateTime horaireDepart, LocalDateTime horaireArrivee,
                  int trainId, int arretDepartId, int arretArriveeId) {
        this.id = id;
        this.horaireDepart = horaireDepart;
        this.horaireArrivee = horaireArrivee;
        this.trainId = trainId;
        this.arretDepartId = arretDepartId;
        this.arretArriveeId = arretArriveeId;
    }

    // Constructeur sans id (pour les nouveaux trajets)
    public Trajet(LocalDateTime horaireDepart, LocalDateTime horaireArrivee,
                  int trainId, int arretDepartId, int arretArriveeId) {
        this.horaireDepart = horaireDepart;
        this.horaireArrivee = horaireArrivee;
        this.trainId = trainId;
        this.arretDepartId = arretDepartId;
        this.arretArriveeId = arretArriveeId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHoraireDepart() {
        return horaireDepart;
    }

    public void setHoraireDepart(LocalDateTime horaireDepart) {
        this.horaireDepart = horaireDepart;
    }

    public LocalDateTime getHoraireArrivee() {
        return horaireArrivee;
    }

    public void setHoraireArrivee(LocalDateTime horaireArrivee) {
        this.horaireArrivee = horaireArrivee;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public int getArretDepartId() {
        return arretDepartId;
    }

    public void setArretDepartId(int arretDepartId) {
        this.arretDepartId = arretDepartId;
    }

    public int getArretArriveeId() {
        return arretArriveeId;
    }

    public void setArretArriveeId(int arretArriveeId) {
        this.arretArriveeId = arretArriveeId;
    }

    // Getters et Setters pour les objets liés
    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
        if (train != null) {
            this.trainId = train.getId();
        }
    }

    public Arret getArretDepart() {
        return arretDepart;
    }

    public void setArretDepart(Arret arretDepart) {
        this.arretDepart = arretDepart;
        if (arretDepart != null) {
            this.arretDepartId = arretDepart.getId();
        }
    }

    public Arret getArretArrivee() {
        return arretArrivee;
    }

    public void setArretArrivee(Arret arretArrivee) {
        this.arretArrivee = arretArrivee;
        if (arretArrivee != null) {
            this.arretArriveeId = arretArrivee.getId();
        }
    }

    // Méthodes utilitaires
    public String getHoraireDepartFormat() {
        return horaireDepart.format(formatter);
    }

    public String getHoraireArriveeFormat() {
        return horaireArrivee.format(formatter);
    }

    public String getDuree() {
        long minutes = java.time.Duration.between(horaireDepart, horaireArrivee).toMinutes();
        long heures = minutes / 60;
        minutes = minutes % 60;

        return String.format("%dh %02dmin", heures, minutes);
    }

    @Override
    public String toString() {
        String departStr = arretDepart != null ? arretDepart.getNom() : "Arrêt #" + arretDepartId;
        String arriveeStr = arretArrivee != null ? arretArrivee.getNom() : "Arrêt #" + arretArriveeId;
        String trainStr = train != null ? train.getTypeTrain().getDisplayName() : "Train #" + trainId;

        return String.format("Trajet #%d: %s → %s (%s) - %s",
                id, departStr, arriveeStr, getDuree(), trainStr);
    }
}