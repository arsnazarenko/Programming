package project.client.commands.uiCommands;

import project.client.commands.Command;
import project.client.commands.NameOfCommands;
import project.client.сlassModel.Organization;

import java.io.Serializable;

public class AddCommand extends Command{
    private Organization organization;


    public AddCommand(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
