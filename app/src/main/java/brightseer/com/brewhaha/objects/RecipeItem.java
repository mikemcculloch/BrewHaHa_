package brightseer.com.brewhaha.objects;

import java.util.List;

import brightseer.com.brewhaha.models.Comment;

public class RecipeItem {
    private int RecipeContentId;
    private String Title;
    private String Description;
    private String Author;
    private String UserImageUrl;
    private List<RecipeImage> RecipeImage;
    private int UserProfileId;
    private brightseer.com.brewhaha.objects.RecipeSummary RecipeSummary;
    private int BatchSizeId;
    private int StyleId;
    private String StyleDescription;
    private List<RecipeGrain> RecipeGrains;
    private List<RecipeHop> RecipeHops;
    private List<RecipeYeast> RecipeYeasts;
    private List<Comment> Comments;
    private List<RecipeInstruction> RecipeInstructions;
    private boolean Favorite;
    private String Token;
    private boolean Approved;
    private boolean Submitted;
    private int NextRecipeContentId;
    private String DateModified;
    private String DateCreated;

    public RecipeItem() {
    }

    public RecipeItem(int recipeContentId, String title, String description, String author, String userImageUrl, List<RecipeImage> recipeImage, int userProfileId, brightseer.com.brewhaha.objects.RecipeSummary recipeSummary, int batchSizeId, int styleId, String styleDescription, List<RecipeGrain> recipeGrains, List<RecipeHop> recipeHops, List<RecipeYeast> recipeYeasts, List<Comment> comments, List<RecipeInstruction> recipeInstructions, boolean favorite, String token, boolean approved, boolean submitted, int nextRecipeContentId, String dateModified, String dateCreated) {
        RecipeContentId = recipeContentId;
        Title = title;
        Description = description;
        Author = author;
        UserImageUrl = userImageUrl;
        this.RecipeImage = recipeImage;
        UserProfileId = userProfileId;
        RecipeSummary = recipeSummary;
        BatchSizeId = batchSizeId;
        StyleId = styleId;
        StyleDescription = styleDescription;
        RecipeGrains = recipeGrains;
        RecipeHops = recipeHops;
        RecipeYeasts = recipeYeasts;
        Comments = comments;
        this.RecipeInstructions = recipeInstructions;
        Favorite = favorite;
        Token = token;
        Approved = approved;
        Submitted = submitted;
        NextRecipeContentId = nextRecipeContentId;
        DateModified = dateModified;
        DateCreated = dateCreated;
    }

    public int getRecipeContentId() {
        return RecipeContentId;
    }

    public void setRecipeContentId(int recipeContentId) {
        RecipeContentId = recipeContentId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public List<RecipeImage> getRecipeImage() {
        return RecipeImage;
    }

    public void setRecipeImage(List<RecipeImage> recipeImage) {
        this.RecipeImage = recipeImage;
    }

    public int getUserProfileId() {
        return UserProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        UserProfileId = userProfileId;
    }

    public brightseer.com.brewhaha.objects.RecipeSummary getRecipeSummary() {
        return RecipeSummary;
    }

    public void setRecipeSummary(brightseer.com.brewhaha.objects.RecipeSummary recipeSummary) {
        RecipeSummary = recipeSummary;
    }

    public int getBatchSizeId() {
        return BatchSizeId;
    }

    public void setBatchSizeId(int batchSizeId) {
        BatchSizeId = batchSizeId;
    }

    public int getStyleId() {
        return StyleId;
    }

    public void setStyleId(int styleId) {
        StyleId = styleId;
    }

    public String getStyleDescription() {
        return StyleDescription;
    }

    public void setStyleDescription(String styleDescription) {
        StyleDescription = styleDescription;
    }

    public List<RecipeGrain> getRecipeGrains() {
        return RecipeGrains;
    }

    public void setRecipeGrains(List<RecipeGrain> recipeGrains) {
        RecipeGrains = recipeGrains;
    }

    public List<RecipeHop> getRecipeHops() {
        return RecipeHops;
    }

    public void setRecipeHops(List<RecipeHop> recipeHops) {
        RecipeHops = recipeHops;
    }

    public List<RecipeYeast> getRecipeYeasts() {
        return RecipeYeasts;
    }

    public void setRecipeYeasts(List<RecipeYeast> recipeYeasts) {
        RecipeYeasts = recipeYeasts;
    }

    public List<Comment> getComments() {
        return Comments;
    }

    public void setComments(List<Comment> comments) {
        Comments = comments;
    }

    public List<RecipeInstruction> getRecipeInstructions() {
        return RecipeInstructions;
    }

    public void setRecipeInstructions(List<RecipeInstruction> recipeInstructions) {
        this.RecipeInstructions = recipeInstructions;
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isApproved() {
        return Approved;
    }

    public void setApproved(boolean approved) {
        Approved = approved;
    }

    public boolean isSubmitted() {
        return Submitted;
    }

    public void setSubmitted(boolean submitted) {
        Submitted = submitted;
    }

    public int getNextRecipeContentId() {
        return NextRecipeContentId;
    }

    public void setNextRecipeContentId(int nextRecipeContentId) {
        NextRecipeContentId = nextRecipeContentId;
    }

    public String getDateModified() {
        return DateModified;
    }

    public void setDateModified(String dateModified) {
        DateModified = dateModified;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }
}