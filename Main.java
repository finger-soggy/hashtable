import java.io.*;
import java.util.*;
import java.time.*;
public class Main {
    //Hash Function, h(x)
    public int hashfunc(int k, int b) {
        return k%b;
    }
    public boolean put(int k, int b, int slot, int[][] h) { //insert function
        boolean loaded = false;
        int s = 0;
        while (!loaded && s<slot) if (h[b][s] == -1) {
            h[b][s] = k;
            loaded = true;
        } else s++;
        if (!loaded) {
            System.out.println("The slot is full");
            System.out.println("Rehashing ongoing...");
        }
        return loaded;
    }
    public void delete(int k, int b, int slot, int[][] h) { //delete function
        boolean deleted = false;
        int s = 0;
        while (!deleted && s<slot) {
            if (h[b][s] == k) {
                h[b][s] = -1;
                deleted = true;
                System.out.println("KEY has been deleted");
            }
            else {
                s++;
            }
        }
        if (!deleted)
            System.out.println("KEY not found");
    }
    public int get(int k, int b, int slot, int[][] h) { //search/retrieve function
        boolean found = false;
        int s=0;
        while (s<slot) {
            if (h[b][s] == k) {
                System.out.println("KEY found");
                return h[b][s];
            }
            else {
                s++;
            }
        }
        if (!found)
            System.out.println("Key not found");
        return -1;
    }
    public int rehashfunc(int k, int b, int count) { //rehashing function
        return (k%b + count*(prime(b)-(k%prime(b))));
    }
    public int prime(int b) {
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
    public static void hashprint(int[][] h, int b, int s) { //print the hash table
        for (int i=0; i<b; i++) {
            for (int j=0; j<s; j++) {
                System.out.printf("%d  ",h[i][j]);
            }
            System.out.printf("\n");
        }
    }
    public static void main(String[] args) throws IOException{
        Main hash = new Main(); //object
        boolean load;                                           //Used for rehashing purpose, false = rehash
        int bucketnum = 5;                                      //bucket size
        int slot = 3;                                           //slot size
        int bucket;                                             //key's bucket(use with rehashfunc())
        int[][] hashtable = new int[bucketnum][slot];           //hashtable 2D array
        for (int[] row: hashtable)                              //Fill 2D hashtable array with -1, -1 = NULL
            Arrays.fill(row, -1);
        File keys = new File("Keys");                   //Create a file for our text file(data file)
        Scanner fr = new Scanner(keys);                          //Create scanner to scan our text file
        while (fr.hasNextLine()) {                               //When there is string in a line go through the loop
            String data = fr.nextLine();                         //Stored the text file's data in a string
            System.out.println(data);
            int dataint = Integer.parseInt(data);                //convert the string to integer
            bucket = hash.hashfunc(dataint, bucketnum);          //Find the bucket of said integer(key)
            load = hash.put(dataint, bucket, slot, hashtable);   //insert into hash table, return false if the slots of specific bucket is full
            int c=1;                                             //For rehashing
            while (!load) {
                bucket = hash.rehashfunc(dataint, bucketnum, c) % bucketnum;
                load = hash.put(dataint, bucket, slot, hashtable);
                c++;
            }
        }
        hashprint(hashtable, bucketnum, slot);
        fr.close();
    }
}