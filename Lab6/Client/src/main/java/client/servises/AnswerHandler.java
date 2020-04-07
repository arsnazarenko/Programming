package client.servises;



import library.сlassModel.Organization;

import java.util.Deque;

public class AnswerHandler {
    public void handling(Object answerObject) {
        if(answerObject instanceof Deque) {
            Deque<Organization> organizationDeque = (Deque) answerObject;
            StringBuilder stringBuilder = new StringBuilder();
            organizationDeque.forEach(o -> stringBuilder.append(o.toString() + "\n"));
            System.out.println(stringBuilder.toString());
        } else if (answerObject instanceof String || answerObject instanceof Organization) {
            System.out.println(answerObject);
        } else if (answerObject == null) {
            System.out.println("Объектов не найдено");
        }
    }
}
