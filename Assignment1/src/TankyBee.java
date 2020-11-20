// Represents bees that are slow to sting, but are very sturdy
public class TankyBee extends HoneyBee {

    private int damage; // attack damage of the bee
    private int armor; // armor of the bee
    private static final int cost = 3;
    private static final int health = 30;


    /**
     * The constructor for a TankyBee
     *
     * @param position position of the insect in the game
     * @param damage attack damage of the bee
     * @param armor armor of the bee
     */
    public TankyBee (Tile position, int damage, int armor) {
        super (position, health, cost);
        this.armor = armor;
        this.damage = damage;
    }


    /**
     * Tanky bees can only sting hornets that are
     * positioned on the same tile as them. The bee will look to sting the first hornet in the
     * swarm (if not empty) by inflicting them an amount of damage equal to the bee’s attack
     * damage. If no hornet is there, then the tanky bee doesn't do anything.
     *
     * @return true if the bee stings a hornet, false otherwise
     */
    @Override
    public boolean takeAction () {

        if(this.getPosition ().getNumOfHornets () != 0){
            this.getPosition ().getHornet ().takeDamage ( this.damage );
            return true;
        }
        return false;
    }


    /**
     * Since this bee is tanky, before applying the damage
     * to the bee (in the same way as takeDamage() from Insect does), the damage should be
     * multiplied by a multiplier which depends on this bee’s armor. The multiplier is a double
     * equal to 100/(100 + armor). The damage to be applied to the bee should be then round
     * toward 0.
     * @param damageReceived indicate the damage received by the insect
     */
    @Override
    public void takeDamage ( int damageReceived ) {
        double damageMultiplier = 100.0 / (this.armor + 100.0);
        damageReceived = (int) (damageReceived * damageMultiplier);

        super.takeDamage ( damageReceived );
    }

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (!(o instanceof TankyBee)) return false;
        if (!super.equals ( o )) return false;
        TankyBee tankyBee = (TankyBee) o;
        return damage == tankyBee.damage &&
                armor == tankyBee.armor;
    }
}
