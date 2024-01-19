import java.io.*;
import java.util.*;

public class Main {
    public int hashfunc(int k, int b) {
        return k%b;
    }
    public boolean put(int k, int b, int slot, int[][] h) {
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
    public void delete(int k, int b, int slot, int[][] h) {
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
    public int get(int k, int b, int slot, int[][] h) {
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
    public int rehashfunc(int k, int b, int count) {
        return (k%b + count*prime(b));
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
    public static void main(String[] args) throws IOException{
        Main hash = new Main();
        boolean load;
        int bucketnum = 5;
        int slot = 3;
        int bucket, bucketnew;
        int[][] hashtable = new int[bucketnum][slot];
        for (int[] row: hashtable)
            Arrays.fill(row, -1);
        File keys = new File("Keys");
        Scanner fr = new Scanner(keys);
        while (fr.hasNextLine()) {
            String data = fr.nextLine();
            System.out.println(data);
            int dataint = Integer.parseInt(data);
            bucket = hash.hashfunc(dataint, bucketnum);
            load = hash.put(dataint, bucket, slot, hashtable);
            int c=1;
            while (!load) {
                bucket = hash.rehashfunc(dataint, bucketnum, c);
                load = hash.put(dataint, bucket, slot, hashtable);
                c++;
            }
        }
        System.out.println(hashtable[4][0] + "\n" + hashtable[4][1]);

        fr.close();
    }
}