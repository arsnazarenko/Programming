package library.clientCommands.commandType;


import library.clientCommands.Command;

import java.util.Objects;

public class ExecuteScriptCommand extends Command {
    private String script;

    public ExecuteScriptCommand(String script, String login, String password) {
        super(login, password);
        this.script = script;

    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecuteScriptCommand that = (ExecuteScriptCommand) o;
        return Objects.equals(script, that.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(script);
    }

}
