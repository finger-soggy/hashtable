import java.io.*;
import java.util.*;

public class Main{
    public static void main(String args[]) {
        HashTable[] hash = new HashTable[100];
        for (int i=0; i<100; i++){
            hash[i] = new HashTable();
        }
        Main hashcode = new Main();
        int key=1314;
        int result;
        result = hashcode.hashfunc(key, 100);
        hash[result].put(key);
        System.out.println(hash[result].slot[0]);
        System.out.println(result);
        key = 1514;
        hash[result].remove(key);
        hash[result].get(1414);

    }

    public int hashfunc(int key, int buc) {
        int res = key%buc;
        return res;
    }

}
