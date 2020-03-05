package Lessons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

public class ReflectionSearch {
    public static void searchOfClasses() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        //String marker = str;
        //нахожу КлассЛоадер ткекущего птока, затем ищем главного родителя классЛоадера , т к в нем лежит нужное нам поле
        ClassLoader myClOld = Thread.currentThread().getContextClassLoader();
        Class myCL = myClOld.getClass();
        while (myCL != java.lang.ClassLoader.class) {
            myCL = myCL.getSuperclass();
        }

        //Создаю поле типа Поле и с помощью рефлексии добываю приватное поле "classes" в текущем загрузчике классов.
        Field myCL_Classes = myCL.getDeclaredField("classes");
        // получаю доступ к приватному полю
        myCL_Classes.setAccessible(true);
        // присваиваю переменной типа Вектор значение этого поля в классе ClassLoader
        Vector classesVector = (Vector) myCL_Classes.get(myClOld);
        //Итерируясь по вектору, проверяю, есть ли у класса публичный констркутор, и тогда создаю экземпялр и получа. имя
        for (Object elem : classesVector) {
            Class a = (Class) elem;
            if (a.getConstructors().length != 0 && !(a.getSuperclass() == Exception.class)) {

                System.out.println("CLASS: " + a.newInstance().getClass().getName()+"\nFATHER: " + a.newInstance().getClass().getSuperclass().getName());
            }
        }

    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        ReflectionSearch.searchOfClasses();
    }
}
