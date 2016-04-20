package brightseer.com.brewhaha.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Vector;

import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.objects.BatchSize;
import brightseer.com.brewhaha.models.Comment;
import brightseer.com.brewhaha.objects.Country;
import brightseer.com.brewhaha.objects.Difficulty;
import brightseer.com.brewhaha.objects.Grain;
import brightseer.com.brewhaha.objects.GrainUse;
import brightseer.com.brewhaha.objects.MainFeed;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.objects.Hops;
import brightseer.com.brewhaha.objects.HopsForm;
import brightseer.com.brewhaha.objects.HopsUse;
import brightseer.com.brewhaha.models.RecipeImage;
import brightseer.com.brewhaha.models.RecipeGrain;
import brightseer.com.brewhaha.models.RecipeHop;
import brightseer.com.brewhaha.models.RecipeInstruction;
import brightseer.com.brewhaha.objects.KeyValuepair;
import brightseer.com.brewhaha.objects.Laboratory;
import brightseer.com.brewhaha.objects.RecipeContent;
import brightseer.com.brewhaha.objects.RecipeItem;
import brightseer.com.brewhaha.objects.RecipeType;
import brightseer.com.brewhaha.models.RecipeYeast;
import brightseer.com.brewhaha.objects.SrmColorKey;
import brightseer.com.brewhaha.objects.Style;
import brightseer.com.brewhaha.objects.UnitOfMeasure;
import brightseer.com.brewhaha.objects.UserProfile;
import brightseer.com.brewhaha.objects.Yeast;

public class JsonToObject {
    public static RecipeItem JsonToRecipeItem(JsonObject result) {
        RecipeItem recipeItem = new RecipeItem();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();

            recipeItem = gson.fromJson(result, RecipeItem.class);

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return recipeItem;
    }


    public static MainFeed JsonToMainFeed(JsonObject result) {
        MainFeed mainFeed = new MainFeed();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();

            mainFeed = gson.fromJson(result, MainFeed.class);

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return mainFeed;
    }









    public static RecipeContent JsonToRecipeContent(JsonObject result) {
        RecipeContent recipeContent = new RecipeContent();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();
//            JSONObject j = new JSONObject(result.toString());

            recipeContent = gson.fromJson(result, RecipeContent.class);

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return recipeContent;
    }

    public static List<MainFeedItem> JsonToHomeItemList(JsonArray result) {
        List<MainFeedItem> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();
            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), MainFeedItem.class));
            }


        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return gig;
    }

    public static List<Comment> JsonToCommentsList(JsonArray result) {
        List<Comment> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Comment.class));
            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return gig;
    }

    public static List<BatchSize> JsonToBatchSize(JsonArray result) {
        List<BatchSize> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), BatchSize.class));
            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return gig;
    }

    public static List<Difficulty> JsonToDifficulty(JsonArray result) {
        List<Difficulty> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();
            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Difficulty.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return gig;
    }

    public static List<RecipeType> JsonToRecipeType(JsonArray result) {
        List<RecipeType> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();
            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), RecipeType.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return gig;
    }

    public static List<Style> JsonToStyle(JsonArray result) {
        List<Style> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();
            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Style.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return gig;
    }

    public static UserProfile JsonToUserProfile(JsonObject result) {
        UserProfile userProfile = new UserProfile();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s").create();


            userProfile = gson.fromJson(result, UserProfile.class);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }

        return userProfile;
    }

    public static List<Grain> JsonToGrainList(JsonArray result) {
        List<Grain> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Grain.class));
            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<Hops> JsonToHopsList(JsonArray result) {
        List<Hops> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();
            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Hops.class));
            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<Yeast> JsonToYeastList(JsonArray result) {
        List<Yeast> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();
            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Yeast.class));
            }

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<RecipeGrain> JsonToRecipeGrainList(JsonArray result) {
        List<RecipeGrain> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), RecipeGrain.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static KeyValuepair JsonToKeyValuepair(JsonObject result) {
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            return gson.fromJson(result, KeyValuepair.class);

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
            return null;
        }
    }

    public static List<UnitOfMeasure> JsonToUnitOfMeasureList(JsonArray result) {
        List<UnitOfMeasure> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), UnitOfMeasure.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<GrainUse> JsonToGrainUseList(JsonArray result) {
        List<GrainUse> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), GrainUse.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<Country> JsonToCountryList(JsonArray result) {
        List<Country> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Country.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<SrmColorKey> JsonToSrmColorKeyList(JsonArray result) {
        List<SrmColorKey> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), SrmColorKey.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<HopsUse> JsonToHopsUseList(JsonArray result) {
        List<HopsUse> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), HopsUse.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<HopsForm> JsonToHopsFormList(JsonArray result) {
        List<HopsForm> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), HopsForm.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<Laboratory> JsonToLaboratoryList(JsonArray result) {
        List<Laboratory> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), Laboratory.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<RecipeHop> JsonToIngredientHopList(JsonArray result) {
        List<RecipeHop> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), RecipeHop.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<RecipeYeast> JsonToIngredientYeastList(JsonArray result) {
        List<RecipeYeast> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), RecipeYeast.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }

    public static List<RecipeInstruction> JsonToInstructionList(JsonArray result) {
        List<RecipeInstruction> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), RecipeInstruction.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }


    public static List<RecipeImage> JsonToImageList(JsonArray result) {
        List<RecipeImage> gig = new Vector<>();
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.setDateFormat("s aa").create();

            for (int i = 0; i < result.size(); i++) {
                gig.add(gson.fromJson(result.get(i).getAsJsonObject(), RecipeImage.class));
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, e.getMessage());
            }
        }
        return gig;
    }


}
