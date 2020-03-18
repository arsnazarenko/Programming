package project.client.commands.uiCommands;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;

public class LoadCommand extends Command {
    private String xmlText;

    public LoadCommand(String xmlText) {

        this.xmlText = xmlText;
    }

    public String getXmlText() {
        return xmlText;
    }

    public void setXmlText(String xmlText) {
        this.xmlText = xmlText;
    }
}
