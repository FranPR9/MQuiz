package ratio.com.marvelQ.entities;

import android.util.Log;

/**
 * Created by FernandoV on 01/05/16.
 */
public class Option {

    private String title;
    private String img;
    private boolean answer;
    private boolean imgexists;

    public Option(String title, String img, boolean answer,boolean imgexists){

        this.title=title;
        this.img=img;
        this.answer=answer;
        this.imgexists =imgexists;
        checkImg();
        printData();

    }

    public void checkImg()
    {
        if(img.contains("image_not_available")){
            imgexists = false;
        }

    }

    public void printData()
    {
        Log.d("data",title+" "+img+" "+answer+" "+imgexists);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public boolean isImgexists() {
        return imgexists;
    }

    public void setImgexists(boolean imgexists) {
        this.imgexists = imgexists;
    }
}
