// Represents an Insect
public abstract class Insect {

    private Tile position; // position of the insect in the game
    private int health; // health points (hp) of the insect

    /**
     * The constructor for an Insect
     * @param position position of the insect in the game
     * @param health health points (hp) of the insect
     */
    public Insect(Tile position, int health) throws IllegalArgumentException{

        this.health = health;

        if(!position.addInsect ( this ))
            throw new IllegalArgumentException ();
    }

    public final Tile getPosition(){
        return this.position;
    }

    public final int getHealth(){
        return this.health;
    }

    public void setPosition ( Tile position ) {
        this.position = position;
    }

    /**
     * Give a damage to the insect
     * @param damageReceived indicate the damage received by the insect
     */
    public void takeDamage(int damageReceived){
        /*if the insect is a bee positioned on the bee hive,
        then the damage should be reduced by 10% before
        being applied (the damage should be rounded toward 0)*/

        if(this instanceof HoneyBee && this.position.isHive())
            damageReceived -= damageReceived *0.10;

        this.health -= damageReceived;

        /*if the insect ends up having a non-positive health (0 or below),
          then the insect has been killed.*/
        if(this.health <= 0){
            this.position.removeInsect ( this );
        }

    }

    public abstract boolean takeAction();

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Insect)) return false;
        Insect insect = (Insect) o;
        return getHealth () == insect.getHealth () &&
                getPosition ().equals ( insect.getPosition () );
    }
}
