package library.clientCommands.commandType;


import library.clientCommands.Command;
import library.сlassModel.Organization;

import java.util.Objects;

public class UpdateIdCommand extends Command {
    private Organization organization;
    private Long id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateIdCommand that = (UpdateIdCommand) o;
        return Objects.equals(organization, that.organization) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization, id);
    }
}
