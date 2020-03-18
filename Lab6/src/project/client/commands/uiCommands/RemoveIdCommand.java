package project.client.commands.uiCommands;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;

public class RemoveIdCommand extends Command {
    private Long id;

    public RemoveIdCommand(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
