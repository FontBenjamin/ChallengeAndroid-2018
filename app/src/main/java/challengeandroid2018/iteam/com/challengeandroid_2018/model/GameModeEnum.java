package challengeandroid2018.iteam.com.challengeandroid_2018.model;

/**
 * Created by Marianna on 16/03/2018.
 */

public enum GameModeEnum {

    NORMAL(0),
    SPEED(1);

    private int num;

    GameModeEnum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }


}
