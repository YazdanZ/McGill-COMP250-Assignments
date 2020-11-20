// Represents a HoneyBee
public abstract class HoneyBee extends Insect {


    private int cost; // indicating how much this bee costs in food.


    /**
     * The constructor for a HoneyBee
     *
     * @param position position of the insect in the game
     * @param health   health points (hp) of the insect
     * @param cost cost in food
     */
    public HoneyBee (Tile position, int health, int cost) {
        super (position, health);
        this.cost = cost;
    }

    public int getCost(){
        return this.cost;
    }

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (!(o instanceof HoneyBee)) return false;
        if (!super.equals ( o )) return false;
        HoneyBee honeyBee = (HoneyBee) o;
        return getCost () == honeyBee.getCost ();
    }

}
