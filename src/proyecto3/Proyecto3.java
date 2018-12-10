package proyecto3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static proyecto3.NumberFileManager.*;

public class Proyecto3 {
    
    private static ArrayList<Integer> arr; // ArrayList de valores
    private static Integer[] arrAux;
    
    private static boolean mem = false; // Flag de selección de técnica
    private static boolean tab = false; // Flag de selección de técnica
    private static boolean fi = false; // Flag de muestra de entrada
    private static boolean fr = false; // Flag de muestra de resultados
    private static boolean ft = false; // Flag de muestra de tiempo de computación
    private static boolean c = false;  // Flag de limpieza de fichero de resultados
    
    public Proyecto3(String path) {
        try {
            arr = new NumberFileManager().readFromFile(path);
        } catch (IOException ex) {
            System.out.println("Can't read data from file: " + path);
            arr = new ArrayList<>();
        }
    }
   

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Syntax: java -jar Coprime.jar [-mem | -tab | -fi | -fr | -ft | -c] input_filename");
            System.out.println("(You can input more than one filename at once)");
            System.out.println("-mem: Use memoization technique");
            System.out.println("-tab: Use tabulation technique");
            System.out.println("-fi: Show read numbers from file(s)");
            System.out.println("-fr: Show results (the sets of numbers in which all are coprime)");
            System.out.println("-ft: Show time spent computing the results");
            System.out.println("-c:  Clear the results log file before writing new results");
            return;
        }
        ArrayList<String> paths = new ArrayList();
        for (String arg : args) {
            switch (arg) {
                
                case "-mem":
                    mem = true;
                    continue;
                case "-tab":
                    tab = true;
                    continue;
                case "-fi":
                    fi = true;
                    continue;
                case "-fr":
                    fr = true;
                    continue;
                case "-ft":
                    ft = true;
                    continue;
                case "-c":
                    c = true;
                    continue;
            }
            paths.add(arg);
        }
        
        if (c) cleanResults();
        
        // Procesamos cada uno de los ficheros introducidos por parámetro
        for (String string : paths) {
            if (paths.size() > 1) {
                System.out.println("Reading from file: " + string);
            }
            Proyecto3 p = new Proyecto3(string);
            if (fi) p.showInput(); // Mostramos los valores leídos del fichero

            double t = 0; 
            arrAux = new Integer[arr.size()];
            arrAux = (Integer[]) arr.toArray(arrAux);

            t = System.currentTimeMillis();
            if (mem) {
                Memoization(arrAux);
                t = System.currentTimeMillis() - t;
               writeResult(string, "Memoization", t);
                if (ft) System.out.println("Process time:\t" + t / 1000);
            }
            
            t = System.currentTimeMillis();
            if (tab) {
                Tabulation(arrAux);
                t = System.currentTimeMillis() - t;
                writeResult(string, "Tabulation", t);
                if (ft) System.out.println("Process time:\t" + t / 1000);
            }
        }
    }
    
    public void showInput() {
        System.out.println("*** Input");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(i + " => " + arr.get(i));
        }
        System.out.println("-------------------------------");
    }
    
    private static int Memoization(Integer[] arr) {
            HashMap<Integer, Integer> dic = new HashMap<>();
            HashMap<Integer, Integer> values = Lis(arr, dic, arr.length - 1);
            int mx = 1;

           for(Map.Entry<Integer, Integer> i : values.entrySet()) {
                if (mx < i.getValue()) {
                    mx = i.getValue();
                }
            }
            if (fr) System.out.println("Numero de cambios requeridos: " + (arr.length - mx));
            return (arr.length - mx);
        }
          
        private static HashMap<Integer,Integer> Lis(Integer[] arr, HashMap<Integer,Integer> dp, int n) {
            if (n == 0) {
                dp.put(n, 1);
            } else {
                dp = Lis(arr, dp, n - 1);
                dp.put(n,1);
                for (int i = 0; i < n; i ++) {
                    if (arr[n] > arr[i] && dp.get(n) < dp.get(i) + 1) {
                        dp.put(n, dp.get(i)+1);
                    }
                }
            }
            return dp;
        }


        private static int Tabulation(Integer[] arr) {
            int n = arr.length;
            int[] lis = new int[n];
            lis[0] = 1;

            for (int i = 1; i < n; i ++) {
                lis[i] = 1;
                for (int j = 0; j < i; j ++) {
                    if (arr[i] > arr[j] && lis[i] < lis[j] + 1) {
                        lis[i] = lis[j] + 1;
                    }
                }
            }
            int maximum = 0;
            for (int i = 0; i < n; i ++) {
                if (maximum < lis[i]) {
                    maximum = lis[i];
                }
            }
            if (fr) System.out.println("Numero de cambios requeridos: " + (n - maximum));
            return (n - maximum);      
        }
    
}
