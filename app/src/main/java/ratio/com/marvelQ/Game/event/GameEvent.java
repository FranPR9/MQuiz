package ratio.com.marvelQ.Game.event;

import ratio.com.marvelQ.entities.Option;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public class GameEvent {


    private Option[] options;
    private int type;
    private String error;
    private boolean success;
    private Option option;

    public static final int SAVE_SCORE = 4;
    public static final int OPTIONS = 5;

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Option[] getOptions() {
        return options;
    }

    public void setOptions(Option[] options) {
        this.options = options;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
