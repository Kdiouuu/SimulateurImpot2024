package simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class SimulateurReusineTest {

    private static BaremeIR bareme;

    @BeforeAll
    static void setup() {
        bareme = new BaremeIR();
    }

    // === Tests unitaires FoyerFiscal.getNombreParts() ===

    @Test
    void testParts_CelibataireSansEnfants() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.CELIBATAIRE, 0, 0, false);
        assertEquals(1.0, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_MarieDeuxEnfants() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.MARIE, 2, 0, false);
        assertEquals(3.0, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_PacseTroisEnfants() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.PACSE, 3, 0, false);
        assertEquals(4.0, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_Handicapes() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.CELIBATAIRE, 0, 2, false);
        assertEquals(2.0, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_ParentIsoleAvecEnfants() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.DIVORCE, 1, 1, true);
        assertEquals(2.5, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_MarieAvecQuatreEnfants() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.MARIE, 4, 0, false);
        assertEquals(5.0, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_CelibataireAvecEnfantsHandicapes() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.CELIBATAIRE, 0, 3, false);
        assertEquals(2.5, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_ParentIsoleAvecDeuxEnfants() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.DIVORCE, 2, 0, true);
        assertEquals(2.5, f.getNombreParts(), 1e-6);
    }

    @Test
    void testParts_VeryLargeFamily() {
        FoyerFiscal f = new FoyerFiscal(0, 0, SituationFamiliale.MARIE, 100, 0, false);
        // 2 parts + (1 + (100-2)) = 2 + 99 = 101
        assertEquals(101.0, f.getNombreParts(), 1e-6);
    }

    // === Tests BaremeIR.calculerImpotParPart() ===

    @Test
    void testBareme_PremiereTranche() {
        assertEquals(0.0, bareme.calculerImpotParPart(10000), 1e-6);
    }

    @Test
    void testBareme_DeuxiemeTranche() {
        double v = bareme.calculerImpotParPart(15000);
        assertEquals((15000 - 11295) * 0.11, v, 1e-6);
    }

    @Test
    void testBareme_MultiTranches() {
        double v = bareme.calculerImpotParPart(200000);
        assertTrue(v > (177107 - 82342) * 0.41);
    }

    // === Tests pour Tranche.java ===

    @Test
    void testTranche_RevenuZero() {
        Tranche t = new Tranche(10000, 20000, 0.11);
        assertEquals(0.0, t.calculerPourPart(0), 1e-6);
    }

    @Test
    void testTranche_BorneInferieure() {
        Tranche t = new Tranche(10000, 20000, 0.11);
        assertEquals(0.0, t.calculerPourPart(10000), 1e-6);
    }

    @Test
    void testTranche_RevenuAuMilieu() {
        Tranche t = new Tranche(10000, 20000, 0.11);
        assertEquals((15000 - 10000) * 0.11, t.calculerPourPart(15000), 1e-6);
    }

    @Test
    void testTranche_BorneSuperieure() {
        Tranche t = new Tranche(10000, 20000, 0.11);
        assertEquals((20000 - 10000) * 0.11, t.calculerPourPart(20000), 1e-6);
    }

    // === Tests Abattement ===

    @Test
    void testAbattement_Plancher() {
        assertEquals(472.0, Abattement.calculer(3000), 1e-6);
    }

    @Test
    void testAbattement_Intermediaire() {
        assertEquals(1000.0, Abattement.calculer(10000), 1e-6);
    }

    @Test
    void testAbattement_Plafond() {
        assertEquals(13648.0, Abattement.calculer(200000), 1e-6);
    }

    // === Tests Decote ===

    @Test
    void testDecote_AucunePourHautImpot() {
        assertEquals(0.0, Decote.calculer(5000, 2.0), 1e-6);
    }

    @Test
    void testDecote_ActifCelibataire() {
        assertEquals(1852 * 0.75 - 1000, Decote.calculer(1000, 1.0), 1e-6);
    }

    @Test
    void testDecote_ActifCouple() {
        assertEquals(3069 * 0.75 - 2000, Decote.calculer(2000, 2.0), 1e-6);
    }

    @Test
    void testDecote_MinimumIncome() {
        FoyerFiscal f = new FoyerFiscal(1000, 0, SituationFamiliale.CELIBATAIRE, 0, 0, false);
        SimulateurReusine s = new SimulateurReusine(f);
        s.calculer();
        assertTrue(s.getDecote() > 0);
    }

    // === Tests ContribExceptionnelle ===

    @Test
    void testContrib_Aucun() {
        assertEquals(0.0, ContribExceptionnelle.calculer(249000, 1.0), 1e-6);
    }

    @Test
    void testContrib_3pourcentSeulement() {
        assertEquals((300000 - 250000) * 0.03, ContribExceptionnelle.calculer(300000, 1.0), 1e-6);
    }

    @Test
    void testContrib_4pourcentApres500k() {
        double expected = (600000 - 250000) * 0.03 + (600000 - 500000) * 0.01;
        assertEquals(expected, ContribExceptionnelle.calculer(600000, 1.0), 1e-6);
    }

    @Test
    void testContrib_MultipleParts() {
        double exp = (300000 - 250000) * 0.03 * 2;
        assertEquals(exp, ContribExceptionnelle.calculer(600000, 2.0), 1e-6);
    }

    // === Tests d’intégration SimulateurReusine ===

    @Test
    void testSimu_CelibataireSansEnfants() {
        FoyerFiscal f = new FoyerFiscal(30000, 0, SituationFamiliale.CELIBATAIRE, 0, 0, false);
        SimulateurReusine s = new SimulateurReusine(f);
        s.calculer();
        assertEquals(30000, s.getRevenuNet(), 1e-6);
        assertEquals(30000 - s.getAbattement(), s.getRevenuImposable(), 1e-6);
        assertTrue(s.getImpotNet() >= 0);
    }

    @Test
    void testSimu_CoupleAvecEnfantsEtHandi() {
        FoyerFiscal f = new FoyerFiscal(40000, 40000, SituationFamiliale.MARIE, 3, 1, false);
        SimulateurReusine s = new SimulateurReusine(f);
        s.calculer();
        assertEquals(80000, s.getRevenuNet(), 1e-6);
        assertEquals(4.5, s.getNbParts(), 1e-6);
        assertTrue(s.getImpotAvantDecote() >= 0);
    }

    @Test
    void testSimu_ParentIsoleAvecEnfant() {
        FoyerFiscal f = new FoyerFiscal(20000, 0, SituationFamiliale.DIVORCE, 1, 0, true);
        SimulateurReusine s = new SimulateurReusine(f);
        s.calculer();
        assertEquals(2.0, s.getNbParts(), 1e-6);
        assertTrue(s.getImpotNet() >= 0);
    }

    @Test
    void testSimu_DecoteActive() {
        FoyerFiscal f = new FoyerFiscal(15000, 0, SituationFamiliale.CELIBATAIRE, 0, 0, false);
        SimulateurReusine s = new SimulateurReusine(f);
        s.calculer();
        assertTrue(s.getDecote() > 0);
        assertEquals(0.0, s.getImpotNet(), 1e-6);
    }

    @Test
    void testSimu_MaximumIncome() {
        FoyerFiscal f = new FoyerFiscal(1_000_000, 1_000_000, SituationFamiliale.MARIE, 4, 2, true);
        SimulateurReusine s = new SimulateurReusine(f);
        s.calculer();
        assertTrue(s.getRevenuNet() > 0);
        assertTrue(s.getImpotNet() >= 0);
    }

    @Test
    void testSimu_ComplexFamily() {
        FoyerFiscal f = new FoyerFiscal(50000, 30000, SituationFamiliale.MARIE, 3, 2, true);
        SimulateurReusine s = new SimulateurReusine(f);
        s.calculer();
        assertTrue(s.getRevenuNet() > 0);
        assertTrue(s.getNbParts() > 3.0);
        assertTrue(s.getImpotNet() >= 0);
    }

    // === Tests pour AdaptateurSimulateurReusine ===

    @Test
    void testAdapter_SetAndGetRevenus() {
        AdaptateurSimulateurReusine adapter = new AdaptateurSimulateurReusine();
        adapter.setRevenusNetDeclarant1(50000);
        adapter.setRevenusNetDeclarant2(30000);
        assertEquals(50000, adapter.getRevenuNetDeclatant1());
        assertEquals(30000, adapter.getRevenuNetDeclatant2());
    }

    @Test
    void testAdapter_SituationEtEnfants() throws Exception {
        AdaptateurSimulateurReusine adapter = new AdaptateurSimulateurReusine();
        adapter.setSituationFamiliale(SituationFamiliale.PACSE);
        adapter.setNbEnfantsACharge(2);
        adapter.setNbEnfantsSituationHandicap(1);
        adapter.setParentIsole(true);

        Field fNb = AdaptateurSimulateurReusine.class.getDeclaredField("nbEnfants");
        Field fH = AdaptateurSimulateurReusine.class.getDeclaredField("nbEnfantsHandicapes");
        Field fIso = AdaptateurSimulateurReusine.class.getDeclaredField("parentIsole");
        fNb.setAccessible(true);
        fH.setAccessible(true);
        fIso.setAccessible(true);

        assertEquals(2, fNb.get(adapter));
        assertEquals(1, fH.get(adapter));
        assertTrue((Boolean) fIso.get(adapter));
    }

    @Test
    void testAdapter_CalculImpotComplet() {
        AdaptateurSimulateurReusine adapter = new AdaptateurSimulateurReusine();
        adapter.setRevenusNetDeclarant1(40000);
        adapter.setRevenusNetDeclarant2(20000);
        adapter.setSituationFamiliale(SituationFamiliale.MARIE);
        adapter.setNbEnfantsACharge(1);
        adapter.setNbEnfantsSituationHandicap(0);
        adapter.setParentIsole(false);
        adapter.calculImpotSurRevenuNet();

        assertEquals(60000, adapter.getRevenuFiscalReference());
        assertTrue(adapter.getAbattement() > 0);
        assertTrue(adapter.getNbPartsFoyerFiscal() >= 2.5);
        assertTrue(adapter.getImpotAvantDecote() >= 0);
        assertTrue(adapter.getDecote() >= 0);
        assertTrue(adapter.getImpotSurRevenuNet() >= 0);
        assertTrue(adapter.getContribExceptionnelle() >= 0);
    }
}
