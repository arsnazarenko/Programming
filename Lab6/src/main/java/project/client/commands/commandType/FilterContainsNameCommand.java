package project.client.commands.commandType;

import project.client.commands.Command;

public class FilterContainsNameCommand extends Command {
    private String subString;

    public FilterContainsNameCommand(String subString) {
        this.subString = subString;
    }

    public String getSubString() {
        return subString;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }
}
