package com.kerware.simulateurreusine;

/**
 * Représente une tranche du barème progressif.
 * Exigence 2.1 : permettre le calcul d'impôt par tranches.
 *
 * Cette classe est finale car elle n'est pas destinée à être étendue.
 */
public final class Tranche {

    private final double taux;
    private final int borneInf;
    private final int borneSup;

    /**
     * Crée une tranche avec les bornes et le taux donnés.
     *
     * @param borneInfParam Borne inférieure de la tranche (incluse).
     * @param borneSupParam Borne supérieure de la tranche (exclue).
     * @param tauxParam Taux appliqué à cette tranche (ex: 0.11 pour 11%).
     */

    public Tranche(int borneInfParam, int borneSupParam, double tauxParam) {
        this.borneInf = borneInfParam;
        this.borneSup = borneSupParam;
        this.taux = tauxParam;
    }

    /**
     * Calcule l'impôt dû sur cette tranche pour un revenu donné.
     *
     * @param revenu Revenu à imposer.
     * @return Impôt dû pour cette tranche.
     */
    public double calculerPourPart(double revenu) {
        if (revenu <= borneInf) {
            return 0;
        }
        double baseImposable = Math.min(revenu, borneSup) - borneInf;
        return baseImposable * taux;
    }

    /**
     * Retourne le taux d'imposition de cette tranche.
     *
     * @return Le taux d'imposition.
     */
    public double getTaux() {
        return taux;
    }

    /**
     * Retourne la borne inférieure de cette tranche.
     *
     * @return La borne inférieure.
     */
    public int getBorneInf() {
        return borneInf;
    }

    /**
     * Retourne la borne supérieure de cette tranche.
     *
     * @return La borne supérieure.
     */
    public int getBorneSup() {
        return borneSup;
    }
}
