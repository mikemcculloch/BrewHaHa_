package brightseer.com.brewhaha.firebase_helpers;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
public class firebase_crud {
    Firebase rootRef;

    public firebase_crud() {
        rootRef = new Firebase(Constants.fireBaseRoot);
    }

    public boolean CloneRecipe(String feedKey) {
        try {
            MainFeedItem mainFeedItem = GetMainFeedItem(feedKey);
            if (mainFeedItem != null) {
                String newTitle = "Clone: " + mainFeedItem.getTitle();
                mainFeedItem.setTitle(newTitle);
                mainFeedItem.setKey("");
                String nFeedKey = AddUserFeed(mainFeedItem);

                RecipeDetail recipeDetail = GetRecipeDetail(feedKey);
                if (recipeDetail != null) {
                    recipeDetail.setOwnerEmail(BrewSharedPrefs.getEmailAddress());
                    AddRecipeDetail(recipeDetail, nFeedKey);
                }


                AddCloneRecipe(feedKey, nFeedKey);
                return true;
            }
            return false;
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            throw ex;
        }
    }

    private MainFeedItem GetMainFeedItem(String feedKey) {
        try {

            Firebase refFeed = rootRef.child(Constants.fbUserFeeds).child(Constants.fbPublicFeeds).child(feedKey);
            refFeed.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MainFeedItem mainFeedItem = dataSnapshot.getValue(MainFeedItem.class);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            return null;
        }
    }

    private String AddUserFeed(MainFeedItem mainFeedItem) {
        try {
            String feedKey = "";
            if (!BrewSharedPrefs.getEmailAddress().isEmpty()) {
                Firebase refFeedPush = rootRef.child(Constants.fbUserFeeds).child(BrewSharedPrefs.getEmailAddress()).push();
                refFeedPush.setValue(mainFeedItem);
                feedKey = refFeedPush.getKey();
                mainFeedItem.setKey(feedKey);

                Firebase theChild = rootRef.child(feedKey);

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

    private RecipeDetail GetRecipeDetail(String feedKey) {
        try {

            Firebase refRecipeDetail = rootRef.child(Constants.fbUserFeeds).child(Constants.fbRecipeDetail).child(feedKey);

            refRecipeDetail.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RecipeDetail recipeDetail = dataSnapshot.getValue(RecipeDetail.class);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            return null;
        }
    }

    private void AddRecipeDetail(RecipeDetail recipeDetail, String feedKey) {
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

    private void AddCloneRecipe(String feedKey, String nFeedKey) {
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
            Firebase theChild = rootRef.child(postId);
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
}
