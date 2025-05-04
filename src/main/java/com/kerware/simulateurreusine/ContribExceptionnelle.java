package com.kerware.simulateurreusine;

/**
 * Exigence 2.6 : Contribution exceptionnelle sur les hauts revenus.
 */
public class ContribExceptionnelle {

    private static final int SEUIL_TAUX_3 = 250_000;
    private static final int SEUIL_TAUX_4 = 500_000;
    private static final double TAUX_3 = 0.03;
    private static final double TAUX_4 = 0.01;

    public static double calculer(int revenu, double parts) {
        double revenuParPart = revenu / parts;
        double contribution = 0;

        if (revenuParPart > SEUIL_TAUX_3) {
            contribution = (revenuParPart - SEUIL_TAUX_3) * TAUX_3;
        }
        if (revenuParPart > SEUIL_TAUX_4) {
            contribution += (revenuParPart - SEUIL_TAUX_4) * TAUX_4; // Supplémentaire à 4 %
        }

        return contribution * parts;
    }
}
