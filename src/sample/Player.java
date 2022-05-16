package sample;

public class Player {
    private int score;
    private int xCore;
    private int yCore;
    private boolean isSet;
    private String name;

    public Player(String name) {
        this.name=name;
        this.score =0;
        this.isSet=false;
        this.xCore=-1;
        this.yCore=-1;
    }

    public void setCoordinates(int x,int y){
        this.xCore=x;
        this.yCore=y;
        isSet=true;
    }

    public void newStar(){
        this.score++;
    }

    public int getxCore() {
        return xCore;
    }

    public int getyCore() {
        return yCore;
    }

    public int getScore() {
        return score;
    }

    public boolean isSet(){
        return this.isSet;
    }

    public boolean isIn(int x,int y){
        if(this.xCore==x&&this.yCore==y)
            return true;
        return false;
    }

    public String getName() {
        return name;
    }
}
