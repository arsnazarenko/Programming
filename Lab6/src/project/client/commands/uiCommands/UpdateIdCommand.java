package project.client.commands.uiCommands;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;
import project.client.сlassModel.Organization;

public class UpdateIdCommand extends Command {
    Organization organization;
    Long id;

    public UpdateIdCommand(Organization organization, Long id) {
        this.organization = organization;
        this.id = id;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
