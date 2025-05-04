package com.kerware.simulateurreusine;

/**
 * Exigence 2.6 : Contribution exceptionnelle sur les hauts revenus.
 */
public class ContribExceptionnelle {

    public static double calculer(int revenu, double parts) {
        double revenuParPart = revenu / parts;
        double contribution = 0;

        if (revenuParPart > 250000) {
            contribution = (revenuParPart - 250000) * 0.03;
        }
        if (revenuParPart > 500000) {
            contribution += (revenuParPart - 500000) * 0.01; // Supplémentaire à 4 %
        }

        return contribution * parts;
    }
}
