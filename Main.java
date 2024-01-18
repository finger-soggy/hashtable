import java.io.*;
import java.util.*;
class Main{
    public int bucketnum;
    public int hashfunc(int key, int buc) {
        int res = key%buc;
        return res;
    }
    public static void main(String args[]) throws IOException {
        Main hashtable = new Main();
        Random rand = new Random();
        hashtable.bucketnum = 5;
        HashTable[] hash = new HashTable[hashtable.bucketnum];
        for (int i=0; i<5; i++){
            hash[i] = new HashTable();
        }
        for (int j=0; j<5; j++){
            for (int q=0; q<3; q++) {
                hash[j].slot[q] = -1;
            }
        }
        Main hashcode = new Main();
        File keyset = new File("Keyset1.txt");
        Scanner fread = new Scanner(keyset);
        while (fread.hasNextLine()) {
            String data = fread.nextLine();
            int datakey = Integer.parseInt(data);
            int result = hashcode.hashfunc(datakey, hashtable.bucketnum);
            hash[result].put(datakey);
        }

        int search;
        search = hash[1].get(3941);
        System.out.println(search);
        fread.close();

    }



}
