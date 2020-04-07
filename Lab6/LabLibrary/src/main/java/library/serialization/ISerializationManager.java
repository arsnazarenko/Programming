package library.serialization;

public interface ISerializationManager {
    byte[] objectSerial(Object object);

    Object objectDeserial(byte[] array);
}
