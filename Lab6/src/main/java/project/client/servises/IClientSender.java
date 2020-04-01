package project.client.servises;

import java.io.Serializable;
import java.net.SocketAddress;

public interface IClientSender {
    void send(Serializable letter, SocketAddress address);
}
