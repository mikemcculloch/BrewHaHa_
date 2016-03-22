package brightseer.com.brewhaha;

/**
 * Created by mccul_000 on 10/13/2014.
 */
public class Constants {

    public static int DATABASE_VERSION = 27;
    public static final String LOG = "brightseer.com.brewhaha";

    public static final String IV = "c2ee4e98f99579ed";
    public static final String PASSWORD = "D.[5P{UPv?{gFX8W";
    public static final String SALT = "89NZv8Q7KB/hemFwt4yHDw==";

    public static String urlRecipePlaceHolder = "http://wcf.brewhaha.beer/images/img_recipe_placeholder.jpg";
    public static String urlBrewHahaContent = "http://www.brewhaha.beer/";

    public static String rest_url_base = "http://wcf.brewhaha.beer/brewhaha.svc/";
    public static String fileuploaderServices = "http://wcf.brewhaha.beer/Fileuploader.svc/";

    public static String recipeServics = "http://beerservices.brewhaha.beer/RecipeServices.svc/";

    public static String wcfGetHomeContentListByLastId = rest_url_base + "GetHomeContentListByLastId/";
    public static String wcfGetBatchSizeList = rest_url_base + "GetBatchSizeList"; //No Params
    public static String wcfGetContentById = rest_url_base + "GetContentById/";

    public static String wcfGetAllGrain = rest_url_base + "GetAllGrains";//No Params
    public static String wcfGetAllHops = rest_url_base + "GetAllHops";//No Params
    public static String wcfGetAllYeast = rest_url_base + "GetAllYeast";//No Params

    public static String wcfGetDifficultyMList = rest_url_base + "GetDifficultyMList"; //No Params
    public static String wcfGetRecipeTypeMList = rest_url_base + "GetRecipeTypeMList"; //No Params
    public static String wcfGetStyleMList = rest_url_base + "GetStyleMList"; //No Params
    public static String wcfAddUpdateFavorite = rest_url_base + "AddUpdateFavorite/";
    public static String wcfGetHomeContentFromSearch = rest_url_base + "GetHomeContentFromSearch/";
    public static String wcfGetSearchResultsCount = rest_url_base + "GetSearchResultsCount/";
    //    public static String wcfUploadImage = fileuploaderServices + "Upload/";
    public static String wcfGetUserProfile = rest_url_base + "GetUserProfile/";
    public static String wcfAddUpdateComment = rest_url_base + "AddUpdateComment/";
    public static String wcfGetRefreshDate = rest_url_base + "GetRefreshDate"; //No Params
    public static String wcfGetCommentSetByLastId = rest_url_base + "GetCommentSetByLastId/";
    public static String wcfRemoveComment = rest_url_base + "RemoveComment/";
    public static String wcfGetUserContentByLastId = rest_url_base + "GetUserContentByLastId/";
    public static String wcfAddDefaultRecipe = rest_url_base + "AddDefaultRecipe/";
    public static String wcfRemoveRecipe = rest_url_base + "RemoveRecipe/";
    public static String wcfUpdateRecipeContent = rest_url_base + "UpdateRecipeContent/";
    public static String wcfGetRecipeGrain = rest_url_base + "GetRecipeGrain/";
    public static String wcfGetUnitOfMeasure = rest_url_base + "GetUnitOfMeasure";
    public static String wcfGetGrainUse = rest_url_base + "GetGrainUse";
    public static String wcfGetCounties = rest_url_base + "GetCounties";
    public static String wcfAddUpdateIngredientGrain = rest_url_base + "AddUpdateIngredientGrain/"; ///{contentItemPk}/{ingredientGrainPk}/{grainPk}/{grainUsePk}/{countryPk}/{grainTypePk}/{unitOfMeasurePk}/{amount}/{grainName}/{color}
    public static String wcfRemoveIngredientGrain = rest_url_base + "RemoveIngredientGrain/"; ///{contentItemPk}/{ingredientGrainPk}/{active}
    public static String wcfGetColorKeys = rest_url_base + "GetColorKeys";

    public static String wcfGetRecipeYeast = rest_url_base + "GetRecipeYeast/";
    public static String wcfGetRecipeHops = rest_url_base + "GetRecipeHops/";
    public static String wcfGetInstructions = rest_url_base + "GetInstructions/";
    public static String wcfGetHopsUse = rest_url_base + "GetHopsUse";
    public static String wcfGetHopsForm = rest_url_base + "GetHopsForm";
    public static String wcfGetLaboratories = rest_url_base + "GetLaboratories";
    public static String wcfAddUpdateIngredientHopV2 = rest_url_base + "AddUpdateIngredientHopV2/";
    public static String wcfRemoveIngredientHop = rest_url_base + "RemoveIngredientHop/"; //RemoveIngredientHop/{contentItemPk}/{ingredientHopsPk}/{active}
    public static String wcfAddUpdateIngredientYeast = rest_url_base + "AddUpdateIngredientYeast/";    //AddUpdateIngredientYeast/{ingredientYeastPk}/{contentItemPk}/{laboratoryPk}/{yeastPk}/{attenuationPercentage}
    public static String wcfRemoveIngredientYeast = rest_url_base + "RemoveIngredientYeast/"; //RemoveIngredientYeast/{ingredientYeastPk}/{contentItemPk}/{active}
    public static String wcfAddUpdateInstruction = rest_url_base + "AddUpdateInstruction/"; //AddUpdateInstruction/{instructionPk}/{contentItemPk}/{userProfilePk}/{order}
    public static String wcfRemoveInstruction = rest_url_base + "RemoveInstruction/"; //RemoveInstruction/{instructionPk}/{contentItemPk}/{active}

    public static String wcfGetRecipeImages = rest_url_base + "GetRecipeImages/";
    public static String wcfUploadContentImages = fileuploaderServices + "UploadContentImages/";
    public static String wcfRemoveContentImage = rest_url_base + "RemoveContentImage/"; //{contentToken}/{imagePk}

    public static String wcfGetAllPendingApproval = rest_url_base + "GetAllPendingApproval/"; //GetAllPendingApproval/{userToken}
    public static String wcfRequestApproval = rest_url_base + "RequestApproval/"; //RequestApproval/{userToken}/{recipeToken}
    public static String wcfApproveRequest = rest_url_base + "ApproveRequest/"; //ApproveRequest/{adminToken}/{recipeToken}

    public static String wcfCopyGrain = rest_url_base + "CopyGrain/";
    public static String wcfCopyHop = rest_url_base + "CopyHop/";
    public static String wcfCopyYeast = rest_url_base + "CopyYeast/";
    public static String wcfCopyInstruction = rest_url_base + "CopyInstruction/";

    public static String wcfInstructionOrderUpdate = rest_url_base + "InstructionOrderUpdate/";

    public static String wcfPlusValidation = rest_url_base + "PlusValidation"; //(string googlePlusId, string fullName, string emailAddress, string userImageUrl, string plusProfile)



    public static String GetRecipeItemUrl = recipeServics + "GetRecipeByGuid/";











    public static String DATABASE_NAME = "brewhaha.db";
    public static final String table_HomeItem = "HomeItem";
    public static final String field_ContentItemPk = "ContentItemPk";

    public static String DATABASE_NAME_BatchSize = "brewhaha.db.batchsize";
    public static final String batchSize_TableName = "BatchSize";
    public static final String batchSize_BatchSizePk = "BatchSizePk";

    public static String DATABASE_NAME_Difficulty = "brewhaha.db.difficulty";
    public static final String difficulty_TableName = "Difficulty";
    public static final String difficulty_DifficultyPk = "DifficultyPk";
    public static final String difficulty_Description = "Description";

    public static String DATABASE_NAME_RecipeType = "brewhaha.db.recipetype";
    public static final String recipeType_TableName = "RecipeType";
    public static final String recipeType_RecipeTypePk = "RecipeTypePk";
    public static final String recipeType_Description = "Description";

    public static String DATABASE_NAME_Style = "brewhaha.db.style";
    public static final String style_Table = "Style";
    public static final String style_StylePk = "StylePk";
    public static final String style_Description = "Description";

    public static String DATABASE_NAME_InstructionSelected = "brewhaha.db.InstructionSelected";
    public static final String table_InstructionSelected = "InstructionSelected";
    public static final String instructionSelected_UserToken = "UserToken";
    public static final String instructionSelected_ContentItemPk = "ContentItemPk";
    public static final String InstructionSelected_InstructionsId = "InstructionsId";

    public static String DATABASE_NAME_IngredientSelected = "brewhaha.db.IngredientSelected";
    public static final String table_IngredientSelected = "IngredientSelected";
    public static final String ingredientSelected_UserToken = "UserToken";
    public static final String ingredientSelected_ContentItemPk = "ContentItemPk";
    public static final String ingredientSelected_IngredientId = "IngredientId";
    public static final String ingredientSelected_Type = "Type";

    public static String DATABASE_NAME_Grain = "brewhaha.db.grain";
    public static final String grain_TableName = "Grain";
    public static final String grain_Name = "Name";
    public static final String grain_GrainPk = "GrainPk";

    public static String DATABASE_NAME_Yeast = "brewhaha.db.yeast";
    public static final String yeast_TableName = "Yeast";
    public static final String yeast_YeastPk = "YeastPk";
    public static final String yeast_Name = "Name";

    public static String DATABASE_NAME_Hops = "brewhaha.db.hops";
    public static final String hops_TableName = "Hops";
    public static final String hops_HopsPk = "HopsPk";
    public static final String hops_Name = "Name";

    public static String DATABASE_NAME_UnitOfMeasure = "brewhaha.db.unitOfMeasure";
    public static final String unitOfMeasure_Table = "UnitOfMeasure";
    public static final String unitOfMeasure_Description = "Description";
    public static final String unitOfMeasure_Type = "Type";
    public static final String unitOfMeasure_TypeDescription = "TypeDescription";
    public static final String unitOfMeasure_UnitOfMeasurePk = "UnitOfMeasurePk";

    public static String DATABASE_NAME_GrainUse = "brewhaha.db.grainUse";
    public static final String grainUse_Table = "GrainUse";
    public static final String grainUse_Description = "Description";
    public static final String grainUse_GrainUsePk = "GrainUsePk";

    public static String DATABASE_NAME_Country = "brewhaha.db.country";
    public static final String country_Table = "Country";
    public static final String country_Abbreviation = "Abbreviation";
    public static final String country_CountryPk = "CountryPk";
    public static final String country_Name = "Name";

    public static String DATABASE_NAME_SrmColorKey = "brewhaha.db.srmColorKey";
    public static final String srmColorKey_Table = "SrmColorKey";
    public static final String srmColorKey_HexColor = "HexColor";
    public static final String srmColorKey_ColorSrm = "ColorSrm";
    public static final String srmColorKey_SrmColorKeyPk = "SrmColorKeyPk";

    public static String DATABASE_NAME_HopsUse = "brewhaha.db.hopsUse";
    public static final String hopsUse_Table = "HopsUse";
    public static final String hopsUse_Description = "Description";
    public static final String hopsUse_HopsUsePk = "HopsUsePk";

    public static String DATABASE_NAME_HopsForm = "brewhaha.db.hopsForm";
    public static final String hopsForm_Table = "HopsForm";
    public static final String hopsForm_Description = "Description";
    public static final String hopsForm_HopsFormPk = "HopsFormPk";

    public static String DATABASE_NAME_Laboratory = "brewhaha.db.laboratory";
    public static final String laboratory_Table = "Laboratory";
    public static final String laboratory_Name = "Name";
    public static final String laboratory_LaboratoryPk = "LaboratoryPk";

    public static final String spLoggedIn = "LoggedIn";
    public static final String spUserToken = "UserToken";
    public static final String spShowWelcome = "ShowWelcome";
    public static final String spScreenName = "ScreenName";
    public static final String spContentToken = "ContentToken";
    public static final String spContentTitle = "ContentTitle";
    public static final String spUserImagePath = "UserImagePath";
    public static final String spFullName = "FullName";
    public static final String spEmailAddress = "EmailAddress";
    public static final String spReferenceLastUpdated = "ReferenceLastUpdated";
    public static final String spContentItemPk = "contentItemPk";
    public static final String spUserProfileImageUrl = "UserProfileImageUrl";
    public static final String spUserProfileDate = "userProfileDate";
    public static final String spNextContentItemId = "NextContentItemId";
    public static final String spAcceptedTerms = "spAcceptedTerms";

    public static final String spGoolgePlusProfileUrl = "spGoolgePlusProfileUrl";

    public static final String exRecipeTypeValue = "RecipeTypeValue";
    public static final String exStyleValue = "StyleValue";
    public static final String exDifficultyValue = "DifficultyValue";
    public static final String exSearchValue = "SearchValue";
    public static final String exContentItemPk = "ContentItemPk";
    public static final String exForceUpdate = "ForceUpdate";
    public static final String exImageUrlList = "ImageUrlList";
    public static final String exRecipePreview = "RecipePreview";

    public static final String exRecipeTitle = "RecipeTitle";
    public static final String exPosition = "Position";
    public static final String exUsername = "exUsername";
    public static final String exUserdate = "exUserdate";
    public static final String exBitMapInfo = "BitMapInfo";
    public static final String exAuthorImage = "exAuthorImage";
    public static final String exRecipeImage = "exRecipeImage";
    public static final String exBitMapInfoMain = "BitMapInfoMain";

    public static final String exAbvValue = "abvValue";
    public static final String exIbuValue = "ibuValue";
    public static final String exGrainPk = "grainPk";
    public static final String exHopsPk = "hopsPk";
    public static final String exYeastPk = "yeastPk";
    public static final int NUM_OF_COLUMNS = 3;

    public static final int sceneCards = 0;
    public static final int sceneDirections = 1;
    public static final int sceneIngredients = 2;
    public static final int sceneOverview = 3;
    public static final int sceneComments = 4;

    public static final int ANIMATION_DELAY = 300;

    public static final String gacOpen = "Open";
    public static final String gacMainActivity = "MainActivity";
    public static final String gacRecipe = "Recipe";
    public static final String gacLogin = "Login";
    public static final String gacSignUp = "Signup";

    public static final String fragTagInstructions = "fragment_instruction";

    public static final String flavorLite = "lite";
    public static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApqlUQJHJXij+JeVCZrL48q8CbAz1S2+1fxZjhLQOfvVMI9wooIW49rYPet6Z/DUQWlOmwmGunVpT52E8CL2aHJf7Qfnv/YiuCPRsEaHTtW1/832AFL8mNTkzmRFLHskIUhUM7OxOi6Ls3RV4zzTiPm7LpCeqH2YS9dTBqSQzqnVeMRMHq46e5tbGw/wE9fVvL/twXI5kAwwjon0DywOf93vp2r/GeEOqgfy53fIoKfh3/SngcirkHJmvD76Lng0ePKKJ2u4npBv5cGBO/XPr31qWhZI1telK+uPP/eeCVIamhMXj6KUvwQh2NkyaLWSM4JSk26dOpqbKHU2Imz9mUQIDAQAB";

    public static final String flavorFull = "full";
    public static final String BASE64_PUBLIC_KEY_FULL = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArfCEsknjElo0tF18N7hh95kNr7eWMgBbc8jwtlM5bcJK3j/RDpQ3PLFToYsfdmT5SG0oMqLs7PiEMB+ek7GIrVe3IT7GDxbHWPhnISlDtOLC8yEvdjVekEyCkaY6Q49N9afBS+1b0vRROpqsqT3izU4GGqt2qlo04IsJkZdPKTputtx2MiAZmlmKGMXk6uERl+Gnev5Sutlqa2b5h/BqbS3DHCV2OOTD7VWvPCbUlXseqLL5bDInk1QJzmRIYicpj8/mH9tvQgFEMkjhqSQgqO/05MWTKD9hta5WG6XLkZG0kc3jnyHKjcszcdyAVA4m8k7ziatyymP66FLdCjtGoQIDAQAB";
}
