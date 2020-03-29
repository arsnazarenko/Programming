package project.client.servises;

import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Objects;

public class LetterInfo {
    private SocketAddress remoteAddress;
    private byte[] bytes;

    public LetterInfo(SocketAddress address, byte[] bytes) {
        this.remoteAddress = address;
        this.bytes = bytes;
    }

    public SocketAddress getAddress() {
        return remoteAddress;
    }

    public void setAddress(SocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LetterInfo that = (LetterInfo) o;
        return Objects.equals(remoteAddress, that.remoteAddress) &&
                Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(remoteAddress);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
