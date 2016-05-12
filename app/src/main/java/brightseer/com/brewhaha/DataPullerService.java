package brightseer.com.brewhaha;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.JsonArray;
import brightseer.com.brewhaha.repository.DBHelper_BatchSize;
import brightseer.com.brewhaha.repository.DBHelper_Country;
import brightseer.com.brewhaha.repository.DBHelper_Difficulty;
import brightseer.com.brewhaha.repository.DBHelper_Grain;
import brightseer.com.brewhaha.repository.DBHelper_GrainUse;
import brightseer.com.brewhaha.repository.DBHelper_HopUse;
import brightseer.com.brewhaha.repository.DBHelper_Hops;
import brightseer.com.brewhaha.repository.DBHelper_HopsForm;
import brightseer.com.brewhaha.repository.DBHelper_Laboratory;
import brightseer.com.brewhaha.repository.DBHelper_RecipeType;
import brightseer.com.brewhaha.repository.DBHelper_SrmColorKey;
import brightseer.com.brewhaha.repository.DBHelper_Style;
import brightseer.com.brewhaha.repository.DBHelper_UnitOfMeasure;
import brightseer.com.brewhaha.repository.DBHelper_Yeast;
import brightseer.com.brewhaha.repository.JsonToObject;

/**
 * Created by mccul_000 on 12/11/2014.
 */
public class DataPullerService extends IntentService {
    DBHelper_RecipeType repoRecipeType = new DBHelper_RecipeType(this);
    DBHelper_Difficulty repoDifficulty = new DBHelper_Difficulty(this);
    DBHelper_BatchSize repoBatchSize = new DBHelper_BatchSize(this);
    DBHelper_Style repoStyle = new DBHelper_Style(this);
    DBHelper_Grain repoGrain = new DBHelper_Grain(this);
    DBHelper_Hops repoHops = new DBHelper_Hops(this);
    DBHelper_Yeast repoYeast = new DBHelper_Yeast(this);
    DBHelper_UnitOfMeasure repoUnitOfMeasure = new DBHelper_UnitOfMeasure(this);
    DBHelper_GrainUse repoGrainUse = new DBHelper_GrainUse(this);
    DBHelper_Country repoCountry = new DBHelper_Country(this);
    DBHelper_SrmColorKey repoSrmColorKey = new DBHelper_SrmColorKey(this);

    DBHelper_HopUse repoHopsUse = new DBHelper_HopUse(this);
    DBHelper_HopsForm repoHopsForm = new DBHelper_HopsForm(this);
    DBHelper_Laboratory repoLaboratory = new DBHelper_Laboratory(this);

    boolean forceUpdate = false;

    public DataPullerService() {
        super("DataPullerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        forceUpdate = intent.getExtras().getBoolean(Constants.exForceUpdate, false);

//        InitRecipeType();
////        InitDifficulty();
//        InitBatchSize();
//        InitStyle();
//        InitGrain();
//        InitHops();
//        InitYeast();
//
//        InitUnitOfMeasure();
//        InitGrainUse();
//        InitCountry();
//        InitSrmColorKey();
//        InitHopsUse();
//        InitHopsForm();
//        InitLaboratory();

//        UpdateUserProfile();
    }

//    private void InitGrain() {
//        if (repoGrain.getGrainCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_Grain);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetAllGrain)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoGrain.insertGrain(JsonToObject.JsonToGrainList(result));
//                                    repoGrain.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitHops() {
//        if (repoHops.getHopsCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_Hops);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetAllHops)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoHops.insertHops(JsonToObject.JsonToHopsList(result));
//                                    repoHops.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitYeast() {
//        if (repoYeast.getYeastCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_Yeast);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetAllYeast)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoYeast.insertYeast(JsonToObject.JsonToYeastList(result));
//                                    repoYeast.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitRecipeType() {
//        if (repoRecipeType.getRecipeTypeCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_RecipeType);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetRecipeTypeMList)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoRecipeType.insertRecipeType(JsonToObject.JsonToRecipeType(result));
//                                    repoRecipeType.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitDifficulty() {
//        if (repoDifficulty.getDifficultyCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_Difficulty);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetDifficultyMList)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoDifficulty.insertDifficulty(
//                                            JsonToObject.JsonToDifficulty(result));
//                                    repoDifficulty.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitBatchSize() {
//        if (repoBatchSize.getBatchSizeCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_BatchSize);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetBatchSizeList)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoBatchSize.insertBatchSize(
//                                            JsonToObject.JsonToBatchSize(result));
//                                    repoBatchSize.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitStyle() {
//        if (repoStyle.getStyleCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_Style);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetStyleMList)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoStyle.insertStyle(
//                                            JsonToObject.JsonToStyle(result));
//                                    repoStyle.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
////    private void UpdateUserProfile() {
////        if (BrewSharedPrefs.getIsUserLoggedIn()) {
////            Ion.with(getApplicationContext())
////                    .load(Constants.wcfGetUserProfile + BrewSharedPrefs.getUserKey())
////                    .asJsonObject()
////                    .setCallback(new FutureCallback<JsonObject>() {
////                        @Override
////                        public void onCompleted(Exception e, JsonObject result) {
////                            try {
////                                if (result != null) {
////                                    UserProfile userProfile = JsonToObject.JsonToUserProfile(result);
////                                    if (userProfile != null) {
////                                        BrewSharedPrefs.setUserProfileImageUrl(userProfile.getImageUrl());
////                                    }
////                                }
////                            } catch (Exception ex) {
////                                if (BuildConfig.DEBUG) {
////                                    Log.e(Constants.LOG, ex.getMessage());
////                                }
////                            }
////                        }
////                    });
////
////        }
////    }
//
//    private void InitUnitOfMeasure() {
//        if (repoUnitOfMeasure.getUnitOfMeasureCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_UnitOfMeasure);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetUnitOfMeasure)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoUnitOfMeasure.insertUnitOfMeasure(JsonToObject.JsonToUnitOfMeasureList(result));
//                                    repoUnitOfMeasure.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitGrainUse() {
//        if (repoGrainUse.getGrainUseCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_GrainUse);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetGrainUse)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoGrainUse.insertGrainUse(JsonToObject.JsonToGrainUseList(result));
//                                    repoGrainUse.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitCountry() {
//        if (repoCountry.getCountryCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_Country);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetCounties)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoCountry.insertCountry(JsonToObject.JsonToCountryList(result));
//                                    repoCountry.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitSrmColorKey() {
//        if (repoSrmColorKey.getSrmColorKeyCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_SrmColorKey);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetColorKeys)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoSrmColorKey.insertSrmColorKey(JsonToObject.JsonToSrmColorKeyList(result));
//                                    repoSrmColorKey.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitHopsUse() {
//        if (repoHopsUse.getHopsUseCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_HopsUse);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetHopsUse)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoHopsUse.insertHopsUse(JsonToObject.JsonToHopsUseList(result));
//                                    repoHopsUse.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitHopsForm() {
//        if (repoHopsForm.getHopsFormCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_HopsForm);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetHopsForm)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoHopsForm.insertHopsForm(JsonToObject.JsonToHopsFormList(result));
//                                    repoHopsForm.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
//    private void InitLaboratory() {
//        if (repoLaboratory.getLaboratoryCount() <= 0 || forceUpdate) {
//            getApplicationContext().deleteDatabase(Constants.DATABASE_NAME_Laboratory);
//            Ion.with(getApplicationContext())
//                    .load(Constants.wcfGetLaboratories)
//                    .asJsonArray()
//                    .setCallback(new FutureCallback<JsonArray>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonArray result) {
//                            try {
//                                if (result != null) {
//                                    repoLaboratory.insertLaboratory(JsonToObject.JsonToLaboratoryList(result));
//                                    repoLaboratory.close();
//                                }
//                            } catch (Exception ex) {
//                                if (BuildConfig.DEBUG) {
//                                    Log.e(Constants.LOG, ex.getMessage());
//                                }
//                            }
//                        }
//                    });
//        }
//    }
//
}
