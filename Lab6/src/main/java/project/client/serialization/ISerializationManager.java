package project.client.serialization;

import java.io.Serializable;

public interface ISerializationManager {
    byte[] objectSerial(Object object);

    Object objectDeserial(byte[] array);
}
