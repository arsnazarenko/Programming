package server.business.dao;

import java.util.Arrays;
import java.util.Objects;

public class UserInfo {
    private byte[] password;
    private Long userId;

    public UserInfo(byte[] password, Long userId) {
        this.password = password;
        this.userId = userId;
    }

    public byte[] getPassword() {
        return password;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Arrays.equals(password, userInfo.password) &&
                Objects.equals(userId, userInfo.userId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}

