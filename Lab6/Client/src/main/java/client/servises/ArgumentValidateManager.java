package client.servises;

public class ArgumentValidateManager {

    public Long idValid(String param) {
        Long result = null;
        try {
            result = Long.parseLong(param);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка вода id");
        }
        return result;
    }

    public String subStringIsValid(String param) {
        String result = null;
        if (param.trim().equals("")) {
            System.out.println("НЕВЕРНАЯ ПОДСТРОКА");
        } else {
            result = param;
        }
        return result;
    }
}
