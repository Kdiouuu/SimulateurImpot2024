# SimulateurReusine

## 📌 Description

**SimulateurReusine** est une application Java permettant de simuler le calcul de l'impôt sur le revenu en France, en tenant compte de :
- la situation familiale,
- les revenus des déclarants,
- le nombre d'enfants (y compris en situation de handicap),
- les règles d'abattement, décote et contribution exceptionnelle.

Le projet comprend un moteur de calcul (`SimulateurReusine`) et un adaptateur (`AdaptateurSimulateurReusine`) pour une intégration simplifiée avec des interfaces externes.

---

## 🛠️ Technologies

- Java 17+
- JUnit 5 (tests unitaires)
- Maven (build, dépendances, plugins)
- Checkstyle (vérification du style de code)

---

## 📦 Installation

1. Clonez le dépôt :

```bash
git clone https://github.com/Kdiouuu/SimulateurImpot2024.git

```

2. Compilez le projet :

>  utilisez simplement `mvn`.

---

## 🚀 Exécution des tests

Lancez les tests unitaires avec :

```bash
./mvnw test
```

Tous les tests sont situés dans `src/test/java/simulateurreusine/SimulateurReusineTest.java`. La couverture des tests est de 100 %.
Exécuter  les tests avec IntelliJ.
---

## ✅ Analyse de style (Checkstyle)

Le projet utilise Checkstyle pour garantir la qualité du code source.
Pour verifier checkstyle utilisez le plugin Checkstyle-IDEA


---

## 🧪 Structure des tests

- **FoyerFiscal** : vérifie le calcul du nombre de parts fiscales selon la situation.
- **Tranche & BaremeIR** : tests du barème progressif par tranches.
- **Abattement** : vérifie les planchers et plafonds d’abattement.
- **Decote & ContribExceptionnelle** : tests sur les mécanismes d'ajustement d'impôt.
- **SimulateurReusine** : tests d’intégration complète sur des cas types.
- **AdaptateurSimulateurReusine** : tests sur l’interface d’adaptation.

---

## 📄 Exemple de cas testé

```java
FoyerFiscal f = new FoyerFiscal(40000, 20000, SituationFamiliale.MARIE, 2, 1, false);
SimulateurReusine simu = new SimulateurReusine(f);
simu.calculer();
System.out.println("Impôt net : " + simu.getImpotNet());
```

---

## 📚 Règles fiscales intégrées

- Barème progressif 2023
- Abattement de 10 % plafonné
- Décote pour faibles revenus
- Contribution exceptionnelle sur hauts revenus

---
