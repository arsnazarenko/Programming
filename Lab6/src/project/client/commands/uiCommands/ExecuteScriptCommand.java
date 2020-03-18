package project.client.commands.uiCommands;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;

public class ExecuteScriptCommand extends Command {
    public String script;

    public ExecuteScriptCommand(String script) {
        this.script = script;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
