package ratio.com.marvelQ.entities;

/**
 * Created by FranciscoPR on 20/10/16.
 */
public class LetterOffset {
    private char letter;
    private int offset;

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        boolean equals = false;
        if(o.getClass()!=LetterOffset.class){
            return equals;
        }
        LetterOffset letterOffset = (LetterOffset) o;
        if(letter==letterOffset.getLetter()&&offset==letterOffset.getOffset()){
            equals = true;
            return equals;
        }


        return equals;
    }
}
