package challengeandroid2018.iteam.com.challengeandroid_2018.model;

/**
 * Created by Marianna on 16/03/2018.
 */

public class Score {

    private int idScore;
    private String pseudo;
    private int score;

    public Score( String pseudo, int score) {

        this.pseudo = pseudo;
        this.score = score;
    }

    public int getIdScore() {
        return idScore;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getScore() {
        return score;
    }



    public String toString(){
        return this.pseudo+"\n" + this.score;
    }
}
