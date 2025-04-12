package com.nimbus_railway.models;

public class Train {
    private int id;
    private int nombreWagons;
    private TypeTrain typeTrain;

    public enum TypeTrain {
        NIMBUS_4000("NIMBUS 4000"),
        POUDLARD_EXPRESS("POUDLARD EXPRESS"),
        POUDLARD_EXPRESS_2_0("POUDLARD EXPRESS 2.0");

        private final String displayName;

        TypeTrain(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static TypeTrain fromDisplayName(String displayName) {
            for (TypeTrain type : TypeTrain.values()) {
                if (type.getDisplayName().equals(displayName)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Type de train inconnu: " + displayName);
        }
    }

    // Constructeur par défaut
    public Train() {
    }

    // Constructeur complet
    public Train(int id, int nombreWagons, TypeTrain typeTrain) {
        this.id = id;
        this.nombreWagons = nombreWagons;
        this.typeTrain = typeTrain;
    }

    // Constructeur sans id (pour les nouveaux trains)
    public Train(int nombreWagons, TypeTrain typeTrain) {
        this.nombreWagons = nombreWagons;
        this.typeTrain = typeTrain;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombreWagons() {
        return nombreWagons;
    }

    public void setNombreWagons(int nombreWagons) {
        this.nombreWagons = nombreWagons;
    }

    public TypeTrain getTypeTrain() {
        return typeTrain;
    }

    public void setTypeTrain(TypeTrain typeTrain) {
        this.typeTrain = typeTrain;
    }

    @Override
    public String toString() {
        return "Train #" + id + " - " + typeTrain.getDisplayName() + " (" + nombreWagons + " wagons)";
    }
}