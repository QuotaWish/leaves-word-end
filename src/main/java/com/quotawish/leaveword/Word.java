public class Word {
    private String word;
    private int length;
    private boolean isCorrect;

    public Word(String word, int length, boolean isCorrect) {
        this.word = word;
        this.length = length;
        this.isCorrect = isCorrect;
    }

    public String getWord() {
        return word;
    }

    public int getLength() {
        return length;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}