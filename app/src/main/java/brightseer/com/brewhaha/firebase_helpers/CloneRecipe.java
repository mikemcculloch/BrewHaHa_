package brightseer.com.brewhaha.firebase_helpers;

import android.util.Log;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import brightseer.com.brewhaha.BrewSharedPrefs;
import brightseer.com.brewhaha.BuildConfig;
import brightseer.com.brewhaha.Constants;
import brightseer.com.brewhaha.helper.Utilities;
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
public class CloneRecipe {
    private Firebase rootRef;
    private String nFeedKey;

    public CloneRecipe() {
        rootRef = new Firebase(Constants.fireBaseRoot);
    }

    public void Clone(String feedKey, String feedName,  View parentView) {
        try {
            CloneMainFeedItem(feedKey, feedName, parentView);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
            throw ex;
        }
    }

    private void CloneMainFeedItem(final String feedKey, String feedName, final View parentView) {
        try {
            Firebase refFeed = rootRef.child(Constants.fbUserFeeds).child(feedName).child(feedKey);
            refFeed.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MainFeedItem mainFeedItem = dataSnapshot.getValue(MainFeedItem.class);
                    if (mainFeedItem != null) {
                        String newTitle = "Clone: " + mainFeedItem.getTitle();
                        mainFeedItem.setTitle(newTitle);
                        mainFeedItem.setKey("");
                        mainFeedItem.setCloneKey(feedKey);

                        FirebaseCrud firebaseCrud = new FirebaseCrud();
                        nFeedKey = firebaseCrud.AddUserFeed(mainFeedItem);

                        CloneRecipeDetail(feedKey);
                        CloneRecipeGrain(feedKey);
                        CloneRecipeHop(feedKey);
                        CloneRecipeYeast(feedKey);
                        CloneRecipeInstruction(feedKey);
                        CloneRecipeImage(feedKey);

                        View.OnClickListener undoListner = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseCrud firebaseCrud = new FirebaseCrud();
                                firebaseCrud.DeleteRecipe(nFeedKey, feedKey);
                            }
                        };
                        Utilities.RunSnackBar(parentView, "A copy has been added to your recipes.", undoListner);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void CloneRecipeDetail(final String feedKey) {
        try {
            Firebase refRecipeDetail = rootRef.child(Constants.fbRecipeDetail).child(feedKey);

            refRecipeDetail.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RecipeDetail recipeDetail = dataSnapshot.getValue(RecipeDetail.class);
                    if (recipeDetail != null) {
                        recipeDetail.setOwnerEmail(BrewSharedPrefs.getEmailAddress());

                        FirebaseCrud firebaseCrud = new FirebaseCrud();
                        firebaseCrud.AddRecipeDetail(recipeDetail, nFeedKey);

                        firebaseCrud.AddCloneRecipe(feedKey, nFeedKey);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void CloneRecipeGrain(String feedKey) {
        try {
            Firebase ref = rootRef.child(Constants.fbIngredients).child(feedKey).child(Constants.fbGrains);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        RecipeGrain recipeGrain = postSnapshot.getValue(RecipeGrain.class);
                        if (recipeGrain != null) {
                            FirebaseCrud firebaseCrud = new FirebaseCrud();
                            firebaseCrud.AddRecipeGrain(recipeGrain, nFeedKey);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void CloneRecipeHop(String feedKey) {
        try {
            Firebase ref = rootRef.child(Constants.fbIngredients).child(feedKey).child(Constants.fbHops);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        RecipeHop recipeHop = postSnapshot.getValue(RecipeHop.class);
                        if (recipeHop != null) {
                            FirebaseCrud firebaseCrud = new FirebaseCrud();
                            firebaseCrud.AddRecipeHop(recipeHop, nFeedKey);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void CloneRecipeYeast(String feedKey) {
        try {
            Firebase ref = rootRef.child(Constants.fbIngredients).child(feedKey).child(Constants.fbYeasts);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        RecipeYeast recipeYeast = postSnapshot.getValue(RecipeYeast.class);
                        if (recipeYeast != null) {
                            FirebaseCrud firebaseCrud = new FirebaseCrud();
                            firebaseCrud.AddRecipeYeast(recipeYeast, nFeedKey);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void CloneRecipeInstruction(String feedKey) {
        try {
            Firebase ref = rootRef.child(Constants.fbDirections).child(feedKey);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        RecipeInstruction recipeInstruction = postSnapshot.getValue(RecipeInstruction.class);
                        if (recipeInstruction != null) {
                            FirebaseCrud firebaseCrud = new FirebaseCrud();
                            firebaseCrud.AddRecipeInstruction(recipeInstruction, nFeedKey);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

    private void CloneRecipeImage(String feedKey) {
        try {
            Firebase ref = rootRef.child(Constants.fbImages).child(feedKey);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        RecipeImage recipeImage = postSnapshot.getValue(RecipeImage.class);
                        if (recipeImage != null) {
                            FirebaseCrud firebaseCrud = new FirebaseCrud();
                            firebaseCrud.AddRecipeImage(recipeImage, nFeedKey);
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                Log.e(Constants.LOG, ex.getMessage());
            }
        }
    }

}
