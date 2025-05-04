package com.kerware.simulateurreusine;

/**
 * Représente une tranche du barème progressif.
 * Exigence 2.1 : permettre le calcul d'impôt par tranches.
 */
public class Tranche {

    private final double taux;
    private final int borneInf;
    private final int borneSup;

    public Tranche(int borneInf, int borneSup, double taux) {
        this.borneInf = borneInf;
        this.borneSup = borneSup;
        this.taux = taux;
    }

    public double calculerPourPart(double revenu) {
        if (revenu <= borneInf) return 0;
        double baseImposable = Math.min(revenu, borneSup) - borneInf;
        return baseImposable * taux;
    }

    public double getTaux() {
        return taux;
    }

    public int getBorneInf() {
        return borneInf;
    }

    public int getBorneSup() {
        return borneSup;
    }
}
