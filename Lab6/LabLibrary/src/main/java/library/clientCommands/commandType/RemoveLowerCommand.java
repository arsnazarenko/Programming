package library.clientCommands.commandType;


import library.clientCommands.Command;
import library.сlassModel.Organization;

import java.util.Objects;

public class RemoveLowerCommand extends Command {
    Organization organization;

    public RemoveLowerCommand(Organization organization, String login, String password) {
        super(login, password);
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
        RemoveLowerCommand that = (RemoveLowerCommand) o;
        return Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization);
    }
}
