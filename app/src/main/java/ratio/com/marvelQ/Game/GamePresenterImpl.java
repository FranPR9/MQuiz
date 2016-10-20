package ratio.com.marvelQ.Game;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ratio.com.marvelQ.Game.event.GameEvent;
import ratio.com.marvelQ.Game.ui.GameView;
import ratio.com.marvelQ.entities.Option;
import ratio.com.marvelQ.entities.LetterOffset;
import ratio.com.marvelQ.lib.EventBus;

/**
 * Created by FranciscoPR on 18/10/16.
 */
public class GamePresenterImpl implements GamePresenter {
    private static final String TAG = "GamePresenter";
    private EventBus eventBus;
    private GameView view;
    private GameInteractor interactor;
    private List<Option> options;
    private Random random;
    private String privatek, publick;
    private boolean firstOption=true; //variable that will helpme now if it is the first option after, a new photo is render.
    private List<LetterOffset> combinations;

    public GamePresenterImpl(EventBus eventBus, GameView view, GameInteractor interactor, List<Option> options) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
        this.options = options;
        random = new Random();
        this.combinations = new ArrayList<LetterOffset>();
    }

    @Override
    public void OnCreate() {
        eventBus.register(this);
    }

    @Override
    public void OnDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void getOptions(String private_apikey, String public_apikey) {
        view.showProgress();
        privatek =private_apikey;
        publick = public_apikey;

        for(int i=0;i<4;i++){
            if(!getOption(private_apikey, public_apikey))i--;
        }


    }

    public boolean getOption(String private_apikey, String public_apikey) {

        char letter = getLetter();
        int offset = getOffset(letter);
        while (checkCombinationRepeated(letter,offset)){
            letter = getLetter();
            offset = getOffset(letter);
        }

        String ts = getTS();
        String hash = "";
        try {
            hash = getHash(ts+private_apikey+public_apikey).toString();
            interactor.executeOptions(String.valueOf(letter), offset, ts, public_apikey, hash);
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean checkCombinationRepeated(char letter, int offset) {
        if(combinations.size()<=1) return false;

        LetterOffset aux =  new LetterOffset();
        aux.setLetter(letter);
        aux.setOffset(offset);
        for(int i=0;i<combinations.size();i++){
            if(aux.equals(combinations.get(i))){
                combinations.add(aux);
               return true;
            }
        }

        return false;

    }

    @Override
    @Subscribe
    public void OptionsRespond(GameEvent event) {
        Log.d(TAG,event.isSuccess()+" type:"+event.getType());
        if(event.isSuccess()){
            switch (event.getType()){
                case GameEvent.OPTIONS:
                    checkIfFirstOption();
                    setOptions(event.getOption());
                    break;
                case GameEvent.SAVE_SCORE:
                    view.hideProgress();
                    view.showSuccessSavingScore();
                    view.showShareDialog();
                    break;
            }
        }else{
            switch (event.getType()){
                case GameEvent.OPTIONS:
                    view.hideProgress();
                    view.errorOnOptions();
                    view.showErrorButton();
                    break;
                case GameEvent.SAVE_SCORE:
                    view.hideProgress();
                    view.showErrorSavingScore();
                    view.showShareDialog();
                    break;
            }
        }
    }

    private void checkIfFirstOption() {
        Log.d(TAG,"firstoption:"+firstOption);
        if(firstOption)
        {
            options.clear();
            combinations.clear();
            firstOption = false;
        }
        //options are clear
        //because when a the user picks an option the other option are removed
        //except for the picked option. This method will clear now the options list.
    }

    private void setOptions(Option option) {
        if(options.size()<=4){
            options.add(option);
            if(options.size()==4){
                if(checkIfImageExistsnSetAnswere()){
                    swapAnswer();
                    view.hideProgress();
                    view.presentImage(options.get(answer_position).getImg());
                    view.showOptions(options);
                    firstOption = true;
                }
                else{
                    firstOption = true;
                    getOptions(privatek,publick);
                }
            }
        }

    }

    private int answer_position=0;

    private boolean checkIfImageExistsnSetAnswere() {
        for (int i =0;i<options.size();i++){
            if(options.get(i).isImgexists()){
                options.get(i).setAnswer(true);
                answer_position = i;
                return true;
            }
        }

        return false;
    }

    private void swapAnswer() {
        int answ = random.nextInt(3);//%4;

        Option aux = options.get(answ);
        options.set(answ,options.get(answer_position));
        options.set(answer_position,aux);
        Log.d("request","answere swap from:"+answer_position+" to:"+answ);
        answer_position = answ;
    }


    @Override
    public void saveScore(String id, int score) {
        view.showProgress();
        interactor.executeSaveScore(id, score);
    }

    @Override
    public void removeAllExceptSelected(int position) {




    }

    @Override
    public void clearOptions() {
        options.clear();
        combinations.clear();
    }

    public char getLetter() {

        char c= 65;

        Random random = new Random();
        int num = (random.nextInt(28)%26)+65;

        c = (char) num;

        Log.d("char",c+"");

        return  c;

    }

    public int getOffset(char C) {

        //Random random = new Random();
        int offset = 0;

        if(C==65)offset =  random.nextInt(79);
        if(C==66)offset =  random.nextInt(92);
        if(C==67)offset =  random.nextInt(99);
        if(C==68)offset =  random.nextInt(78);
        if(C==69)offset =  random.nextInt(33);
        if(C==70)offset =  random.nextInt(37);
        if(C==71)offset =  random.nextInt(56);
        if(C==72)offset =  random.nextInt(70);
        if(C==73)offset =  random.nextInt(38);
        if(C==74)offset =  random.nextInt(44);
        if(C==75)offset =  random.nextInt(34);
        if(C==76)offset =  random.nextInt(54);
        if(C==77)offset =  random.nextInt(152);
        if(C==78)offset =  random.nextInt(43);
        if(C==79)offset =  random.nextInt(19);

        if(C==80)offset =  random.nextInt(61);
        if(C==81)offset =  random.nextInt(8);
        if(C==82)offset =  random.nextInt(59);
        if(C==83)offset =  random.nextInt(196);
        if(C==84)offset =  random.nextInt(92);
        if(C==85)offset =  random.nextInt(21);
        if(C==86)offset =  random.nextInt(32);
        if(C==87)offset =  random.nextInt(57);
        if(C==88)offset =  random.nextInt(15);
        if(C==89)offset =  random.nextInt(4);
        if(C==90)offset =  random.nextInt(10);



        return  offset;
    }

    public String getTS() {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }


    public StringBuffer getHash(String hashstr) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte hash[] = hashstr.getBytes();
        byte[] digest;

        md.update(hash);
        digest = md.digest();
        StringBuffer sb;
        sb = new StringBuffer();


        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        //System.out.println("original:" + original);
        System.out.println("digested(hex):" + sb.toString());
        Log.d("digest",digest+"");

        return sb;
    }


}
