package brightseer.com.brewhaha.objects;

import java.util.List;

public class RecipeItem {
    private int RecipeContentId;
    private String Title;
    private String Description;
    private String Author;
    private String UserImageUrl;
    private List<RecipeImage> recipeImage;
    private int UserProfileId;
    private BeerSummary RecipeSummary;
    private int BatchSizeId;
    private int StyleId;
    private String StyleDescription;
    private List<RecipeGrain> RecipeGrains;
    private List<RecipeHop> RecipeHops;
    private List<RecipeYeast> RecipeYeasts;
    private List<Comment> Comments;
    private List<Instruction> Instructions;
    private boolean Favorite;
    private String Token;
    public boolean Approved;
    public boolean Submitted;
    private int NextRecipeContentId;
    private String DateModified;
    private String DateCreated;

    public RecipeItem() {
    }

    public RecipeItem(int recipeContentId, String title, String description, String author, String userImageUrl, List<RecipeImage> recipeImage, int userProfileId, BeerSummary recipeSummary, int batchSizeId, int styleId, String styleDescription, List<RecipeGrain> recipeGrains, List<RecipeHop> recipeHops, List<RecipeYeast> recipeYeasts, List<Comment> comments, List<Instruction> instructions, boolean favorite, String token, boolean approved, boolean submitted, int nextRecipeContentId, String dateModified, String dateCreated) {
        RecipeContentId = recipeContentId;
        Title = title;
        Description = description;
        Author = author;
        UserImageUrl = userImageUrl;
        this.recipeImage = recipeImage;
        UserProfileId = userProfileId;
        RecipeSummary = recipeSummary;
        BatchSizeId = batchSizeId;
        StyleId = styleId;
        StyleDescription = styleDescription;
        RecipeGrains = recipeGrains;
        RecipeHops = recipeHops;
        RecipeYeasts = recipeYeasts;
        Comments = comments;
        Instructions = instructions;
        Favorite = favorite;
        Token = token;
        Approved = approved;
        Submitted = submitted;
        NextRecipeContentId = nextRecipeContentId;
        DateModified = dateModified;
        DateCreated = dateCreated;
    }
}