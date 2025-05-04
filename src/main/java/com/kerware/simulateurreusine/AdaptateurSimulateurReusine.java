package com.kerware.simulateurreusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;

/**
 * Adaptateur pour rendre compatible le simulateur réusiné avec l'interface existante.
 * Permet de valider la non-régression des anciens tests.
 * Exigence 4.1 : compatibilité descendante.
 */
public final class AdaptateurSimulateurReusine implements ICalculateurImpot {

    private int revenu1 = 0;
    private int revenu2 = 0;
    private SituationFamiliale situation;
    private int nbEnfants = 0;
    private int nbEnfantsHandicapes = 0;
    private boolean parentIsole = false;

    private SimulateurReusine simulateur;

    @Override
    public void setRevenusNetDeclarant1(int rn) {
        this.revenu1 = rn;
    }

    @Override
    public void setRevenusNetDeclarant2(int rn) {
        this.revenu2 = rn;
    }

    @Override
    public void setSituationFamiliale(com.kerware.simulateur.SituationFamiliale sf) {
        this.situation = SituationFamiliale.valueOf(sf.name());
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
        this.nbEnfants = nbe;
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsHandicapes = nbesh;
    }

    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        FoyerFiscal foyer = new FoyerFiscal(
                revenu1, revenu2, situation, nbEnfants, nbEnfantsHandicapes, parentIsole
        );
        this.simulateur = new SimulateurReusine(foyer);
        simulateur.calculer();
    }

    @Override
    public int getRevenuNetDeclatant1() {
        return revenu1;
    }

    @Override
    public int getRevenuNetDeclatant2() {
        return revenu2;
    }

    @Override
    public double getContribExceptionnelle() {
        return simulateur.getContributionExceptionnelle();
    }

    @Override
    public int getRevenuFiscalReference() {
        return (int) simulateur.getRevenuNet();
    }

    @Override
    public int getAbattement() {
        return (int) simulateur.getAbattement();
    }

    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateur.getNbParts();
    }

    @Override
    public int getImpotAvantDecote() {
        return (int) simulateur.getImpotAvantDecote();
    }

    @Override
    public int getDecote() {
        return (int) simulateur.getDecote();
    }

    @Override
    public int getImpotSurRevenuNet() {
        return (int) simulateur.getImpotNet();
    }
}
