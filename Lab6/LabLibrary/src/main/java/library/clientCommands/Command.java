package library.clientCommands;

import java.io.Serializable;

public abstract class Command implements Serializable {
    private UserData userData;

    public Command(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
