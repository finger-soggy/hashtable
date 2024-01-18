class HashTable {
    int[] slot = new int[3];
    public void put(int k) {
        int s=0;
        boolean loaded = false;
        while (!loaded) {
            if (slot[s] == -1) {
                slot[s] = k;
                loaded = true;
            }
            else {
                s++;
            }
        }
    }

    public void remove(int k){
        int s=0;
        boolean found = false;
        while (!found && s<3) {
            if (slot[s] == k) {
                slot[s] = -1;
                found = true;
            }
            else {
                s++;
            }
        }
        if (!found) {
            System.out.println("The key you want to remove is not in the hash table");
        }
    }

    public int get(int k) {
        int s=0;
        boolean found = false;
        while (!found && s<3) {
            if (slot[s] == k) {
                found = true;
                System.out.println("Key found");
                return slot[s];
            }
            else {
                s++;
            }
        }
        System.out.println("The key does not exist");
        return 0;
    }

}
