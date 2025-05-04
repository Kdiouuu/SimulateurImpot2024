package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Représente un foyer fiscal pour le calcul de l'impôt.
 * Exigence 1.1 : gérer les paramètres de situation familiale et revenus.
 */
public class FoyerFiscal {

    private int revenuDeclarant1;
    private int revenuDeclarant2;
    private SituationFamiliale situationFamiliale;
    private int nbEnfants;
    private int nbEnfantsHandicapes;
    private boolean parentIsole;

    public FoyerFiscal(int revenu1, int revenu2, SituationFamiliale situation, int enfants, int enfantsHandi, boolean parentIsole) {
        this.revenuDeclarant1 = revenu1;
        this.revenuDeclarant2 = revenu2;
        this.situationFamiliale = situation;
        this.nbEnfants = enfants;
        this.nbEnfantsHandicapes = enfantsHandi;
        this.parentIsole = parentIsole;
    }

    public int getRevenuTotal() {
        return revenuDeclarant1 + revenuDeclarant2;
    }

    public double getNombreParts() {
        double parts = 1.0;
        switch (situationFamiliale) {
            case MARIE:
            case PACSE:
                parts = 2.0;
                break;
            case CELIBATAIRE:
            case VEUF:
            case DIVORCE:
                parts = 1.0;
                break;
        }

        // Enfants à charge
        if (nbEnfants == 1) parts += 0.5;
        else if (nbEnfants == 2) parts += 1.0;
        else if (nbEnfants > 2) parts += 1.0 + (nbEnfants - 2);

        // Enfants en situation de handicap
        parts += nbEnfantsHandicapes * 0.5;

        // Parent isolé (majoration)
        if (parentIsole && nbEnfants > 0) {
            parts += 0.5;
        }

        return parts;
    }

    public int getRevenu1() {
        return revenuDeclarant1;
    }

    public int getRevenu2() {
        return revenuDeclarant2;
    }

    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public boolean isParentIsole() {
        return parentIsole;
    }

    public int getNbEnfants() {
        return nbEnfants;
    }

    public int getNbEnfantsHandicapes() {
        return nbEnfantsHandicapes;
    }
}
