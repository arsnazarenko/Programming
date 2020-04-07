package project.server.services;

import java.io.Serializable;
import java.net.SocketAddress;

public interface IServerSender {
    void send(Object letter, SocketAddress address);
}
