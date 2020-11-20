// Represent bees that collect pollen
public class BusyBee extends HoneyBee {

    private static final int cost = 2;
    private static final int health = 5;

    private static final int foodToAdd = 2; // food that the busy bee adds to the tile
    /**
     * The constructor for a BusyBee
     *
     * @param position position of the insect in the game
     */
    public BusyBee (Tile position) {
        super (position, health, cost);
    }

    /**
     * This type of bees collect pollen, so executing an
     * action results into 2 food being added to the tile
     * where the bee is positioned.
     * @return true
     */
    @Override
    public boolean takeAction () {
        this.getPosition ().storeFood ( foodToAdd );
        return true;
    }
}
