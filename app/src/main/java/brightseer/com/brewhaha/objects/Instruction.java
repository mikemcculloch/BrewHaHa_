package brightseer.com.brewhaha.objects;

/**
 * Created by mccul_000 on 11/23/2014.
 */
public class Instruction {

    private int InstructionPk;
    private int ContentItemPk;
    private int UserProfilePk;
    private String Description;
    private int Order;
    private boolean Active;

    public Instruction() {
    }


    public Instruction(int instructionPk, int contentItemPk, int userProfilePk, String description, int order, boolean active) {
        InstructionPk = instructionPk;
        ContentItemPk = contentItemPk;
        UserProfilePk = userProfilePk;
        Description = description;
        Order = order;
        Active = active;
    }

    public int getInstructionPk() {
        return InstructionPk;
    }

    public void setInstructionPk(int instructionPk) {
        InstructionPk = instructionPk;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public int getUserProfilePk() {
        return UserProfilePk;
    }

    public void setUserProfilePk(int userProfilePk) {
        UserProfilePk = userProfilePk;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instruction)) {
            return false;
        }

        Instruction that = (Instruction) o;

        return !(InstructionPk != 0 ? !String.valueOf(InstructionPk).equals(that.InstructionPk) : that.InstructionPk != 0);
    }

    @Override
    public int hashCode() {
        return InstructionPk != 0 ? InstructionPk : 0;
    }
}
