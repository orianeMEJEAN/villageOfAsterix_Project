package characters;

import characters.inventory.Inventory;
import enums.Gender;
import enums.PersonType;
import magicPotion.MagicPotion;
import magicPotion.Pot;

/**
 * Represents a generic character in the game world.
 * This abstract class defines all shared attributes and the
 * abstract actions common to any living entity capable of
 * fighting, eating, healing, drinking magic potion,
 * and potentially dying.
 */
public abstract class Character {

    private String name;
    private Gender gender;
    private int height;
    private int age;
    private int strength;
    private int endurance;
    private int health;
    private int hunger;
    private int belligerence;
    private int magicPotionLevel;
    private PersonType personType;
    private Inventory inventory;
    private PersonType originalType;

    private int potionCountdown;
    private boolean potionPermanent;

    private final int maxHealth;
    private final int maxHunger;

    private final static int POTION_COUNTDOWN_VALUE = 3;

    /**
     * Creates a new character with all base attributes.
     *
     * @param name             character's name
     * @param gender           biological gender
     * @param height           height in centimeters
     * @param age              age in years
     * @param strength         physical strength
     * @param endurance        resistance to damage
     * @param health           current health points
     * @param hunger           current hunger level
     * @param belligerence     belligerence level
     * @param magicPotionLevel current magic potion quantity
     */
    public Character(String name,
                     Gender gender,
                     int height,
                     int age,
                     int strength,
                     int endurance,
                     int health,
                     int hunger,
                     int belligerence,
                     int magicPotionLevel,
                     PersonType personType
    ) {

        this.name = name;
        this.gender = gender;
        this.height = height;
        this.age = age;
        this.strength = strength;
        this.endurance = endurance;
        this.health = health;
        this.maxHealth = health;
        this.hunger = hunger;
        this.maxHunger = hunger;
        this.belligerence = belligerence;
        this.magicPotionLevel = magicPotionLevel;
        this.personType =  personType;
        this.originalType = personType;
        this.potionPermanent = false;

    }

    /**
     * Heals this character by a given amount.
     * Takes the smaller value between the health healed and the maxHealth.
     *
     * @param points amount of healing points to add
     */
    public void heal(int points) {
        this.health = Math.min(this.health + points, maxHealth);
    }

    /**
     * Feeds this character and reduces hunger.
     *
     * @param points amount of hunger reduction
     */
    public void eat(int points){
        this.hunger = Math.min(this.hunger + points, maxHunger);
    }

    /**
     * Drinks a dose of magic potion.
     *
     * If the character drinks 1 pot of potion the effects are permanents
     *
     * If the Character drinks 2 pots of potion it kills him
     */
    public void drinkPotion(Pot pot){
        if(pot.drinkADose()){
            magicPotionLevel = magicPotionLevel + 1;
            potionCountdown = POTION_COUNTDOWN_VALUE;

            if (magicPotionLevel >= pot.getDosesPerPot()){
                this.potionPermanent = true;
            }

            if (magicPotionLevel >= pot.getDosesPerPot()*2){
                System.out.println(name + " est devenu une statue de granit.");
                die();
            }

            if (pot.getMagicPotion().isNourishing()){
                eat(2);
            }

            if (pot.getMagicPotion().isWithIdefixsHair()){
                personType = PersonType.fantasy;
            }

            if (pot.getMagicPotion().isWithUnicornMilk()){
                // TODO Clone
            }
        } else {
            System.out.println("Il n'y a plus de potion magique!");
        }
    }

    /**
     * Allows the character to fight another one
     */
    public void fight(Character character) {
        int damages = this.endurance * this.strength;
        character.receiveDamage(damages);
    }

    /**
     * Handles the damage system to correctly kill the character.
     *
     * @param points
     */
    public void receiveDamage(int points){
        if (potionCountdown == 0) {
            this.health -= points;
            if (this.health <= 0) {
                die();
            }
        }
    }

    /**
     * Makes this character die.
     * Implementations should update internal state to reflect death.
     */
    public void die(){
        health = 0;
        System.out.println(this.name + " est mort.");
    }

    /**
     * Check if the character is alive
     *
     * @return Boolean
     */
    public Boolean isDead(){
        if (this.health <= 0) {
            return true;
        }
        return false;
    }

    /**
     * Simulate the evaporation of the potion's powers.
     *
     * @return
     */
    public void PotionEffectEvaporation() {
        if (!this.potionPermanent && potionCountdown > 0) {
            potionCountdown--;
        }

        if (potionCountdown == 0) {
            personType = originalType;
        }
    }

    /**
     * Returns the character's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the character's gender.
     *
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Returns the character's height in centimeters.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the character's age in years.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the character's physical strength.
     *
     * @return the strength value
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Returns the character's endurance level.
     *
     * @return the endurance value
     */
    public int getEndurance() {
        return endurance;
    }

    /**
     * Returns the character's current health points.
     *
     * @return the health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the character's current hunger level.
     *
     * @return the hunger value
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Returns the character's belligerence level.
     *
     * @return the belligerence value
     */
    public int getBelligerence() {
        return belligerence;
    }

    /**
     * Returns the current magic potion level.
     *
     * @return the magic potion amount
     */
    public int getMagicPotionLevel() {
        return magicPotionLevel;
    }

    /**
     * Returns the maximum Health of the character
     * @return maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the maximum hunger of the character
     * @return maxHunger
     */
    public int getMaxHunger(){
        return maxHunger;
    }

    /**
     * Returns the person's type (Gaul, Roman, ...).
     */
    public PersonType getPersonType() {
        return personType;
    }

    /**
     * Returns if the Potion's effect are permanent.
     */
    public boolean getPotionPermanent() {
        return potionPermanent;
    }

    /**
     * Returns the Potions's effect countdown
     *
     */
    public int getPotionCountdown() {
        return potionCountdown;
    }

    /**
     * Returns the inventory
     *
     * @return inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the character's name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the character's gender.
     *
     * @param gender the new gender value
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Sets the character's height.
     *
     * @param height the new height in centimeters
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the character's age.
     *
     * @param age the new age in years
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets the character's physical strength.
     *
     * @param strength the new strength value
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * Sets the character's endurance level.
     *
     * @param endurance the new endurance value
     */
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    /**
     * Sets the character's current health points.
     *
     * @param health the new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Sets the character's hunger level.
     *
     * @param hunger the new hunger value
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * Sets the character's belligerence level.
     *
     * @param belligerence the new belligerence value
     */
    public void setBelligerence(int belligerence) {
        this.belligerence = belligerence;
    }

    /**
     * Sets the current magic potion level.
     *
     * @param magicPotion the new potion amount
     */
    public void setMagicPotion(int magicPotion) {
        this.magicPotionLevel = magicPotion;
    }

    /**
     * Sets the potion's permanent effects
     *
     * @param potionPermanent
     */
    public void setPotionPermanent(boolean potionPermanent) {
        this.potionPermanent = potionPermanent;
    }

    /**
     * Sets the potion's countdown
     *
     * @param potionCountdown
     */
    public void setPotionCountdown(int potionCountdown) {
        this.potionCountdown = potionCountdown;
    }

    /**
     * Sets the Character's type to another one
     */
    public void setPersonType(PersonType personType) {
        this.personType = personType;
    }

    /**
     * Sets the Character's inventory
     */

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Returns a string representation of this character.
     * The output contains all attributes with their current values.
     *
     * @return a formatted string describing the character
     */
    @Override
    public String toString() {
        return "Character {" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", age=" + age +
                ", strength=" + strength +
                ", endurance=" + endurance +
                ", health=" + health +
                ", maxHealth=" + maxHealth +
                ", hunger=" + hunger +
                ", maxHunger=" + maxHunger +
                ", belligerence=" + belligerence +
                ", magicPotionLevel=" + magicPotionLevel +
                '}';
    }
}
