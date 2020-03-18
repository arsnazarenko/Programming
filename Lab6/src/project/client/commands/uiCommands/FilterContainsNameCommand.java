package project.client.commands.uiCommands;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;

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
