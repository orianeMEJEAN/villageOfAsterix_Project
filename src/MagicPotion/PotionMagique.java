package MagicPotion;

/**
 * Représente la potion magique d'Astérix et Obélix.
 *
 * Cette classe permet de créer une potion et de rajouter différents ingrédients,
 * qui donne des "pouvoirs",
 * de la consommer par doses ou marmites entières, et de gérer son stock.
 * Une marmite contient 10 doses par défaut.
 *
 * @author Lou
 * @version 1.0
 */
public class PotionMagique {

    /** Liste des ingrédients optionnels
     * qui rend la potion nourrissante ou donne des pouvoirs
     */
    private boolean avecHomard;
    private boolean avecFraises;
    private boolean avecJusBetterave;
    private boolean avecLaitLicorne;
    private boolean avecPoilsIdefix;

    /** Nombre de doses disponibles dans la marmite */
    private int nombreDoses;

    /** Nombre de doses dans une marmite */
    private static final int DOSES_PAR_MARMITE = 10;

    /**
     * Constructeur de la potion magique.
     *
     * Crée une nouvelle potion avec 10 doses
     */
    public PotionMagique() {
        this.nombreDoses = DOSES_PAR_MARMITE;
    }

    /**
     * Ajoute du homard à la potion,
     * rend la potion nourrissante
     */
    public void ajouterHomard() {
        this.avecHomard = true;
        System.out.println("Homard ajouté - Potion plus nourrissante !");
    }

    /**
     * Ajoute des fraises à la potion.
     * rend la potion nourrissante
     */
    public void ajouterFraises() {
        this.avecFraises = true;
        System.out.println("Fraises ajoutées - Potion plus nourrissante !");
    }

    /**
     * Remplace l'huile de roche par du jus de betterave,
     *rend la potion nourrissante
     */
    public void remplacerParJusBetterave() {
        this.avecJusBetterave = true;
        System.out.println("Huile de roche remplacée par du jus de betterave - Potion plus nourrissante !");
    }

    /**
     * Ajoute du lait de licorne à deux têtes à la potion.
     * Donne un pouvoir de dédoublement
     */
    public void ajouterLaitLicorne() {
        this.avecLaitLicorne = true;
        System.out.println("Lait de licorne à deux têtes ajouté - Pouvoir de dédoublement !");
    }

    /**
     * Ajoute des poils d'Idéfix à la potion.
     * Donne un pouvoir de métamorphose
     */
    public void ajouterPoilsIdefix() {
        this.avecPoilsIdefix = true;
        System.out.println("Poils d'Idéfix ajoutés - Pouvoir de métamorphosis !");
    }

    /**
     * Permet de boire une seule dose de potion.
     * Diminue le nombre de doses de 1 et affiche
     * les pouvoirs obtenus
     *
     * @return true si une dose a pu être consommée, false si la marmite est vide
     */
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

    /**
     * Permet de boire une marmite entière de potion
     * et affiches les pouvoirs obtenu
     *
     * @return true si une marmite a pu être consommée, false s'il n'y a pas assez de doses
     */
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

    /**
     * Permet de boire deux marmites entières de potion
     * Nécessite au moins 20 doses disponibles
     *
     * @return true si deux marmites ont pu être consommées, false s'il n'y a pas assez de doses
     */
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

    /**
     * Remplit complètement la marmite.
     * Réinitialise le nombre de doses à 10
     */
    public void remplirMarmite() {
        nombreDoses = DOSES_PAR_MARMITE;
        System.out.println("Marmite remplie ! " + DOSES_PAR_MARMITE + " doses disponibles.\n");
    }

    /**
     * Ajoute une marmite de potion.
     * Augmente le nombre de doses disponibles de 10.
     * Pour préparer la consommation de deux marmites.
     */
    public void ajouterMarmite() {
        nombreDoses += DOSES_PAR_MARMITE;
        System.out.println("Marmite ajoutée ! " + DOSES_PAR_MARMITE + " doses supplémentaires disponibles.\n");
    }

    /**
     * Retourne le nombre de doses actuellement disponibles.
     *
     * @return le nombre de doses dans la marmite
     */
    public int getNombreDoses() {
        return nombreDoses;
    }

    /**
     * Vérifie si la potion est nourrissante.
     *
     * @return true si la potion est nourrissante, false sinon
     */
    public boolean estNourrissante() {
        return avecHomard || avecFraises || avecJusBetterave;
    }

    /**
     * Affiche la recette complète de la potion.
     *
     * Affiche les ingrédients
     * et le nombre de doses actuellement disponibles.
     */
    public void afficherRecette() {
        System.out.println("\nRECETTE DE LA POTION MAGIQUE");
        System.out.println("Ingrédients de base :");
        System.out.println("  - Gui");
        System.out.println("  - Carottes");
        System.out.println("  - Sel");
        System.out.println("  - Trèfle à quatre feuilles");
        System.out.println("  - Poisson passablement frais");
        System.out.println("  - Huile de roche");
        System.out.println("  - Miel");
        System.out.println("  - Hydromel");
        System.out.println("  - Ingredient secret");

        if (avecHomard || avecFraises || avecJusBetterave ||
                avecLaitLicorne || avecPoilsIdefix) {
            System.out.println("\nIngrédients spéciaux ajoutés :");
            if (avecHomard) System.out.println("  - Homard");
            if (avecFraises) System.out.println("  - Fraises");
            if (avecJusBetterave) System.out.println("  - Jus de betterave");
            if (avecLaitLicorne) System.out.println("  - Lait de licorne à deux têtes");
            if (avecPoilsIdefix) System.out.println("  - Poils d'Idéfix");
        }

        System.out.println("\n Doses disponibles : " + nombreDoses);
    }
}