package com.kerware.simulateurreusine;

/**
 * Simulateur d'impôt réusiné.
 * Exigence 1.2 à 3.1 : Calcul complet de l'impôt, décote, contribution, etc.
 *
 * Cette classe n'est pas conçue pour être étendue. Si vous souhaitez ajouter des
 * fonctionnalités supplémentaires, veuillez modifier la classe de manière appropriée.
 */
public final class SimulateurReusine {

    private final FoyerFiscal foyer;
    private final BaremeIR bareme;

    private double revenuNet;
    private double abattement;
    private double revenuImposable;
    private double nbParts;
    private double impotAvantDecote;
    private double decote;
    private double impotNet;
    private double contributionExceptionnelle;

    /**
     * Constructeur du simulateur d'impôt réusiné.
     *
     * @param foyerParam Le foyer fiscal pour lequel l'impôt doit être calculé.
     */

    public SimulateurReusine(FoyerFiscal foyerParam) {
        this.foyer = foyerParam;
        this.bareme = new BaremeIR();
    }

    /**
     * Calcule l'impôt total, incluant le revenu net, les abattements, les tranches fiscales,
     * la décote, l'impôt avant et après la décote, et la contribution exceptionnelle.
     */
    public void calculer() {
        revenuNet = foyer.getRevenuTotal();
        abattement = Abattement.calculer((int) revenuNet);
        revenuImposable = revenuNet - abattement;
        nbParts = foyer.getNombreParts();

        double revenuParPart = revenuImposable / nbParts;
        double impotParPart = bareme.calculerImpotParPart(revenuParPart);
        impotAvantDecote = impotParPart * nbParts;

        decote = Decote.calculer(impotAvantDecote, nbParts);
        impotNet = Math.max(0, impotAvantDecote - decote);

        contributionExceptionnelle = ContribExceptionnelle.calculer((int) revenuNet, nbParts);
    }

    /**
     * Obtient le revenu net du foyer fiscal.
     *
     * @return Le revenu net.
     */
    public double getRevenuNet() {
        return revenuNet;
    }

    /**
     * Obtient l'abattement calculé sur le revenu net.
     *
     * @return L'abattement.
     */
    public double getAbattement() {
        return abattement;
    }

    /**
     * Obtient le revenu imposable après abattement.
     *
     * @return Le revenu imposable.
     */
    public double getRevenuImposable() {
        return revenuImposable;
    }

    /**
     * Obtient le nombre de parts fiscales du foyer.
     *
     * @return Le nombre de parts fiscales.
     */
    public double getNbParts() {
        return nbParts;
    }

    /**
     * Obtient l'impôt avant décote.
     *
     * @return L'impôt avant décote.
     */
    public double getImpotAvantDecote() {
        return impotAvantDecote;
    }

    /**
     * Obtient la décote appliquée sur l'impôt.
     *
     * @return La décote.
     */
    public double getDecote() {
        return decote;
    }

    /**
     * Obtient l'impôt net après application de la décote.
     *
     * @return L'impôt net.
     */
    public double getImpotNet() {
        return impotNet;
    }

    /**
     * Obtient la contribution exceptionnelle.
     *
     * @return La contribution exceptionnelle.
     */
    public double getContributionExceptionnelle() {
        return contributionExceptionnelle;
    }
}
