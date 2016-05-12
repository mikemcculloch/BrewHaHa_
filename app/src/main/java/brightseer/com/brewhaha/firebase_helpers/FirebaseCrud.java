package brightseer.com.brewhaha.firebase_helpers;

import android.util.Log;

import com.firebase.client.Firebase;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.models.MainFeedItem;
import brightseer.com.brewhaha.models.RecipeDetail;
import brightseer.com.brewhaha.models.RecipeGrain;
import brightseer.com.brewhaha.models.RecipeHop;
import brightseer.com.brewhaha.models.RecipeImage;
import brightseer.com.brewhaha.models.RecipeInstruction;
import brightseer.com.brewhaha.models.RecipeYeast;

/**
 * Created by wooan on 5/10/2016.
 */
public class FirebaseCrud {
    private Firebase rootRef;

    public FirebaseCrud() {
        rootRef = new Firebase(Constants.fireBaseRoot);
    }

    public String AddUserFeed(MainFeedItem mainFeedItem) {
        try {
            String feedKey = "";
            if (!BrewSharedPrefs.getEmailAddress().isEmpty()) {
                Firebase ref = rootRef.child(Constants.fbUserFeeds).child(BrewSharedPrefs.getEmailAddress());
                Firebase refFeedPush = ref.push();
                refFeedPush.setValue(mainFeedItem);
                feedKey = refFeedPush.getKey();
                mainFeedItem.setKey(feedKey);

                Firebase theChild = ref.child(feedKey);

                Map<String, Object> keyValue = new HashMap<String, Object>();
                keyValue.put("key", feedKey);
                theChild.updateChildren(keyValue);
            }
            return feedKey;
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            return "";
        }
    }

    public void AddRecipeDetail(RecipeDetail recipeDetail, String feedKey) {
        try {
            if (!BrewSharedPrefs.getEmailAddress().isEmpty()) {
                Firebase refDetail = rootRef.child(Constants.fbRecipeDetail).child(feedKey);
                refDetail.setValue(recipeDetail);
            }
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void AddRecipeGrain(RecipeGrain recipeGrain, String feedKey) {
        try {
            ///ADD NEW RecipeGrain//////////////////
            Firebase refGrain = rootRef.child(Constants.fbIngredients).child(feedKey).child(Constants.fbGrains);
            Firebase refGrainPush = refGrain.push();

            refGrainPush.setValue(recipeGrain);
            String postId = refGrainPush.getKey();
            recipeGrain.setKey(postId);

            Firebase theChild = refGrain.child(postId);
            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void AddRecipeHop(RecipeHop recipeHop, String feedKey) {
        try {
            Firebase refHop = rootRef.child(Constants.fbIngredients).child(feedKey).child(Constants.fbHops);
            Firebase refHopPush = refHop.push();

            refHopPush.setValue(recipeHop);

            String postId = refHopPush.getKey();
            recipeHop.setKey(postId);
            Firebase theChild = refHop.child(postId);
            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void AddRecipeYeast(RecipeYeast recipeYeast, String feedKey) {
        try {
            Firebase refYeast = rootRef.child(Constants.fbIngredients).child(feedKey).child(Constants.fbYeasts);
            Firebase refYeastPush = refYeast.push();

            refYeastPush.setValue(recipeYeast);

            String postId = refYeastPush.getKey();
            recipeYeast.setKey(postId);

            Firebase theChild = refYeast.child(postId);
            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void AddRecipeInstruction(RecipeInstruction recipeInstruction, String feedKey) {
        try {
            Firebase refInstructions = rootRef.child(Constants.fbDirections).child(feedKey);
            Firebase pushInst = refInstructions.push();

            pushInst.setValue(recipeInstruction);

            String postId = pushInst.getKey();
            recipeInstruction.setKey(postId);
            Firebase theChild = refInstructions.child(postId);
            Map<String, Object> keyValue = new HashMap<String, Object>();
            keyValue.put("key", postId);
            theChild.updateChildren(keyValue);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void AddRecipeImage(RecipeImage recipeImage, String feedKey) {
        try {
            Firebase ref = rootRef.child(Constants.fbImages).child(feedKey);
            ref.push().setValue(recipeImage);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    public void AddCloneRecipe(String feedKey, String nFeedKey) {
        try {//could also be sharedWith
            Firebase refClone = rootRef.child(Constants.fbClonedList).child(feedKey).child(BrewSharedPrefs.getEmailAddress());

            Map<String, Object> clonedList = new HashMap<String, Object>();
            clonedList.put("cloneFeedKey", nFeedKey);
            clonedList.put("dateCreated", DateTime.now().toString());
            refClone.setValue(clonedList);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            throw ex;
        }
    }

    public void DeleteRecipe(String feedKey, String parentKey) {
        try {
            Firebase refUserFeed = rootRef.child(Constants.fbUserFeeds).child(BrewSharedPrefs.getEmailAddress()).child(feedKey);
            refUserFeed.removeValue();

            Firebase refInstruction = rootRef.child(Constants.fbDirections).child(feedKey);
            refInstruction.removeValue();

            Firebase refDetail = rootRef.child(Constants.fbRecipeDetail).child(feedKey);
            refDetail.removeValue();

            Firebase refIngredients = rootRef.child(Constants.fbIngredients).child(feedKey);
            refIngredients.removeValue();

            Firebase refImages = rootRef.child(Constants.fbImages).child(feedKey);
            refImages.removeValue();

            Firebase refComments = rootRef.child(Constants.fbComments).child(feedKey);
            refComments.removeValue();

            Firebase refCloneList = rootRef.child(Constants.fbClonedList).child(parentKey).child(BrewSharedPrefs.getEmailAddress());
            refCloneList.removeValue();
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            throw ex;
        }
    }
}
