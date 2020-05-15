package server.services;

import java.net.SocketAddress;

public interface IServerSender {
    void executeSendTask(Object letter, SocketAddress address);
}
