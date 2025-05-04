package com.kerware.simulateurreusine;

/**
 * Exigence 2.5 : Appliquer la décote pour impôt faible.
 */
public class Decote {

    private static final int SEUIL_CELIBATAIRE = 1852;
    private static final int SEUIL_COUPLE = 3069;

    public static double calculer(double impotBrut, double parts) {
        double seuil = (parts > 1.5) ? SEUIL_COUPLE : SEUIL_CELIBATAIRE;

        if (impotBrut >= seuil) return 0;
        return Math.max(0, seuil * 0.75 - impotBrut);
    }
}
