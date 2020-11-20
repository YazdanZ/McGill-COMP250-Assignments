// Represents a Hornet
public class Hornet extends Insect {

    private int damage; //attack damage

    /**
     * The constructor for a Hornet
     *
     * @param position position of the insect in the game
     * @param health   health points (hp) of the insect
     * @param damage attack damage of the hornet
     */
    public Hornet (Tile position, int health, int damage) {
        super (position, health);
        this.damage = damage;
    }

    /**
     * If there’s a bee positioned on the same tile as
     * this hornet, the hornet will sting it inflicting on the bee an amount of damage equal to
     * this hornet’s attack damage. If on the other hand there’s no bee on the same tile as
     * this hornet, then the hornet will try to move toward the bee hive. Unless of course the
     * hornet is already on the bee hive, in which case there’s nothing for this hornet to do (the
     * hornets have won the game!). In the case in which the hornet tries to move toward the
     * hive, it will do so by moving to the next tile on the path.
     *
     * @return  true whether the hornet either stings a bee or moves to the next tile
     */
    @Override
    public boolean takeAction () {
        if(this.getPosition ().getBee () != null){
            //sting the bee
            this.getPosition ().getBee ().takeDamage ( damage );
            return true;
        }
        else if(!this.getPosition ().isHive ()){
            //move towards the bee hive
            Tile currentTile = this.getPosition ();
            Tile nextTile = currentTile.towardTheHive ();

            currentTile.removeInsect ( this );
            nextTile.addInsect ( this );
            return true;
        }
        return false;
    }

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Hornet)) return false;
        if (!super.equals ( o )) return false;
        Hornet hornet = (Hornet) o;
        return damage == hornet.damage;
    }

}
