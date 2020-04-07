package server.services;

import java.net.SocketAddress;

public interface IServerSender {
    void send(Object letter, SocketAddress address);
}
