// A Tile is a square on the board on which the game will be played
public class Tile {

    /*
    Bee collect food to feed their larva. This is how strong bees are raised.
    If a player wants to add a new bee to the game, they will need to have enough food to do so.
    */
    private int food; //  food present on the tile.

    private boolean beeHive; // whether or not a bee hive has been built on the tile
    private boolean hornetNest; // whether or not a hornet nest has been built on the tile

    // whether or not this tile is part of the path that leads from the
    // hornet nest to the bee hive
    private boolean partOfPath;

    // a reference to the next tile on the path from the hornet nest toward
    // the bee hive. It contains null if the tile is not on the path or at the end of it
    private Tile toBeeHive;

    // a reference to the next tile on the path from the bee hive toward the
    // hornet nest. It contains null if the tile is not on the path or at the end of it
    private Tile toHornetNest;

    private HoneyBee bee; // the bee positioned on the tile

    private SwarmOfHornets swarmOfHornets; // all the hornets positioned on the tile

    /**
     * The constructor for a Tile
     * It initializes all fields to default values
     */
    public Tile(){
//        beeHive = false;
//        hornetNest = false;
//        partOfPath = false;
//        food = 0;
//        bee = null;
//        swarmOfHornets = null;
//        toBeeHive = null;
//        toHornetNest = null;
    }

    /**
     * The constructor for a Tile
     *
     * @param food food present on the tile
     * @param beeHive whether or not a bee hive has been built on the tile
     * @param hornetNest whether or not a hornet nest has been built on the tile
     * @param partOfPath whether or not this tile is part of the path that leads from the
     *     // hornet nest to the bee hive
     * @param toBeeHive reference to the next tile on the path from the hornet nest toward
     *     // the bee hive
     * @param toHornetNest reference to the next tile on the path from the bee hive toward the
     *     // hornet nest
     * @param bee the bee positioned on the tile
     * @param swarmOfHornets all the hornets positioned on the tile
     */
    public Tile(int food, boolean beeHive, boolean hornetNest, boolean partOfPath, Tile toBeeHive,
                Tile toHornetNest, HoneyBee bee, SwarmOfHornets swarmOfHornets){

        this.food = food;
        this.beeHive = beeHive;
        this.hornetNest = hornetNest;
        this.partOfPath = partOfPath;
        this.toBeeHive = toBeeHive;
        this.toHornetNest = toHornetNest;
        this.bee = bee;
        this.swarmOfHornets = swarmOfHornets;
    }

    public boolean isHive(){
        return this.beeHive;
    }

    public boolean isNest(){
        return this.hornetNest;
    }

    /**
     * builds a bee hive on this tile. This is a void method that
     * simply updates the field indicating whether or not there’s a bee hive
     * on the tile
     */
    public void buildHive(){
        this.beeHive = true;
    }

    /**
     * builds a hornet nest on this tile. This is a void method that
     * simply updates the field indicating whether or not there’s a
     * hornet nest on the tile
     */
    public void buildNest(){
        this.hornetNest = true;
    }

    public boolean isOnThePath(){
        return this.partOfPath;
    }

    public Tile towardTheHive(){
        return this.toBeeHive;
    }

    public Tile towardTheNest(){
        return this.toHornetNest;
    }

    public void createPath(Tile toBeeHive, Tile toHornetNest){
        this.toBeeHive = toBeeHive;
        this.toHornetNest = toHornetNest;
        this.partOfPath = true;
    }

    public int collectFood(){
        int tempFood = this.food;
        this.food = 0;
        return tempFood;
    }

    public void storeFood(int addedFood){
        this.food += addedFood;
    }

    public HoneyBee getBee(){
        return this.bee;
    }

    public Hornet getHornet(){
        return  this.swarmOfHornets.getFirstHornet ();
    }

    public int getNumOfHornets(){
        if(this.swarmOfHornets == null)
            return 0;
        return this.swarmOfHornets.sizeOfSwarm ();
    }

    /**
     * Add an insect tho this tile
     * @param insect insect to add
     * @return if the operation was successful
     */
    public boolean addInsect(Insect insect){
        if(insect instanceof HoneyBee){
            // can only position one bee on a tile
            // can't position bees on hornet nests
            if (this.bee != null || this.hornetNest)
                return false;
            insect.setPosition ( this );
            this.bee = (HoneyBee) insect;
            return true;
        }

        if(insect instanceof Hornet){
            // no limit to the number of hornets that can be positioned on a tile
            if(this.hornetNest || this.beeHive || this.partOfPath){
                if(this.swarmOfHornets == null)
                    swarmOfHornets = new SwarmOfHornets ();
                insect.setPosition ( this );
                this.swarmOfHornets.addHornet ((Hornet) insect);
                return true;
            }
            return false;
        }

         return false;
    }

    /**
     * Remove an insect from this tile
     * @param insect Insect to remove from tile
     * @return if the operation was successful
     */
    public boolean removeInsect(Insect insect){
        if(insect instanceof HoneyBee){
            if(insect == this.bee)
                insect.setPosition ( null );
                this.bee = null;
                return true;
            }

        if(insect instanceof Hornet)
            if(this.swarmOfHornets.removeHornet ( (Hornet) insect )){
                insect.setPosition ( null );
                return true;
            }

        return false;
    }

}
