package lessons;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collections {
    public static void main(String[] args) {
        //Список на основе массива
        //эффективно - добавление в конце и обращение по индексу
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(3);
        ints.add(12);
        ints.add(4);
        ints.add(5);
        ints.add(9);
        ints.add(34);
        System.out.println(ints);
        ints.subList(3, ints.size()).clear();
        System.out.println(ints);


        //Двусвязный сисок
        //Эффективно - удаление или вставка в середину массива
        List<Integer> lin = new LinkedList<>();
        ints.add(45);
        ints.add(435);
        ints.add(4543);
        ints.add(45231);
        ints.add(4115);
        ints.add(451111);


        //Очерердь
        Queue<Integer> intsQueue = new LinkedList<>();
        intsQueue.add(1);
        intsQueue.add(67);
        intsQueue.add(12);
        intsQueue.add(7);
        intsQueue.add(3);
        intsQueue.add(5);
        intsQueue.add(8);
        System.out.println(intsQueue);
        System.out.println(intsQueue.peek());       //peek() - подсмотреть элемент без удаления
        System.out.println(intsQueue.poll());       //pool() - достать элемент и удалить его
        System.out.println(intsQueue);
        Integer elem;

        //PriorityQueue - очередь с приоритетом
        //автоматическя сортировка, т к PriorityQueue методом poll() удаляет наименьший элемент и возращает его
        Queue<Integer> intsPr = new PriorityQueue<>(intsQueue);

        while ((elem = intsPr.poll()) != null) {
            ints.add(elem);
        }

        System.out.println(ints);


        //Deque - стек и очередь в одном флаконе
        Deque<Integer> deqInts = new ArrayDeque<>();        //ArrayDeque - реализация интерфейса Deque
        deqInts.offerFirst(1);      //функции вставки и удаления в начале и конце дека
        deqInts.offerLast(456);
        deqInts.offerFirst(78);
        deqInts.offerLast(9989);
        deqInts.pollFirst();
        deqInts.pollLast();
        for (Integer el : deqInts) {
            System.out.println("My element in ArrayDeque " + el);
        }


        //порядок непредсказуем, элементы уникальны
        Set<Pair<String, Integer>> lamps = new HashSet<>();
        lamps.add(new Pair<String, Integer>("a", 1));
        lamps.add(new Pair<String, Integer>("b", 2));
        lamps.add(new Pair<String, Integer>("c", 3));
        lamps.add(new Pair<String, Integer>("d", 4));
        lamps.add(new Pair<String, Integer>("e", 5));
        lamps.add(new Pair<String, Integer>("f", 6));
        //Хранит объекты в том порядке, в котором добавляли их
        Set<String> stringsSet = new LinkedHashSet<>();
        stringsSet.add("Sanya");
        stringsSet.add("Tyoma");
        stringsSet.add("Vika");

        //TreeSet - двоичное дерево поиска из объектов, элементы должны быть comparable или коллекции нужно передать Comparator
        Set<Pair<Float, Integer>> tree = new TreeSet<>();


        // Трюк для удаления одинаковых объектов
        List<String> test = new ArrayList<>();
        test.add("aaa");
        test.add("aaa");
        test.add("bbb");
        test.add("ccc");
        test.add("ddd");
        test.add("aaa");
        System.out.println(test);
        Set<String> testSet = new LinkedHashSet<>(test);

        List<String> testNew = new ArrayList<>(testSet);
        System.out.println(testNew);
        //получение массива из коллекции
        String[] abc = testNew.toArray(new String[testNew.size()]);


        /*
        Map
        loadFactor - оэффициент загрузки, по умолчанию 0,75
         */


        Map<Integer, String> database = new HashMap<>();
        database.put(283333, "Назаренко Арсений Евгеньевич");
        database.put(283343, "Назаренко Артем Евгеньевич");
        database.put(283356, "Житкевич Виктория Владимировна");
        database.put(238533, "Скляренко Степан Олегович");
        database.put(289033, "Гурин Александр Николаевич");
        database.put(283876, "Замков Николай Александрович");
        System.out.println(database.get(283876));


        System.out.println(database.put(289033, "Макарьев Евгений Анатольевич"));


        Set<Integer> databaseKeys = new HashSet<>(database.keySet());

        List<String> databaseValues = new ArrayList<>(database.values());

        System.out.println(databaseValues);
        System.out.println(databaseKeys);


        for (Map.Entry<Integer, String> entry : database.entrySet()) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }

        /*Пример того, как коллекция работает с коллизиями, когда хэш код получился равным,
        для этого специально создадим новый класс, equals переопределим, а hashcode нет
        */

        class Ob {
            private String a;
            private int b;

            public Ob(String a, int b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Ob ob = (Ob) o;
                return b == ob.b &&
                        Objects.equals(a, ob.a);
            }

            @Override
            public int hashCode() {
                return 6 + b;
            }
        }

        Map<Ob, Integer> err = new HashMap<>();
        err.put(new Ob("qwr", 34), 24);
        err.put(new Ob("ert", 34), 67);
        err.put(new Ob("kkhkh", 34), 899);


        System.out.println(err);
        System.out.println(err.keySet());
        System.out.println(err.values());

        for (Map.Entry<Ob, Integer> entry : err.entrySet()) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }

        /*в итоге по хэшкоду ключа все элементы попали в одну ячеку, и образовали из себя свзяный список,
         теперь поиск элемента по ключу осуществляется с помощью прохода по связному списку и применения equals
         , ведь он у нас пееопределен верно. Так решается проблема одинакового хэшкода для двух разных объектов

         К тому же, если мы вставляли в мапу значение по ключу key1, а затем хотим получить значение по ключу key2, при условии что key2.equals(key1),
         мы не получим объект в силу неправильного hashcode, т к мы попадем в другую корзину, где объектов нет совсем
        */



        /*
        Пример того как будет работать Map в условиях того, что hashcode переопределен верно, а equals нет
         */
        class Bad {
            private String a;
            private int b;

            public Bad(String a, int b) {
                this.a = a;
                this.b = b;
            }


            @Override
            public int hashCode() {
                return Objects.hash(a, b);
            }
        }


        Map<Bad, Integer> example = new HashMap<>();
        example.put(new Bad("qwe", 12), 12);
        example.put(new Bad("1212e", 1672), 12);
        example.put(new Bad("sepepe", 100002), 12);    // добавим эелемент с с одним и тем же ключом, но с разным значением
        example.put(new Bad("sepepe", 100002), 13);
        /*
        После вывода можно заметить, что из за плохого equals по одному и тому же ключу(хэшкод однаков) пара приходит в ту же яейку, где уже был объект с таким ключом
        и вместо привычной перезаписи создает связный список, таким образом в последствии нельзя по ключу найти наше значение, см. ниже
         */
        System.out.println(example.values());
        System.out.println(example.keySet());
        System.out.println("=================================");
        for (Map.Entry<Bad, Integer> element : example.entrySet()) {
            System.out.println(element.getKey() + " ------>>>> " + element.getValue());
        }

        //попробуем достать элемент по ключу
        System.out.println(example.get(new Bad("qwe", 12)));
        /*
         вывод null, тк не смотря несмотря на то, что по ключу мы обратились в нужную ячейку(из за хорошего хэш кода), пар "ключ + значение" там много,
         поэтому надо проходить по всемя всязному списку таких пар и , сравнивая на equals, находить из всех нужную нам пару
         но в нашем случае проверка на equals ни разу не выдаст истину!!!
         */




        /*
        Внутри хэшсет лежит хэш мап, теперь значение - это ключ мапы внутри, а значение мапы - константа, остальное схоже
        с неправильным хэшкодом опять же получается связный список
         */
        Set<Ob> obs = new HashSet<>();
        obs.add(new Ob("wer", 34));
        obs.add(new Ob("rty", 34));
        obs.add(new Ob("dfg", 34));
        obs.add(new Ob("ghj", 34));
        System.out.println(obs);
        Ob ob11 = new Ob("fhfgh", 34);
        obs.add(ob11);
        System.out.println(obs);
        obs.remove(ob11);
        System.out.println(obs);


        //Авоматическая упаковка примитивный типов в обертки распаковка обратно
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);        //автоупаковка - при добавление в коллекцию сразу int в Integer
        }
        //for (int i = 0; i < 10; i++) {
        //    list.remove(i);     //автораспаковка - значит теперь мы удаляем объекты в коллекцию не по самому объекту, а по индексу
        //может возникнуть ошибка!!!
        //}


        //При копировании объектов надо быть аккуртаным
        Map<Integer, ArrayList<String>> maps = new HashMap<>();
        //java.util.Arrays$ArrayList cannot be cast to java.util.ArrayList, посмотри, какой класс возвращает этот метод
        //ArrayList<String> strs = (ArrayList<String> ) Arrays.asList("Awdawd", "awdawd" , "awdawd");
        ArrayList<String> strs2 = new ArrayList<>();
        ArrayList<String> strs = new ArrayList<>();
        strs2.add("2222eeee");
        strs2.add("5hhhheeee");
        strs2.add("09999999gggg");
        strs.add("qweqwe");
        strs.add("yyyyrrr");
        strs.add("ollolol");
        maps.put(1, strs);
        maps.put(3, strs2);
        //Создадим новую мапу, и с помощью конструктора закинем туда наш maps
        Map<Integer, List<String>> maps2 = new TreeMap<>(maps);
        //изменили копию
        maps2.get(1).add("TEST");
        //копия не глубокая, а по ссылке, поэтому исходная мапа изенилась
        System.out.println(maps.values());
        System.out.println(maps2);
        String a = "awdawd awdawd awdawd";

        Vector<Integer> vector = new Vector<>();

        vector.addAll(Stream.iterate(1, n -> n + 1).limit(5000).collect(Collectors.toList()));
        //vector.forEach(i -> {if (i % 2 == 1 && i % 5 == 0) System.out.println(i);});


        System.out.println("    asdasda            sdads    ".trim() + "YYY");
        System.out.println("    asdasd             asdads    " + "YYY");



    }

}
