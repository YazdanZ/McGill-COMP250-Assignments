package assignment3;

public class MyTester {
    public static void main ( String[] args ) {
        testBinaryInsertion ();
    }

    private static void testBinaryInsertion () {
        Dog R = new Dog ( "Rex", 8, 1, 1, 1.0 );
        Dog S = new Dog ( "Stella", 5, 1, 1, 1.0 );
        Dog P = new Dog ( "Poldo", 10, 1, 1, 1.0 );

        DogShelter ds = new DogShelter ( S );
        System.out.println ( "Testing BST insertion" );

        //ds.shelter ( S );
        ds.shelter ( P );
        ds.shelter ( R );

        if (ds.root.d != S || ds.root.older.d != P || ds.root.older.younger.d != R)
            System.out.println ( "Error in test binary insertion" );

        ds = new DogShelter ( P );
        ds.shelter ( S );
        ds.shelter ( R );

        if (ds.root.d != P || ds.root.younger.d != S || ds.root.younger.older.d != R)
            System.out.println ( "Error in test binary insertion" );
    }
}
