package ratio.com.marvelQ.Score.event;

import org.json.JSONArray;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public class ScoreEvent {

    private boolean success;
    private String error;
    private int type;
    private JSONArray scores;

    public static final int SCORES = 3;

    public JSONArray getScores() {
        return scores;
    }

    public void setScores(JSONArray scores) {
        this.scores = scores;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
