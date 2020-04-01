package project.client.commands.commandType;

import project.client.commands.Command;
import project.client.сlassModel.Organization;

public class AddIfMinCommand extends Command {
    private Organization organization;

    public AddIfMinCommand(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
