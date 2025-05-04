package com.kerware.simulateurreusine;

import java.util.ArrayList;
import java.util.List;

/**
 * Barème progressif de l'impôt sur le revenu 2024 (revenus 2023).
 * Exigence 2.2 : implémenter le barème officiel par tranches.
 */
public class BaremeIR {

    private final List<Tranche> tranches = new ArrayList<>();

    public BaremeIR() {
        tranches.add(new Tranche(0, 11294, 0.0));
        tranches.add(new Tranche(11295, 28797, 0.11));
        tranches.add(new Tranche(28798, 82341, 0.30));
        tranches.add(new Tranche(82342, 177106, 0.41));
        tranches.add(new Tranche(177107, Integer.MAX_VALUE, 0.45));
    }

    public double calculerImpotParPart(double revenuParPart) {
        double impot = 0.0;
        for (Tranche t : tranches) {
            impot += t.calculerPourPart(revenuParPart);
        }
        return impot;
    }
}
