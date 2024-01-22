import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException{
        HashTable hash = new HashTable();                       //hash table ADT object
        boolean load;                                           //Used for collision detection purpose, false = collision handling starts
        int bucketnum = 100;                                    //bucket size
        int slot = 100;                                         //slot size
        int datanum=0;
        int bucket, probing_method;                             //key's bucket + chosen collision handling method
        int[][] hashtable = new int[bucketnum][slot];           //hashtable 2D array
        for (int[] row: hashtable)                              //Fill 2D hashtable array with -1, -1 = NULL
            Arrays.fill(row, -1);
        File keys = new File("keyset4");                  //Create a file for our text file(data file)
        Scanner fr = new Scanner(keys);                         //Create scanner to scan our text file
        Scanner probechoose = new Scanner(System.in);           //Choose collision handling method
        Scanner retrieve = new Scanner(System.in);              //user inputs to retrieve a key
        System.out.println("Choose the collision handling method:"); //1 for rehashing, 2 for quadratic probing, 3 for random probing, default is linear probing
        probing_method = probechoose.nextInt();
        /* START INSERTING PROCESS
        -------------------------------------------------------------------------------------------------------------------*/
        long start_in = System.nanoTime();                       //Timing for inserting starts
        while (fr.hasNextLine()) {                               //When there is string in a line go through the loop
            datanum++;
            String data = fr.nextLine();                         //Stored the text file's data in a string
            System.out.println(data);
            int dataint = Integer.parseInt(data);                //convert the string to integer
            bucket = hash.hashfunc(dataint, bucketnum);          //Find the bucket of said integer(key)
            load = hash.put(dataint, bucket, slot, hashtable);   //insert into hash table, return false if the slots of specific bucket is full
            int c=1;                                             //For rehashing
            switch (probing_method) {
                case 1 -> {
                    while (!load) {
                        if (c>100) {                                                        //Insertion limited to just 100 iteration of rehashing
                            System.out.println("Insertion failed");                         //Iteration exceeding 100 will fail to put the key into hash table
                            break;
                        }
                        System.out.println("Rehashing ongoing");
                        bucket = hash.rehashfunc(dataint, bucketnum, c) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                        c++;
                    }
                }
                case 2 -> {
                    while (!load) {
                        if (c>100) {                                                        //Insertion limited to just 100 iteration of quadratic probing
                            System.out.println("Insertion failed");                         //Iteration exceeding 100 will fail to put the key into hash table
                            break;
                        }
                        System.out.println("Quadratic probing ongoing");
                        bucket = hash.quadraticprobing(dataint, bucketnum, c) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                        c++;
                    }
                }
                case 3 -> {
                    while (!load) {
                        System.out.println("Random probing ongoing");
                        bucket = hash.randomprobing(dataint, bucketnum) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                    }
                }
                default -> {
                    while (!load) {
                        System.out.println("Linear probing ongoing");
                        bucket = hash.linearprobing(dataint, bucketnum, c) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                        c++;
                    }
                }
            }
        }
        long end_in = System.nanoTime();                                                    //Timing for inserting ends
        hash.hashprint(hashtable, bucketnum, slot);
        /*START SEARCHING ANALYSIS
        -----------------------------------------------------------------------------------------------------------------------*/
        int[] search = new int[datanum];                                                            //Array for keys to be searched
        System.out.println("Choose the keys you want to search: ");
        for (int numsearch=0; numsearch<datanum; numsearch++) {
            search[numsearch] = retrieve.nextInt();                                                 //User input keys to be searched
        }
        long start = System.nanoTime();                                                             //Timing for searching starts
        for (int size=0; size<datanum; size++) {                                                    //Searching for keys
            switch (probing_method) {
                case 1 -> hash.getrehash(search[size], bucketnum, slot, hashtable);
                case 2 -> hash.getquadratic(search[size], bucketnum, slot, hashtable);
                case 3 -> hash.getrandom(search[size], bucketnum, slot, hashtable);
                default -> hash.get(search[size], bucketnum, slot, hashtable);
            }
        }
        long end = System.nanoTime();                                                                //Timing for searching ends
        System.out.println("Time take to insert " + datanum + " keys: " +(end_in - start_in));       //Time taken in ns
        System.out.println("Time taken to search " + datanum + " keys: " + (end-start));             //Time taken in ns
        probechoose.close();
        fr.close();
        retrieve.close();
    }
}