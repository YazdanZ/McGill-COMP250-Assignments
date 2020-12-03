package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DogShelter implements Iterable<Dog> {
    public DogNode root;

    public DogShelter ( Dog d ) {
        this.root = new DogNode ( d );
    }

    private DogShelter ( DogNode dNode ) {
        this.root = dNode;
    }


    // add a dog to the shelter
    public void shelter ( Dog d ) {
        if (root == null)
            root = new DogNode ( d );
        else
            root = root.shelter ( d );
    }

    // removes the dog who has been at the shelter the longest
    public Dog adopt () {
        if (root == null)
            return null;

        Dog d = root.d;
        root = root.adopt ( d );
        return d;
    }

    // overload adopt to remove from the shelter a specific dog
    public void adopt ( Dog d ) {
        if (root != null)
            root = root.adopt ( d );
    }


    // get the oldest dog in the shelter
    public Dog findOldest () {
        if (root == null)
            return null;

        return root.findOldest ();
    }

    // get the youngest dog in the shelter
    public Dog findYoungest () {
        if (root == null)
            return null;

        return root.findYoungest ();
    }

    // get dog with highest adoption priority with age within the range
    public Dog findDogToAdopt ( int minAge, int maxAge ) {
        return root.findDogToAdopt ( minAge, maxAge );
    }

    // Returns the expected vet cost the shelter has to incur in the next numDays days
    public double budgetVetExpenses ( int numDays ) {
        if (root == null)
            return 0;

        return root.budgetVetExpenses ( numDays );
    }

    // returns a list of list of Dogs. The dogs in the list at index 0 need to see the vet in the next week.
    // The dogs in the list at index i need to see the vet in i weeks.
    public ArrayList<ArrayList<Dog>> getVetSchedule () {
        if (root == null)
            return new ArrayList<ArrayList<Dog>> ();

        return root.getVetSchedule ();
    }


    public Iterator<Dog> iterator () {
        return new DogShelterIterator ();
    }


    public class DogNode {
        public Dog d;
        public DogNode younger;
        public DogNode older;
        public DogNode parent;

        public DogNode ( Dog d ) {
            this.d = d;
            this.younger = null;
            this.older = null;
            this.parent = null;
        }

        public DogNode shelter ( Dog d ) {
            DogNode newRoot;
            newRoot = this;

            // perform BST insertion
            if (d.getAge () < this.d.getAge ()) {

                if (this.younger == null) {
                    this.younger = new DogNode ( d );
                    this.younger.parent = this;
                } else this.younger = this.younger.shelter ( d );

                // Rotate if the max heap property doesn't hold
                if (this.younger != null && this.younger.d.getDaysAtTheShelter () > this.d.getDaysAtTheShelter ()) {
                    newRoot = this.rotateRight ();
                }
            } else {
                if (this.older == null) {
                    this.older = new DogNode ( d );
                    this.older.parent = this;
                } else this.older = this.older.shelter ( d );

                // Rotate if the max heap property doesn't hold
                if (this.older != null && this.older.d.getDaysAtTheShelter () > this.d.getDaysAtTheShelter ()) {
                    newRoot = this.rotateLeft ();
                }
            }

            return newRoot;
        }

        /**
         * P                 L
         * /  \   rotation    / \
         * L    R   ==>       A   P
         * / \                    / \
         * A   B                  B   R
         */
        private DogNode rotateRight () {
            DogNode L = this.younger;
            DogNode B = this.younger.older;

            L.older = this;
            L.parent = this.parent;

            this.younger = B;
            this.parent = L;
            if (B != null)
                B.parent = this;
            return L;
        }

        /**
         * P                 R
         * / \   rotation    / \
         * L   R   ==>       P   B
         * / \           / \
         * A   B         L   A
         */
        private DogNode rotateLeft () {
            DogNode R = this.older;
            DogNode A = this.older.younger;

            R.younger = this;
            R.parent = this.parent;

            this.parent = R;
            this.older = A;
            if (A != null)
                A.parent = this;

            return R;
        }

        public DogNode adopt ( Dog d ) {
            DogNode newRoot;
            newRoot = this;

            if (d == null)
                return this;
            if (d.getAge () < this.d.getAge ()) {
                if (this.younger != null)
                    this.younger = this.younger.adopt ( d );
            } else if (d.getAge () > this.d.getAge ()) {
                if (this.older != null)
                    this.older = this.older.adopt ( d );
            } else if (this.d == d) {
                if (this.younger == null && this.older == null)
                    newRoot = null;
                else if (this.younger != null && this.older != null) {
                    if (newRoot.younger.d.getDaysAtTheShelter () < newRoot.older.d.getDaysAtTheShelter ()) {
                        newRoot = newRoot.rotateLeft ();
                        newRoot.younger = newRoot.younger.adopt ( d );
                    } else {
                        newRoot = newRoot.rotateRight ();
                        newRoot.older = newRoot.older.adopt ( d );
                    }

                } else {
                    if (this.younger == null) {
                        this.older.parent = this.parent;
                        newRoot = this.older;
                    } else {
                        this.younger.parent = this.parent;
                        newRoot = this.younger;
                    }
                }
            }

            return newRoot;
        }

        public Dog findOldest () {
            if (this.older == null)
                return this.d;
            else return this.older.findOldest ();
        }

        public Dog findYoungest () {
            if (this.younger == null)
                return this.d;
            else return this.younger.findYoungest ();
        }

        public Dog findDogToAdopt ( int minAge, int maxAge ) {
            if (this.findYoungest ().getAge () > maxAge)
                return null;

            else if (this.findOldest ().getAge () < minAge)
                return null;

            else if (this.d.getAge () <= maxAge && this.d.getAge () >= minAge)
                return this.d;

            else if (this.d.getAge () > maxAge)
                return this.younger.findDogToAdopt ( minAge, maxAge );

            else if (this.d.getAge () < minAge)
                return this.older.findDogToAdopt ( minAge, maxAge );

            else return null;
        }

        public double budgetVetExpenses ( int numDays ) {
            double expenses = 0.0;
            DogShelter ds = new DogShelter ( root );
            for (Dog dog : ds) {
                if (dog.getDaysToNextVetAppointment () <= numDays)
                    expenses += dog.getExpectedVetCost ();
            }
            return expenses;
        }

        public ArrayList<ArrayList<Dog>> getVetSchedule () {
            ArrayList<ArrayList<Dog>> result = new ArrayList<> ();
            DogShelter ds = new DogShelter ( root );

            for (Dog dog : ds) {
                int index = dog.getDaysToNextVetAppointment () / 7;
                ArrayList<Dog> week;

                try {
                    week = result.get ( index );
                } catch (IndexOutOfBoundsException e) {
                    while (result.size () < index) {
                        result.add ( new ArrayList<> () );
                    }
                    week = null;
                }

                if (week == null){
                    week = new ArrayList<> ();
                    week.add ( dog );
                    result.add ( week );
                }
                else {
                    week.add ( dog );
                }
            }
            return result;
        }

        public String toString () {
            String result = this.d.toString () + "\n";
            if (this.younger != null) {
                result += "younger than " + this.d.toString () + " :\n";
                result += this.younger.toString ();
            }
            if (this.older != null) {
                result += "older than " + this.d.toString () + " :\n";
                result += this.older.toString ();
            }
			/*if (this.parent != null) {
				result += "parent of " + this.d.toString() + " :\n";
				result += this.parent.d.toString() +"\n";
			}*/
            return result;
        }

    }


    private class DogShelterIterator implements Iterator<Dog> {
        private DogNode nextDog;

        private DogShelterIterator () {
            if (root == null)
                return;
            nextDog = root;
            // get to the smallest node
            while (nextDog.younger != null)
                nextDog = nextDog.younger;
        }

        public Dog next () {
            if (nextDog == null)
                throw new NoSuchElementException ();

            DogNode curr = nextDog;
            if (nextDog.older != null) {
                nextDog = nextDog.older;
                while (nextDog.younger != null)
                    nextDog = nextDog.younger;
                return curr.d;
            }

            while (true) {
                if (nextDog.parent == null) {
                    nextDog = null;
                    return curr.d;
                }

                if (nextDog.parent.younger == nextDog) {
                    nextDog = nextDog.parent;
                    return curr.d;
                }
                nextDog = nextDog.parent;
            }

        }

        public boolean hasNext () {
            return nextDog != null;
        }

    }

}
