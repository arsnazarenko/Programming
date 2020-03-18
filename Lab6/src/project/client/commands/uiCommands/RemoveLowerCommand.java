package project.client.commands.uiCommands;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;
import project.client.сlassModel.Organization;

public class RemoveLowerCommand extends Command {
    Organization organization;

    public RemoveLowerCommand(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
