package brightseer.com.brewhaha.objects;

/**
 * Created by mccul_000 on 11/23/2014.
 */
public class Instruction {

    private int InstructionId;
    private int RecipeContentId;
    private int UserProfileId;
    private String Description;
    private int Order;
    private boolean Active;

    public Instruction() {
    }


    public Instruction(int instructionId, int recipeContentId, int userProfileId, String description, int order, boolean active) {
        InstructionId = instructionId;
        RecipeContentId = recipeContentId;
        UserProfileId = userProfileId;
        Description = description;
        Order = order;
        Active = active;
    }

    public int getInstructionId() {
        return InstructionId;
    }

    public void setInstructionId(int instructionId) {
        InstructionId = instructionId;
    }

    public int getRecipeContentId() {
        return RecipeContentId;
    }

    public void setRecipeContentId(int recipeContentId) {
        RecipeContentId = recipeContentId;
    }

    public int getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        UserProfileId = userProfileId;
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

        return !(InstructionId != 0 ? !String.valueOf(InstructionId).equals(that.InstructionId) : that.InstructionId != 0);
    }

    @Override
    public int hashCode() {
        return InstructionId != 0 ? InstructionId : 0;
    }
}
