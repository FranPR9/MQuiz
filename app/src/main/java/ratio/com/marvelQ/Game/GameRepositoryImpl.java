package ratio.com.marvelQ.Game;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ratio.com.marvelQ.api.AsyncResponse;
import ratio.com.marvelQ.Game.event.GameEvent;
import ratio.com.marvelQ.entities.Option;
import ratio.com.marvelQ.lib.EventBus;
import ratio.com.marvelQ.api.spiderWeb;

/**
 * Created by FranciscoPR on 18/10/16.
 */

public class GameRepositoryImpl implements GameRepository, AsyncResponse {
    private static final String TAG = "GameRepository";
    EventBus eventBus;
    spiderWeb spiderWeb;


    public GameRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getMarvelApiCharacter(String letter, int offset, String ts,String apikey,String hash  ) {
        spiderWeb = new spiderWeb(this,"GET");
        spiderWeb.execute(GameEvent.OPTIONS+"","http://gateway.marvel.com:80/v1/public/characters?nameStartsWith="+letter+"&limit="+1+"&offset="+offset+"&ts="+ts+"&apikey="+apikey+"&hash="+hash);
    }

    @Override
    public void saveUserScore(String id, int score) {
        String name="";
        spiderWeb = new spiderWeb(this,"POST");
        //spiderWeb.execute(GameEvent.SAVE_SCORE+"","http://superheroe.uphero.com/insert_score.php?name="+name+"&score="+score+"&num="+id);
        String parameters = "token="+id+"&score="+score;
        spiderWeb.execute(GameEvent.SAVE_SCORE+"","score",parameters);
    }

    @Override
    public void processFinish(String output, int type, boolean success) {
        Log.d(TAG,"type:"+type);

        switch (type){
            case GameEvent.OPTIONS:
                postEvent(output,type,success);
                break;
            case GameEvent.SAVE_SCORE:
                postEventScore(output, type, success);
                break;
        }

    }

    private void postEventScore(String output, int type, boolean success){
        GameEvent event = new GameEvent();
        event.setSuccess(success);
        event.setType(type);
        if(!success)event.setError(output);

        eventBus.post(event);
    }


    private void postEvent(String output, int type, boolean success) {
        GameEvent event = new GameEvent();
        event.setSuccess(success);
        event.setType(type);
        if(success){
            try {
                Option option = setOptionfromOutput(output);
                if(option!=null)
                    event.setOption(option);
                else{
                    event.setSuccess(false);
                    event.setError("no option");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                event.setSuccess(false);
                event.setError(e.getMessage());
            }

        }else{
            event.setError(output);
        }

        eventBus.post(event);
    }

    private Option setOptionfromOutput(String output) throws JSONException {
        JSONObject jsonObject = new JSONObject(output);
        JSONArray jsonopt = jsonObject.getJSONObject("data").getJSONArray("results");
        if(jsonopt.length()>0){
            Option option = new Option(jsonopt.getJSONObject(0).getString("name"),jsonopt.getJSONObject(0).getJSONObject("thumbnail").getString("path")+"/portrait_fantastic.jpg",false,true);
            return option;
        }

        return null;
    }
}
