package project.client.serialization;

import java.io.Serializable;

public interface ISerializationManager {
    byte[] objectSerial(Serializable object);

    Object objectDeserial(byte[] array);
}
