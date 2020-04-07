package server.services;


import library.clientCommands.Command;

import java.net.SocketAddress;

public class LetterInfo {
    private SocketAddress remoteAddress;
    private Command receiveCommand;

    public LetterInfo(SocketAddress remoteAddress, Command receiveCommand) {
        this.remoteAddress = remoteAddress;
        this.receiveCommand = receiveCommand;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Command getReceiveCommand() {
        return receiveCommand;
    }

    public void setReceiveCommand(Command receiveCommand) {
        this.receiveCommand = receiveCommand;
    }
}
