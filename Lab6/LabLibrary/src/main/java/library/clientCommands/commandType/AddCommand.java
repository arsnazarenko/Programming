package library.clientCommands.commandType;


import library.clientCommands.Command;
import library.clientCommands.UserData;
import library.сlassModel.Organization;

import java.util.Objects;


public class AddCommand extends Command {
    private Organization organization;

    public AddCommand(Organization organization, UserData userData) {
        super(userData);
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddCommand that = (AddCommand) o;
        return Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization);
    }

}
