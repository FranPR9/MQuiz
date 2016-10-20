package ratio.com.marvelQ.Score;

import org.json.JSONException;
import org.json.JSONObject;

import ratio.com.marvelQ.api.AsyncResponse;
import ratio.com.marvelQ.Score.event.ScoreEvent;
import ratio.com.marvelQ.lib.EventBus;
import ratio.com.marvelQ.api.spiderWeb;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public class ScoreRepositoryImpl implements ScoreRepository, AsyncResponse {

    spiderWeb request;
    EventBus eventBus;

    public ScoreRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;

    }

    @Override
    public void getScores(String token) {
        //request.execute(ScoreEvent.SCORES+"","http://superheroe.uphero.com/get_scores.php");
        request = new spiderWeb(this,"GET");
        request.execute(ScoreEvent.SCORES+"","score?token="+token);
    }

    @Override
    public void processFinish(String output, int type, boolean success) {
        PostEvent(output,type,success);
    }

    private void PostEvent(String output, int type, boolean success) {
        ScoreEvent event = new ScoreEvent();
        event.setType(type);
        event.setSuccess(success);
        if(success){
            output = output.replace("<!-- Hosting24 Analytics Code -->\n","");
            output = output.replace("<script type=\"text/javascript\" src=\"http://stats.hosting24.com/count.php\"></script>\n","");
            output = output.replace("<!-- End Of Analytics Code -->","");
            try {
                JSONObject scores = new JSONObject(output);
                event.setScores(scores.getJSONArray("response"));
            } catch (JSONException e) {
                e.printStackTrace();
                event.setSuccess(false);
                event.setError(e.getMessage());
            }
        }
        else{
            event.setError("error");
        }

        eventBus.post(event);
    }
}
