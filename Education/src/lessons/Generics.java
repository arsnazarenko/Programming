package lessons;
import java.util.*;
import java.util.Collections;

public class Generics {
    //параметризованный метод!!
    public static <T> List<T> nCopies(int n, T object) {
        List<T> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add(object);
        }
        return l;
    }

    //Пример 5
    //Попробуем теперь реализовать метод, выполняющий перестановку элементов списка в обратном порядке.
    public static void badReverse(List<?> list) {      //List<?> - список с неограниченным символом подстановки <?>(<? extends Object>)
        List<Object> tmp = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            //list.set(i, tmp.get(list.size()-i-1));        \\ записываем элементы
        }
    }
    //Возникла тшибка компиляции, т к согласно PECS <? extends...> - producer, а значит может только
    // продюсировать элементы, а мы пытаемся в него записывать элементы!


    //Паттерн WildCart Capture:
    public static void goodReverse(List<?> list) {
        rev(list);
    }
    private static <T> void rev(List<T> list) {
        List<T> tmp = new ArrayList<T>(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, tmp.get(list.size()-i-1));
        }
    }
    //теперь все компилится

    //Класс для двух методов ниже, примеры смотри в конце main()
    static class Box<T> {

        private T ob;

        public Box(T ob) {
            this.ob = ob;
        }

        public T getOb() {
            return ob;
        }

        public void setOb(T ob) {
            this.ob = ob;
        }
    }


    /*
    Метод, котрый на вход принимает коллекцию объектов класса Box, параметризованного ЧИСЛАМИ
    возвращает сумму значений единственного поля каждого класса Box
     */
    public static double sum(Collection<Box<? extends Number>> collection) {
        double sum = 0;
        for(Box<? extends Number> elem: collection) {
            sum += elem.getOb().doubleValue();
        }
        return sum;

    }


    /*
    Метод, принимающий на вход два аргумента: Коллекцию, параметризованную классом Box и объект типа T,
    которым параметризован Box. Метод с помощью сеттера устанавливает значение поля присваивая ему значение объекта в параетре.
    МЕТОД ПЛОХОЙ, ИВАН УСКОВ СКАЗАЛ НЕ ПОВТОРЯТЬ
     */
    public static<T> void add(Collection<Box<T>> col, T value) {
        for (Box<T> elem : col) {
            elem.setOb(value);
        }
    }
    public static void main(String[] args) {
        //Примеры подтипов
        Number number2 = Integer.valueOf(456);      // статический метод для создания экземпляра, тоже ОК!
        List<Integer> ints = new ArrayList<>();
        Collection<Integer> colls = ints;       //OK, т к List extends Collection, и параметризованы коллекции одним и тем же типом
        Iterable<Integer> itres = colls;        //ОК, т к Collection implements Iterable , и параметризованы коллекции одним и тем же типом


        //Массивы в джава ковариантны
        //создали массив строк
        String[] strings = new String[]{"ab", "fv", "awd", "rt"};
        //Привели этот массив строк к массиву Object, что позволяет добавить в этот массив любые объекты, наследуемые от Object
        Object[] ob = strings;//ОК, т к String extends Objects
        //из за коваринатности массивов получается возможным следующее:
        //Мы смогли пложить туда Integer
        //ob[0] = 456;
        /*
        на этапе компиляции сделать такое возможно, но в рантайме вылезет java.lang.ArrayStoreException,
         т к мы кладем в массив строк число
         */



        //Дженерики в Джава инвариантны
        Integer[] a = new Integer[]{1, 2, 4};
        List<Integer> ints2 = Arrays.asList(1, 2, 4, 5, 6);     //статический метод, который возвращает Лист
        //List<Number> num2 = ints2;       //Ошибка компиляции


        //Wildcards
        //Пример Ковариантнсти Дженериков
        List<Integer> ints3 = new ArrayList<>();
        List<? extends Number> nums3 = ints3;       //Компилится, т к List<Integer> - подтип List<? extends Numbers>

        //пример контрвариантности
        List<Number> nums4 = new ArrayList<>();
        List<? super Integer> ints4 = nums4;


        /*
        <? extends myClass> - символ подстановки с верхней границей, List c таким символом
        может содержать объекты, класс которых является myClass или наследуется от myClass




        <? super myClass> - символ подстановки с нижней границей, Коллекция параметризованная так
        может содержать объекты, класс которых myClass или супертип от myClass
         */

        //Особенности работы с wildCard


        /*
        PECS - PRODUCER EXTENDS CONSUMER SUPER


        1) Если мы объявили wildcard с EXTENDS, то это PRODUCER. Он только продюсирует,
        предоставляет элемент из контейнера, а сам ничего не принимает(Кроме исключения - null)


        2) Если мы объявили wildcard с SUPER, то это CONSUMER. Он только принимает,
        предоставить ничего не может.(Кроме исключения - Object)

         */

        //Пример1
        List<Integer> ints5 = new ArrayList<>();
        ints5.add(1);
        ints5.add(4);
        ints5.add(78);
        List<? extends Number> nums5 = ints5;
        //nums5.add(34);        //Ошибка компиляции, т к при объявлении wildcard <? extends Number> значения коллекции можно только читать!!!
        //добавить можно только null
        nums5.add(null);



        /*
        Пример 2
        Ошибка компиляции, т к Нельзя прочитать элемент их контейнера с wildcard <? super ...>,
        кроме Object

        */
        class Test{
            public <T> T getFirst(List<? super T> list) {
                //return list.get(0);
                return null;
            }

        }

        //Разберемся, почему так?

        //1.    <? extends Number>

        List<? extends Number> numbers;

        //Что можно присвоить?
        numbers = new ArrayList<Number>();
        numbers = new ArrayList<Integer>();
        numbers = new ArrayList<Long>();
        //и тд....

        class SecondTest{
            public void process(List<? extends Number> numbers) {
                //numbers.add(234L);

                /*
                Для какого типа объектов List это будет безопасно?
                 Ответ:
                 Только для List<Long> и List<Number>

                 Но компилятор знает что помимо вышеперчисленных листов сюда можно
                 подставить List<Float>(), List<Byte>() и тд, и с ними это несовместимо
                 Добавить можно только null
                 Именно поэтому Java запрещает работать с коллекцием как с Потребителем

                 */
            }
        }


        //2.    <? super Number>

        List<? super Number> numbers2;

        //Что можно присвоить?
        numbers2 = new ArrayList<Object>();
        numbers2 = new ArrayList<Number>();

        class ThirdTest {
            public void process(List<? super Number> numbers) {
                /*
                Следующие три, а также все extends Number удовлетворяют обоим
                условиям подстановки в аргумент метода, т к все эти типы extends Number
                и extends Object
                 */
                numbers.add(234L);
                numbers.add(100D);
                numbers.add(20.45345F);

                /*
                А вот следующее добавление противоречит подстановке ArrayList<Number>()
                в аргумент, т к new Object() нельзя добавить ArrayList<Number>();
                Именно поэтому сюда можно добавлять всех extends Number
                 */

                //numbers.add(new Object());


            }
        }

        //Пример3
        //Метод copy копирует элементы из списка типа Integer в список типа Number, с учетом их позиции, все это благодаря Wildcards
        List<Number> nums6 = Arrays.asList(0.1d , 0.2d, 0,3d, 23, 0.12F, 0.45D);
        List<Integer> ints6 = Arrays.asList(1, 3, 4);
        Collections.copy(nums6, ints6);     //Смотри реализацию Collections.copy()
        System.out.println(nums6);


        Generics.goodReverse(nums6);
        System.out.println(nums6);

        //Пример 4
        //Проектирование правильных обобщенных конструкций
        class FourthTest {
            //параметризованный метод!!
            public <T> List<T> nCopies(int n, T object) {
                List<T> l = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    l.add(object);
                }
                return l;
            }



            //Попробуем теперь реализовать метод, выполняющий перестановку элементов списка в обратном порядке.
            public void badReverse(List<?> list) {      //List<?> - список с неограниченным символом подстановки <?>(<? extends Object>)
                List<Object> tmp = new ArrayList<>(list);
                for (int i = 0; i < list.size(); i++) {
                    //list.set(i, tmp.get(list.size()-i-1));        \\ записываем элементы
                }
            }
            //Возникла тшибка компиляции, т к согласно PECS <? extends...> - producer, а значит может только
            // продюсировать элементы, а мы пытаемся в него записывать элементы!

            //Паттерн WildCart Capture:
            public void goodReverse(List<?> list) {
                rev(list);
            }
            private <T> void rev(List<T> list) {       //Работаем с параметризованным методом, аргумент это лист с конкретным
                List<T> tmp = new ArrayList<T>(list);   //парметром типа, а значит и consumer и producer
                for (int i = 0; i < list.size(); i++) {
                    list.set(i, tmp.get(list.size()-i-1));      //теперь все компилится
                }
            }

            //Реализация обобщенного метода max согласно PECS(стандартный JDK метод)

            public <T> T MyMax(Collection<? extends T> coll, Comparator<? super T> comp){
                return null; //без реализации
            }
            /*
            Почему сигнатура именно такая?

            Коллекция - продюссер, она предоставляет нам элементы!! PRODUCER - EXTENDS
            Компаратор - потребитель, для сравнивания он потребляет объекты, т к поочередно вызывает
            метод сравнения принимая аргументом объект!! CONSUMER - SUPER
             */
        }
        //Какую это гибкость дает?
        //Collections.max(List<Integer>, Comparator<Number> - при условии создания экземпляра анонимного класса);
        //Collections.max(List<String>, Comparator<Object> - при условии создания экземпляра анонимного класса);

        //Выполнение методов
        List<Box<? extends Number>> lb = new ArrayList<>();
        lb.add(new Box<Integer>(234));
        lb.add(new Box<Double>(234D));
        lb.add(new Box<Long>(234L));
        lb.add(new Box<Integer>(234));
        lb.add(new Box<Float>(234F));

        System.out.println(Generics.sum(lb));
        List<Box<String>> l = new ArrayList<>();
        l.add(new Box<String>("Aawdaw"));
        l.add(new Box<String>("Aawdaw"));
        l.add(new Box<String>("Aawdaw"));
        l.add(new Box<String>("Aawdaw"));
        l.add(new Box<String>("Aawdaw"));
        l.add(new Box<String>("Aawdaw"));
        Generics.add(l, "sdfsdf");
        System.out.println(l.get(0).getOb());
        System.out.println(l.get(1).getOb());
        System.out.println(l.get(2).getOb());


        //А так можно :) НО в рантайме КлассКаст
        List<Integer> integers = new ArrayList<>();
        List list = integers;
        List<Number> myNumbers = list;
        myNumbers.add(7.7d);
        /*
        Вывод:
        Если необходимо читать из контейнера, то используйте wildcard с верхней границей "? extends".
        Если необходимо писать в контейнер, то используйте wildcard с нижней границей "? super".
        Не используйте wildcard, если нужно производить и запись, и чтение.
        Не используйте Raw типы! Если аргумент типа не определен, то используйте wildcard <?>.
         */




    }


}

