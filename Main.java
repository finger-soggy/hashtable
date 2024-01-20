import java.io.*;
import java.util.*;
public class Main {
    public int hashfunc(int k, int b) {
        return k%b;
    }                                                                //Hash Function, h(x)
    public boolean put(int k, int b, int slot, int[][] h) {         //insert key into hashtable function
        boolean loaded = false;
        int s = 0;
        while (!loaded && s<slot) if (h[b][s] == -1 && h[b][s] != k) {
            h[b][s] = k;
            loaded = true;
        } else s++;
        if (!loaded) {
            System.out.println("The slot is full");
            System.out.println("Collision handling ongoing...");
        }
        return loaded;
    }
    public int get(int k, int b, int slot, int[][] h) {             //search/retrieve function
        int s;
        int buc;
        System.out.println("Search for Key: " + k);
        for (buc=0; buc<b; buc++) {
            for (s=0; s<slot; s++) {
                if (h[buc][s] == k) {
                    System.out.println("Key found!");
                    System.out.println("Bucket: " + buc + "\n" + "Slot: " + s);
                    return h[buc][s];
                }
            }
        }
        System.out.println("Key not found");
        return -1;
    }
    public int rehashfunc(int k, int b, int count) {            //rehashing function
        return (k%b + count*(prime(b)-(k%prime(b))));
    }
    public int prime(int b) {                                   //Finding prime number for rehashing
        int N = b-1;
        int mod;
        boolean primefound = false;
        while (!primefound) {
            for (int n=2; n<N; n++) {
                mod = N % n;
                if (mod == 0) {
                    primefound = false;
                    break;
                } else {
                    primefound = true;
                }
            }
            if (!primefound)
                N--;
        }
        return N;
    }
    public int quadraticprobing(int k, int b, int count) {       //Quadratic Probing hash function
        return (hashfunc(k, b) + count^2);
    }
    public int linearprobing(int k, int b, int count) {
        return (hashfunc(k, b)+count);
    }
    public int randomprobing(int k, int b) {
        Random rand = new Random();
        return (hashfunc(k, b) + rand.nextInt(1, b-1));
    }
    public static void hashprint(int[][] h, int b, int s) {     //print the hash table
        for (int i=0; i<b; i++) {
            for (int j=0; j<s; j++) {
                System.out.printf("%d  ",h[i][j]);
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) throws IOException{
        Main hash = new Main();                                 //hash ADT object
        boolean load;                                           //Used for rehashing purpose, false = rehash
        int bucketnum = 10;                                     //bucket size
        int slot = 1;                                           //slot size
        int bucket, probing_method, search;                     //key's bucket + chosen collision handling method
        int[][] hashtable = new int[bucketnum][slot];           //hashtable 2D array
        for (int[] row: hashtable)                              //Fill 2D hashtable array with -1, -1 = NULL
            Arrays.fill(row, -1);
        File keys = new File("Keys");                  //Create a file for our text file(data file)
        Scanner fr = new Scanner(keys);                         //Create scanner to scan our text file
        Scanner probechoose = new Scanner(System.in);           //Choose collision handling method
        Scanner retrieve = new Scanner(System.in);              //user inputs to retrieve a key
        System.out.println("Choose the collision handling method:"); //1 for rehashing, 2 for quadratic probing, 3 for random probing, default is linear probing
        probing_method = probechoose.nextInt();
        long start_in = System.nanoTime();                       //Timing for inserting starts
        while (fr.hasNextLine()) {                               //When there is string in a line go through the loop
            String data = fr.nextLine();                         //Stored the text file's data in a string
            System.out.println(data);
            int dataint = Integer.parseInt(data);                //convert the string to integer
            bucket = hash.hashfunc(dataint, bucketnum);          //Find the bucket of said integer(key)
            load = hash.put(dataint, bucket, slot, hashtable);   //insert into hash table, return false if the slots of specific bucket is full
            int c=1;                                             //For rehashing
            switch (probing_method) {
                case 1 -> {
                    while (!load) {
                        bucket = hash.rehashfunc(dataint, bucketnum, c) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                        c++;
                    }
                }
                case 2 -> {
                    while (!load) {
                        bucket = hash.quadraticprobing(dataint, bucketnum, c) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                        c++;
                    }
                }
                case 3 -> {
                    while (!load) {
                        bucket = hash.randomprobing(dataint, bucketnum) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                    }
                }
                default -> {
                    while (!load) {
                        bucket = hash.linearprobing(dataint, bucketnum, c) % bucketnum;
                        load = hash.put(dataint, bucket, slot, hashtable);
                        c++;
                    }
                }
            }
        }
        long end_in = System.nanoTime();                                                    //Timing for inserting ends
        hashprint(hashtable, bucketnum, slot);
        System.out.println("Choose the keys you want to search: ");
        long start = System.nanoTime();                                                     //Timing for searching starts
        for (int size=0; size<(bucketnum*slot)-1; size++) {                                 //Searching for 10/100/1000/10000 keys
            search = retrieve.nextInt();
            hash.get(search, bucketnum, slot, hashtable);
        }
        long end = System.nanoTime();                                                       //Timing for searching ends
        System.out.println("Time take to insert 10 keys: " + (end_in - start_in));
        System.out.println("Time taken to search 10 keys: " + (end-start));
        probechoose.close();
        fr.close();
        retrieve.close();
    }
}