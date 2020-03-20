package project.client.commands.commandType;

import project.client.commands.Command;

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
