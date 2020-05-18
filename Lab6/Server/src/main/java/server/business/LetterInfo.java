package server.business;


import java.net.SocketAddress;

public class LetterInfo {
    private SocketAddress remoteAddress;
    private Object transferObject;

    public LetterInfo(SocketAddress remoteAddress, Object transferObject) {
        this.remoteAddress = remoteAddress;
        this.transferObject = transferObject;
    }

    public SocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Object getTransferObject() {
        return transferObject;
    }

    public void setTransferObject(Object transferObject) {
        this.transferObject = transferObject;
    }
}
