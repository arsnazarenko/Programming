package library.clientCommands;

public enum SpecialSignals {
    EXIT_TRUE("Завершение"),
    AUTHORIZATION_FALSE("Вы не авторизованы"),
    SCRIPT_TRUE("Выполнение скрипта");

    private String msg;

    SpecialSignals(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
