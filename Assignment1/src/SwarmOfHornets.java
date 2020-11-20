// Represents a group of hornets together
//Despite what the name ‘swarm’ might let you think, these hornets group together forming a very neat line
public class SwarmOfHornets {

    private Hornet[] hornets;
    private int swarmSize;

    /**
     * The constructor for a SwarmOfHornets
     * Create an empty swarm
     */
    public SwarmOfHornets(){
        this.swarmSize = 0;
        this.hornets = new Hornet[this.swarmSize];
    }

    public int sizeOfSwarm(){
        return this.swarmSize;
    }

    public Hornet[] getHornets(){
        return this.hornets;
    }

    /**
     *
     * @return the first hornet in the array
     */
    public Hornet getFirstHornet(){
        if (this.swarmSize == 0)
            return null;
        return this.hornets[0];
    }

    /**
     * Add a hornet to the array
     * @param hornet hornet to add
     */
    public void addHornet(Hornet hornet){
        // Create a new array and copy the previous array into
        // Add the new hornet

        Hornet[] resizedHornets = new Hornet[this.swarmSize + 1];
        for(int i = 0; i<swarmSize ; i++){
            resizedHornets[i] = this.hornets[i];
        }
        resizedHornets[this.swarmSize] = hornet;
        this.swarmSize++;
        this.hornets = resizedHornets;
    }

    /**
     * Remove a hornet from the array
     * @param hornet hornet to remove
     * @return if the passed hornet has been removed
     */
    public boolean removeHornet(Hornet hornet){
        for(int i = 0; i < this.swarmSize; i++)
        {
            if(this.hornets[i] == hornet)
            {
                Hornet[] resizedHornets = new Hornet[this.swarmSize - 1];

                for (int j = 0; j < i; j++){
                    resizedHornets[j] = this.hornets[j];
                }

                for (int k = i; k < this.swarmSize - 1; k++){
                    resizedHornets[k] = this.hornets[k+1];
                }

                this.swarmSize--;
                this.hornets = resizedHornets;
                return true;
            }
        }
        return false;
    }
}
