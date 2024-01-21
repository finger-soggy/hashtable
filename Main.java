import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        Random rand = new Random();
        FileWriter fw = new FileWriter("Keysgenerator");
        for (int i=0; i<10; i++) {
            int dataint = rand.nextInt(0, 9999);
            String data = Integer.toString(dataint);
            fw.write(data + "\n");
        }
        fw.close();
    }
}