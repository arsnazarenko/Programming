package project.client.commands.commandType;

import project.client.commands.Command;

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
