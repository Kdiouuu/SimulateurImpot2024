package com.kerware.simulateurreusine;

import java.util.ArrayList;
import java.util.List;

/**
 * Barème progressif de l'impôt sur le revenu 2024 (revenus 2023).
 * Exigence 2.2 : implémenter le barème officiel par tranches.
 *
 * Cette classe n'est pas conçue pour être étendue. Si vous souhaitez ajouter des
 * fonctionnalités supplémentaires, veuillez modifier la classe de manière appropriée.
 */
public final class BaremeIR {

    // Constantes représentant les limites de chaque tranche et les taux d'imposition
    private static final int LIMITE_1 = 11294;
    private static final int LIMITE_2 = 28797;
    private static final int LIMITE_3 = 82341;
    private static final int LIMITE_4 = 177106;
    private static final int LIMITE_5 = 177107;

    private static final double TAUX_1 = 0.0;
    private static final double TAUX_2 = 0.11;
    private static final double TAUX_3 = 0.30;
    private static final double TAUX_4 = 0.41;
    private static final double TAUX_5 = 0.45;

    private final List<Tranche> tranches = new ArrayList<>();

    public BaremeIR() {
        tranches.add(new Tranche(0, LIMITE_1, TAUX_1));
        tranches.add(new Tranche(LIMITE_1 + 1, LIMITE_2, TAUX_2));
        tranches.add(new Tranche(LIMITE_2 + 1, LIMITE_3, TAUX_3));
        tranches.add(new Tranche(LIMITE_3 + 1, LIMITE_4, TAUX_4));
        tranches.add(new Tranche(LIMITE_4 + 1, LIMITE_5, TAUX_5));
    }

    /**
     * Calcule l'impôt pour un revenu par part.
     *
     * @param revenuParPart Le revenu par part pour lequel l'impôt doit être calculé.
     * @return Le montant de l'impôt pour ce revenu par part.
     */
    public double calculerImpotParPart(double revenuParPart) {
        double impot = 0.0;
        for (Tranche t : tranches) {
            impot += t.calculerPourPart(revenuParPart);
        }
        return impot;
    }
}
