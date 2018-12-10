package proyecto3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NumberFileManager {
    /**
     * Lee de un fichero los valores de los números que usaremos.
     */
    public ArrayList<Integer> readFromFile(String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        String aux;
        ArrayList<Integer> arr = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((aux = br.readLine()) != null) {
                arr.add(Integer.parseInt(aux));
            }
            br.close();
        }
        return arr;
    }
    
    // Escribe en un fichero de resultados el tiempo de ejecución de un fichero
    public static void writeResult(String path, String technique, double t) {
        try(FileWriter fw = new FileWriter("JAVA_RESULTS.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.println("Test " + path + " " + technique + " Process time: " + t/1000);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Limpia el fichero de resultados
    public static void cleanResults() {
        try(FileWriter fw = new FileWriter("JAVA_RESULTS.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {
            out.print("");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
