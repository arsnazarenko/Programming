package library.clientCommands.commandType;


import library.clientCommands.Command;
import library.clientCommands.UserData;

import java.util.Objects;

public class FilterContainsNameCommand extends Command {
    private String subString;

    public FilterContainsNameCommand(String subString, UserData userData) {
        super(userData);
        this.subString = subString;
    }

    public String getSubString() {
        return subString;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterContainsNameCommand that = (FilterContainsNameCommand) o;
        return Objects.equals(subString, that.subString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subString);
    }
}
