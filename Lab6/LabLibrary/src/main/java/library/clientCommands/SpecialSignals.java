package library.clientCommands;

public enum SpecialSignals {
    EXIT_TRUE("Завершение"),
    AUTHORIZATION_FALSE("Вы не авторизованы"),
    SCRIPT_TRUE("Выполнение скрипта"),
    AUTHORIZATION_TRUE("Пользователь авторизован"),
    REG_TRUE("Пользователь зарегетстрирован"),
    REG_FALSE("Пользователь с таким логином уже есть"),
    SERVER_DIED("Ошибка работы сервера");

    private String msg;

    SpecialSignals(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
