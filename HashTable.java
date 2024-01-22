import java.util.Random;
public class HashTable {
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
        return loaded;
    }
    public int get(int k, int b, int slot, int[][] h) {             //SEARCH USING LINEAR PROBING
        int s;
        int buc;
        for (buc=0; buc<b; buc++) {
            for (s=0; s<slot; s++) {
                if (h[buc][s] == -1)
                    break;
                else if (h[buc][s] == k) {
                    System.out.println("Key found: " + h[buc][s]);
                    return h[buc][s];
                }
            }
        }
        System.out.println("Key not found");
        return -1;
    }
    public int getrehash(int k, int b, int slot, int[][] h) {                       //SEARCH USING REHASHING
        int buc = hashfunc(k, b);
        int initial = buc;
        int s, c;
        boolean firstiter = true;
        c = 0;
        while((buc!=initial) || firstiter ) {
            for (s=0; s<slot; s++) {
                if (h[buc][s] == -1)
                    break;
                else if (h[buc][s] == k) {
                    System.out.println("Key found: " + h[buc][s]);
                    return h[buc][s];
                }
            }
            c++;
            buc = (rehashfunc(k, b, c))%b;
            firstiter = false;
        }
       System.out.println("Key not found");
       return -1;
    }
    public int getquadratic(int k, int b, int slot, int[][] h) {                //SEARCH USING QUADRATIC PROBING
        int buc = hashfunc(k ,b);
        int initial = buc;
        int s;
        int c=0;
        boolean firstiter = true;
        while (firstiter || c<100) {
            for (s=0; s<slot; s++) {
                if (h[buc][s] == -1)
                    break;
                else if (h[buc][s] == k) {
                    System.out.println("Key found: " + h[buc][s]);
                    return h[buc][s];
                }
            }
            c++;
            buc = (initial + (c*c)) % b;
            firstiter = false;
        }
        System.out.println("Key not found");
        return -1;
    }
    public int getrandom(int k, int b, int slot, int[][] h) {                   //SEARCH USING RANDOM PROBING
        int buc;
        int s,c;
        boolean[] visited = new boolean[b];
        for (int v=0; v<b; v++)
            visited[v] = false;
        buc = hashfunc(k,b);
        for (c=0; c<b; c++) {
            visited[buc] = true;
            for (s=0; s<slot; s++) {
                if (h[buc][s] == -1)
                    break;
                else if (h[buc][s] == k) {
                    System.out.println("Key found: " + h[buc][s]);
                    return h[buc][s];
                }
            }
            if (c<b-1) {
                do {
                    buc = (randomprobing(k, b)) % b;
                } while (visited[buc]);
            }
        }

        System.out.println("Key not found");
        return -1;
    }
    public int rehashfunc(int k, int b, int count) {            //rehashing function
        return (k%b + count*(prime(b)-(k%prime(b))));
    }
    public int prime(int b) {                                   //Finding prime number for rehashing (Prime number is the closest to bucket size but
        int N = b-1;                                            //Smaller than bucket size
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
        return (hashfunc(k, b) + count*count);
    }
    public int linearprobing(int k, int b, int count) {          //Linear probing hash function
        return (hashfunc(k, b)+count);
    }
    public int randomprobing(int k, int b) {                     //Random probing hash function
        Random rand = new Random();
        return (hashfunc(k, b) + rand.nextInt(1, b));
    }
    public void hashprint(int[][] h, int b, int s) {     //print the hash table
        for (int i=0; i<b; i++) {
            for (int j=0; j<s; j++) {
                System.out.printf("%d  ",h[i][j]);
            }
            System.out.println("");
        }
    }
}
