package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Représente un foyer fiscal pour le calcul de l'impôt.
 * Exigence 1.1 : gérer les paramètres de situation familiale et revenus.
 */
public final class FoyerFiscal {

    // Constantes pour les parts
    private static final double PART_CELIBATAIRE = 1.0;
    private static final double PART_COUPLE = 2.0;
    private static final double PART_ENFANT_1 = 0.5;
    private static final double PART_ENFANT_2 = 1.0;
    private static final double PART_PAR_ENFANT_SUP = 1.0;
    private static final double PART_HANDICAP = 0.5;
    private static final double PART_PARENT_ISOLE = 0.5;

    private int revenuDeclarant1;
    private int revenuDeclarant2;
    private SituationFamiliale situationFamiliale;
    private int nbEnfants;
    private int nbEnfantsHandicapes;
    private boolean parentIsole;

    /**
     * Crée un foyer fiscal.
     *
     * @param revenu1        revenu du déclarant 1
     * @param revenu2        revenu du déclarant 2
     * @param situation      situation familiale
     * @param enfants        nombre d'enfants à charge
     * @param enfantsHandi   nombre d'enfants handicapés
     * @param estParentIsole parent isolé ou non
     */
    public FoyerFiscal(int revenu1, int revenu2, SituationFamiliale situation,
                       int enfants, int enfantsHandi, boolean estParentIsole) {
        this.revenuDeclarant1 = revenu1;
        this.revenuDeclarant2 = revenu2;
        this.situationFamiliale = situation;
        this.nbEnfants = enfants;
        this.nbEnfantsHandicapes = enfantsHandi;
        this.parentIsole = estParentIsole;
    }

    /**
     * Calcule le revenu total du foyer.
     *
     * @return la somme des revenus des deux déclarants
     */
    public int getRevenuTotal() {
        return revenuDeclarant1 + revenuDeclarant2;
    }

    /**
     * Calcule le nombre de parts fiscales du foyer.
     *
     * @return nombre de parts fiscales
     */
    public double getNombreParts() {
        double parts;

        switch (situationFamiliale) {
            case MARIE:
            case PACSE:
                parts = PART_COUPLE;
                break;
            case CELIBATAIRE:
            case VEUF:
            case DIVORCE:
                parts = PART_CELIBATAIRE;
                break;
            default:
                parts = PART_CELIBATAIRE;
                break;
        }

        if (nbEnfants == 1) {
            parts += PART_ENFANT_1;
        } else if (nbEnfants == 2) {
            parts += PART_ENFANT_2;
        } else if (nbEnfants > 2) {
            parts += PART_PAR_ENFANT_SUP + (nbEnfants - 2);
        }

        parts += nbEnfantsHandicapes * PART_HANDICAP;

        if (parentIsole && nbEnfants > 0) {
            parts += PART_PARENT_ISOLE;
        }

        return parts;
    }

    /**
     * @return revenu du déclarant 1
     */
    public int getRevenu1() {
        return revenuDeclarant1;
    }

    /**
     * @return revenu du déclarant 2
     */
    public int getRevenu2() {
        return revenuDeclarant2;
    }

    /**
     * @return situation familiale
     */
    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    /**
     * @return vrai si le parent est isolé
     */
    public boolean isParentIsole() {
        return parentIsole;
    }

    /**
     * @return nombre d'enfants à charge
     */
    public int getNbEnfants() {
        return nbEnfants;
    }

    /**
     * @return nombre d'enfants handicapés
     */
    public int getNbEnfantsHandicapes() {
        return nbEnfantsHandicapes;
    }
}
