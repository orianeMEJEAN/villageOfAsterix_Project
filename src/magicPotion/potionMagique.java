package magicPotion;

import java.util.Arrays;
import java.util.List;

public class potionMagique {

    // Ingrédients optionnels
    private boolean avecHomard;
    private boolean avecFraises;
    private boolean avecJusBetterave;
    private boolean avecLaitLicorne;
    private boolean avecPoilsIdefix;

    // Propriétés de la potion
    private int nombreDoses;
    private static final int DOSES_PAR_MARMITE = 10;

    // Constructeur
    public potionMagique() {
        this.nombreDoses = DOSES_PAR_MARMITE;
    }

    // Méthodes pour ajouter des ingrédients optionnels
    public void ajouterHomard() {
        this.avecHomard = true;
        System.out.println("Homard ajouté - Potion plus nourrissante !");
    }

    public void ajouterFraises() {
        this.avecFraises = true;
        System.out.println("Fraises ajoutées - Potion plus nourrissante !");
    }

    public void remplacerParJusBetterave() {
        this.avecJusBetterave = true;
        System.out.println("Huile de roche remplacée par du jus de betterave - Potion plus nourrissante !");
    }

    public void ajouterLaitLicorne() {
        this.avecLaitLicorne = true;
        System.out.println("Lait de licorne à deux têtes ajouté - Pouvoir de dédoublement !");
    }

    public void ajouterPoilsIdefix() {
        this.avecPoilsIdefix = true;
        System.out.println("Poils d'Idéfix ajoutés - Pouvoir de métamorphosis !");
    }

    // Méthode pour boire une dose
    public boolean boireUneDose() {
        if (nombreDoses <= 0) {
            System.out.println("La marmite est vide !");
            return false;
        }

        //boire une dose
        nombreDoses--;

        // Afficher les effets obtenus
        System.out.println("\n une dose bu!");
        System.out.println("Effets temporaires obtenus :");
        System.out.println("  - Force surhumaine");
        System.out.println("  - Invincibilité");

        // Effets spéciaux avec les ingrédients
        if (avecLaitLicorne) {
            System.out.println("  - Dédoublement");
        }
        if (avecPoilsIdefix) {
            System.out.println("  - Métamorphose");
        }

        System.out.println("Doses restantes : " + nombreDoses + "\n");
        return true;
    }

    // Méthode pour boire une marmite entière
    public boolean boireUneMarmite() {
        if (nombreDoses < DOSES_PAR_MARMITE) {
            System.out.println("Doses insuffisante !");
            return false;
        }

        //boire la marmite entière
        nombreDoses -= DOSES_PAR_MARMITE;

        // Afficher les effets permanents
        System.out.println("\n marmite bu !");
        System.out.println("Effets permanents obtenus :");
        System.out.println("  - Force");
        System.out.println("  - Invincibilité");

        if (avecLaitLicorne) {
            System.out.println("  - Dédoublement");
        }
        if (avecPoilsIdefix) {
            System.out.println("  - Métamorphose");
        }

        System.out.println();
        return true;
    }

    // Méthode pour boire deux marmites
    public boolean boireDeuxMarmites() {
        if (nombreDoses < DOSES_PAR_MARMITE * 2) {
            System.out.println("Pas assez de doses !");
            return false;
        }

        nombreDoses -= DOSES_PAR_MARMITE * 2;

        System.out.println("\n deux marmites bu !");
        System.out.println("Transformation en statue de granit !");
        System.out.println();
        return true;
    }

    // Méthode pour remplir la marmite
    public void remplirMarmite() {
        nombreDoses = DOSES_PAR_MARMITE;
        System.out.println("Marmite remplie ! " + DOSES_PAR_MARMITE + " doses disponibles.\n");
    }

    //Méthode pour ajouter une marmite pour pouvoir en boire deux
    public void ajouterMarmite() {
        nombreDoses += DOSES_PAR_MARMITE;
        System.out.println("Marmite ajoutée ! " + DOSES_PAR_MARMITE + " doses supplémentaires disponibles.\n");
    }

    // Getters
    public int getNombreDoses() {
        return nombreDoses;
    }

    public boolean estNourrissante() {
        return avecHomard || avecFraises || avecJusBetterave;
    }

    // Méthode d'affichage de la recette
    public void afficherRecette() {
        System.out.println("\nRECETTE DE LA POTION MAGIQUE");
        System.out.println("Ingrédients de base :");

        if (avecHomard || avecFraises || avecJusBetterave ||
                avecLaitLicorne || avecPoilsIdefix) {
            System.out.println("\nIngrédients spéciaux ajoutés :");
            if (avecHomard) System.out.println("  - Homard");
            if (avecFraises) System.out.println("  - Fraises");
            if (avecJusBetterave) System.out.println("  - Jus de betterave");
            if (avecLaitLicorne) System.out.println("  - Lait de licorne à deux têtes");
            if (avecPoilsIdefix) System.out.println("  - Poils d'Idéfix");
        }

        System.out.println("\n📊 Doses disponibles : " + nombreDoses);
    }
}