package brightseer.com.brewhaha.objects;

import java.util.List;

public class RecipeContent {
    private int ContentItemPk;
    private String Author;
    private int BatchSizePk;
    private RecipeSummary recipeSummaryM;
    private List<Comment> CommentMList;
    private int CommentCount;
    private String Description;
    private String DifficultyDescription;
    private int DifficultyPk;
    private List<RecipeImage> imagesMList;
    private List<Ingredient> IngredientMList;
    private int ItemTypePk;
    private String LastUpdatedTimestamp;
    private String RecipeDescription;
    private int RecipeTypePk;
    private String StyleDescription;
    private int StylePk;
    private String Timestamp;
    private String Title;
    private int UserProfilePk;
    private boolean Favorite;
    private String Token;
    private List<RecipeInstruction> recipeInstructions;
    private String UserImageUrl;
    private int TotalLikes;
    private int NextContentItemId;
    public boolean Approved;
    public boolean Submitted;

    public RecipeContent() {
    }

    public RecipeContent(int contentItemPk, String author, int batchSizePk, RecipeSummary recipeSummaryM, List<Comment> commentMList, int commentCount, String description, String difficultyDescription, int difficultyPk, List<RecipeImage> imagesMList, List<Ingredient> ingredientMList, int itemTypePk, String lastUpdatedTimestamp, String recipeDescription, int recipeTypePk, String styleDescription, int stylePk, String timestamp, String title, int userProfilePk, boolean favorite, String token, List<RecipeInstruction> recipeInstructions, String userImageUrl, int totalLikes, int nextContentItemId, boolean approved, boolean submitted) {
        ContentItemPk = contentItemPk;
        Author = author;
        BatchSizePk = batchSizePk;
        this.recipeSummaryM = recipeSummaryM;
        CommentMList = commentMList;
        CommentCount = commentCount;
        Description = description;
        DifficultyDescription = difficultyDescription;
        DifficultyPk = difficultyPk;
        this.imagesMList = imagesMList;
        IngredientMList = ingredientMList;
        ItemTypePk = itemTypePk;
        LastUpdatedTimestamp = lastUpdatedTimestamp;
        RecipeDescription = recipeDescription;
        RecipeTypePk = recipeTypePk;
        StyleDescription = styleDescription;
        StylePk = stylePk;
        Timestamp = timestamp;
        Title = title;
        UserProfilePk = userProfilePk;
        Favorite = favorite;
        Token = token;
        this.recipeInstructions = recipeInstructions;
        UserImageUrl = userImageUrl;
        TotalLikes = totalLikes;
        NextContentItemId = nextContentItemId;
        Approved = approved;
        Submitted = submitted;
    }

    public int getContentItemPk() {
        return ContentItemPk;
    }

    public void setContentItemPk(int contentItemPk) {
        ContentItemPk = contentItemPk;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public int getBatchSizePk() {
        return BatchSizePk;
    }

    public void setBatchSizePk(int batchSizePk) {
        BatchSizePk = batchSizePk;
    }

    public RecipeSummary getRecipeSummaryM() {
        return recipeSummaryM;
    }

    public void setRecipeSummaryM(RecipeSummary recipeSummaryM) {
        this.recipeSummaryM = recipeSummaryM;
    }

    public List<Comment> getCommentMList() {
        return CommentMList;
    }

    public void setCommentMList(List<Comment> commentMList) {
        CommentMList = commentMList;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDifficultyDescription() {
        return DifficultyDescription;
    }

    public void setDifficultyDescription(String difficultyDescription) {
        DifficultyDescription = difficultyDescription;
    }

    public int getDifficultyPk() {
        return DifficultyPk;
    }

    public void setDifficultyPk(int difficultyPk) {
        DifficultyPk = difficultyPk;
    }

    public List<RecipeImage> getImagesMList() {
        return imagesMList;
    }

    public void setImagesMList(List<RecipeImage> imagesMList) {
        this.imagesMList = imagesMList;
    }

    public List<Ingredient> getIngredientMList() {
        return IngredientMList;
    }

    public void setIngredientMList(List<Ingredient> ingredientMList) {
        IngredientMList = ingredientMList;
    }

    public int getItemTypePk() {
        return ItemTypePk;
    }

    public void setItemTypePk(int itemTypePk) {
        ItemTypePk = itemTypePk;
    }

    public String getLastUpdatedTimestamp() {
        return LastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(String lastUpdatedTimestamp) {
        LastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public String getRecipeDescription() {
        return RecipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        RecipeDescription = recipeDescription;
    }

    public int getRecipeTypePk() {
        return RecipeTypePk;
    }

    public void setRecipeTypePk(int recipeTypePk) {
        RecipeTypePk = recipeTypePk;
    }

    public String getStyleDescription() {
        return StyleDescription;
    }

    public void setStyleDescription(String styleDescription) {
        StyleDescription = styleDescription;
    }

    public int getStylePk() {
        return StylePk;
    }

    public void setStylePk(int stylePk) {
        StylePk = stylePk;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getUserProfilePk() {
        return UserProfilePk;
    }

    public void setUserProfilePk(int userProfilePk) {
        UserProfilePk = userProfilePk;
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

    public List<RecipeInstruction> getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(List<RecipeInstruction> recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public int getTotalLikes() {
        return TotalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        TotalLikes = totalLikes;
    }

    public int getNextContentItemId() {
        return NextContentItemId;
    }

    public void setNextContentItemId(int nextContentItemId) {
        NextContentItemId = nextContentItemId;
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
}