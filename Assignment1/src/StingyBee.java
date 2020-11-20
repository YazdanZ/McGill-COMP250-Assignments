// Represents bees that sting their enemies
public class StingyBee extends HoneyBee {

    private int damage; //attack damage
    private static final int cost = 1;
    private static final int health = 10;
    /**
     * The constructor for a StingyBee
     *
     * @param position position of the insect in the game
     * @param damage attack damage
     */
    public StingyBee (Tile position, int damage) {
        super (position, health, cost);
        this.damage = damage;
    }

    /**
     * A sting-y bee that is positioned on the path that
     * leads from the nest to the hive or on the bee hive itself will attempt to sting an hornet.
     * If on the other hand, it is not positioned on the path that leads from the nest to the hive
     * nor on the bee hive itself, then it won’t do anything.
     *
     * It will start to look for the first non-empty swarm that it can find on the path that leads
     * from the bee’s tile to the hornet nest. When it finds it, it will sting the first hornet in
     * the swarm by inflicting it an amount of damage equal to this bee’s attack damage.
     *
     * he bees cannot sting a hornet that is positioned on its nest!
     *
     *
     * @return true if the bee stings a hornet
     */
    @Override
    public boolean takeAction () {

         if(!this.getPosition ().isOnThePath () && !this.getPosition ().isHive ())
             return false;

        // attempt to sting a hornet
        // start to look for the first non-empty swarm that it can find on the path that leads
        // from the bee’s tile to the hornet nest. When it finds it, it will sting the first hornet in


        // check if there's a hornet of the current tile
        // if not move a step forward
        Tile currentTile = this.getPosition ();
        while (currentTile.getNumOfHornets () == 0 && !currentTile.isNest ()){
            currentTile = currentTile.towardTheNest ();
        }

        if(currentTile.getNumOfHornets () != 0){
            currentTile.getHornet ().takeDamage ( this.damage );
            return true;
        }

        return false;
    }

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (!(o instanceof StingyBee)) return false;
        if (!super.equals ( o )) return false;
        StingyBee stingyBee = (StingyBee) o;
        return damage == stingyBee.damage;
    }
}
