package com.kerware.simulateurreusine;

/**
 * Exigence 2.4 : Appliquer un abattement de 10 % avec plancher et plafond.
 */
public class Abattement {

    private static final double TAUX_ABATTEMENT = 0.10;
    private static final int ABATTEMENT_MIN = 472;
    private static final int ABATTEMENT_MAX = 13648;

    public static double calculer(int revenu) {
        double abattement = revenu * TAUX_ABATTEMENT;
        return Math.max(ABATTEMENT_MIN, Math.min(ABATTEMENT_MAX, abattement));
    }
}
