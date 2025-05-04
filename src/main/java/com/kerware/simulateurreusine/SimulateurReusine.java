package com.kerware.simulateurreusine;

/**
 * Simulateur d'impôt réusiné.
 * Exigence 1.2 à 3.1 : Calcul complet de l'impôt, décote, contribution, etc.
 */
public class SimulateurReusine {

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

    public SimulateurReusine(FoyerFiscal foyer) {
        this.foyer = foyer;
        this.bareme = new BaremeIR();
    }

    public void calculer() {
        revenuNet = foyer.getRevenuTotal();
        abattement = Abattement.calculer((int) revenuNet);
        revenuImposable = revenuNet - abattement;
        nbParts = foyer.getNombreParts();

        double revenuParPart = revenuImposable / nbParts;
        double impotParPart = bareme.calculerImpotParPart(revenuParPart);
        impotAvantDecote = impotParPart * nbParts;

        decote = Decote.calculer(impotAvantDecote, nbParts);
        impotNet = Math.max(0,impotAvantDecote - decote);

        contributionExceptionnelle = ContribExceptionnelle.calculer((int) revenuNet, nbParts);
    }

    public double getRevenuNet() {
        return revenuNet;
    }

    public double getAbattement() {
        return abattement;
    }

    public double getRevenuImposable() {
        return revenuImposable;
    }

    public double getNbParts() {
        return nbParts;
    }

    public double getImpotAvantDecote() {
        return impotAvantDecote;
    }

    public double getDecote() {
        return decote;
    }

    public double getImpotNet() {
        return impotNet;
    }

    public double getContributionExceptionnelle() {
        return contributionExceptionnelle;
    }
}
