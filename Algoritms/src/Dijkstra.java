import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Dijkstra {

    public static void main(String[] args) {
        System.out.print("Ввведите количество вершин графа: ");
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        System.out.print("Введите вершину, от которой считать: ");
        int start = sc.nextInt();
        /*
        nextInt() читает лишь число, символ переноса строки, которые вводится при ENTER он
         пропускает, для решения еще раз вызовем nextLine() и забудем об этом переводе
         */
        sc.nextLine();
        //Scanner sc2 = new Scanner(System.in);
        Map<Integer, ArrayList<Pair<Integer, Integer>>> data = new TreeMap<>();
        for (int i = 1; i <= number; i++) {
            System.out.print(i + ": ");
            String arg = sc.nextLine();

            ArrayList<Pair<Integer,Integer>> tmp = new ArrayList<>();
            if (arg.equals("")) {
                data.put(i, tmp);
                continue;
            }
            String delimeter = " ";
            String[] subStr = arg.split(delimeter);

            for(int j = 0; j < subStr.length; j++) {
                Pair<Integer, Integer> res = new Pair<>(Integer.valueOf(
                        subStr[j].split(",")[0]),
                        Integer.valueOf(subStr[j].split(",")[1]));
                tmp.add(res);
            }
            data.put(i, tmp);
        }
        sc.close();
        //sc2.close();

        createCashPairs(data);
        System.out.println("Проверка введенных данных:(вершина -[вершина к которой отходит ребро=длина этого ребра, ...]) ");
        System.out.println(data);

        ArrayList<ArrayList<Pair<Integer, Integer>>> g = new ArrayList<>();
        g.add(null);
        g.addAll(data.values());
        int[] a = Dijkstra(g, start);
        for (int i = 0; i < a.length; i++) {
            System.out.println("До вершины " + (i+1) + " = " + a[i]);
        }



    }
    public static void createCashPairs(Map<Integer, ArrayList<Pair<Integer, Integer>>> before) {
        Map<Integer, ArrayList<Pair<Integer, Integer>>> after = new TreeMap<>(before);
        ArrayList<Integer> usedVerticals = new ArrayList<>();
        for (Map.Entry entry: before.entrySet()) {

            ArrayList<Pair<Integer, Integer>> arr = (ArrayList<Pair<Integer, Integer>>) entry.getValue();
            for (Pair<Integer, Integer> elem:arr) {
                if (elem.getValue() != 0 && !usedVerticals.contains(elem.getKey())) {
                    Pair<Integer, Integer> tmp = new Pair<Integer, Integer>((Integer) entry.getKey(), elem.getValue());
                    before.get(elem.getKey()).add(tmp);
                }

            }
            usedVerticals.add((Integer) entry.getKey());
        }

    }
    public static int[] Dijkstra(ArrayList<ArrayList<Pair<Integer, Integer>>> var, int start) throws IllegalArgumentException {
        if (start >= var.size() || start <= 0) {
            throw new IllegalArgumentException();
        }
        int n = var.size();
        int inf = Integer.MAX_VALUE/2;
        ArrayList<ArrayList<Pair<Integer, Integer>>> g = var; //матрица, которая для каждой вершины хранит массив пар: <иходящее вершина, длина пути до вершины>
        boolean[] used = new boolean[n];
        int[] dist = new int[n];
        for (int i = 0; i < dist.length; i++) {
            dist[i] = inf;
        }
        dist[start] = 0; //длина до вершины, взятой как начало должна быть нулем

        int minDist = 0;//
        int next = start; //

        while(minDist < inf) { //сморим каждой ребро, выходящее из нашей вершины и вычисляем путь от начала обода до вершин, в которые ведут ребра
            for(Pair<Integer, Integer> pair : g.get(next)) {
                int to = pair.getKey();
                int wt = pair.getValue();
                dist[to] = Math.min(dist[to], dist[next] + wt);
            }
            used[next] = true; // помечаем вершину как пройденную
            minDist = inf;
            for (int i = 1; i < n; i++) { //из всех непомечанных вершин, находим ту, путь до котрой самый наименьший от начала обхода, и продолжаем цикл для нее
                if (!used[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    next = i;
                }

            }

        }
        int mass[] = new int[n - 1];
        for (int i = 1; i < n; i++) {
            mass[i - 1] = dist[i];
        }

        return mass;
    }



}

