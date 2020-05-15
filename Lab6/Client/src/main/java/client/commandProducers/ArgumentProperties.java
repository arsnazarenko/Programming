package client.commandProducers;

public interface ArgumentProperties extends StandardCommandProducer{
    /**
     * Метод для установки параметров команде
     * @param parameter
     */
    void setArgument(String parameter);
}
