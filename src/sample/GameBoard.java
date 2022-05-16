package sample;


import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameBoard {
    private Stage window;
    private BorderPane layout;
    private GridPane grid;

    private Player redPlayer;
    private Player bluePlayer;

    private VBox upMenu;
    private Label title;
    private Label resultLabel;
    private Label scoreLabelOfRed;
    private Label scoreLabelOfBlue;
    private Label problemLabel;

    private VBox rightMenu;
    private Button addStar;
    private Button addWall;
    private Button setRed;
    private Button setBlue;
    private Button start;

    private String lastCommand;
    private Button[][] house;
    private int turn;
    private int starNum;


    public GameBoard(int rows,int columns) {

        turn=0;
        starNum = 0;
        lastCommand="";
        house=new Button[rows+10][columns+10];
        redPlayer=new Player("Red");
        bluePlayer=new Player("Blue");
        layout=new BorderPane();

        setTopOfBoard();
        setRightOfBoard();
        GridBoard(rows,columns);

        layout.setCenter(grid);
        Scene scene=new Scene(layout,50*columns+150,50*rows+170);
        window=new Stage();
        window.setScene(scene);
        window.show();
    }

    private void setRightOfBoard() {
        rightMenu=new VBox();

        addStar=new Button("Add Star");
        addStar.setOnAction(event -> addNewStarToBoard());


        addWall=new Button("Add Wall");

        addWall.setOnAction(event -> addNewWallToBoard());
        setBlue=new Button("Set Blue Position");
        setBlue.setOnAction(event -> setPositionOfBlue());
        setRed=new Button("Set Red Position");
        setRed.setOnAction(event -> setPositionOfRed());
        start=new Button("Start");
        start.setOnAction(event -> startTheGame());
        addStar.setMinWidth(105);
        addStar.setMaxWidth(105);
        addWall.setMinWidth(105);
        setRed.setMinWidth(105);
        setBlue.setMinWidth(105);
        start.setMinWidth(105);
        rightMenu.getChildren().addAll(addStar,addWall,setBlue,setRed,start);
        rightMenu.setMargin(addStar,new Insets(0,8,8,0));
        rightMenu.setMargin(addWall,new Insets(0,8,8,0));
        rightMenu.setMargin(setBlue,new Insets(0,8,8,0));
        rightMenu.setMargin(setRed,new Insets(0,8,8,0));
        rightMenu.setMargin(start,new Insets(0,8,8,0));
        layout.setRight(rightMenu);
    }

    private void setTopOfBoard() {

        title=new Label("Red Vs Blue");

        scoreLabelOfBlue=new Label("Blue : "+bluePlayer.getScore());
        scoreLabelOfRed=new Label("Red : "+redPlayer.getScore());
        problemLabel=new Label("");

        upMenu=new VBox(10,title,scoreLabelOfBlue,scoreLabelOfRed,problemLabel);
        upMenu.setPadding(new Insets(10,20,0,20));
        layout.setTop(upMenu);

    }

    private void GridBoard(int rows,int columns){
        grid=new GridPane();
        grid.setPadding(new Insets(20,10,10,15));
        grid.setHgap(1);
        grid.setVgap(1);

        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                Image blank=new Image("image/blankpink.jpg");
                ImageView view=new ImageView(blank);
                view.setFitWidth(40);
                view.setFitHeight(40);


                house[i][j]=new Button("none",view);
                house[i][j].setMinWidth(50);
                house[i][j].setMinHeight(50);
                house[i][j].setMaxWidth(50);
                house[i][j].setMaxHeight(50);

                int x=i;
                int y=j;
                house[i][j].setOnAction(event -> clickedOnButtonOfGrid(x,y));
                GridPane.setConstraints(house[i][j],j,i);
                grid.getChildren().addAll(house[i][j]);
            }
        }
    }

    private void setPositionOfRed() {
        lastCommand="setRed";
    }

    private void setPositionOfBlue() {
        lastCommand="setBlue";
    }

    private void addNewWallToBoard() {
        lastCommand="newWall";
    }

    private void addNewStarToBoard() {
        lastCommand="newStar";
    }

    private void startTheGame() {
        if(redPlayer.isSet()&&bluePlayer.isSet()){
            lastCommand="start";
            addStar.setVisible(false);
            addWall.setVisible(false);
            setRed.setVisible(false);
            setBlue.setVisible(false);
            start.setVisible(false);
            message("Now just click in turns! first blue moves");
        }else{
            message("Set the players position!");
        }
    }

    private void clickedOnButtonOfGrid(int x,int y) {
        switch (lastCommand) {
            case "newStar":
                if(redPlayer.isIn(x,y)||bluePlayer.isIn(x,y)){
                    message("You can't make this star unless You change the player's position");
                }else{
                    if(!house[x][y].getText().equals("Star"))
                        starNum++;
                    message("");
                    changeDutyOfButton("Star",x,y,"image/star.png",40,40);
                }
                break;
            case "newWall":
                if(redPlayer.isIn(x,y)||bluePlayer.isIn(x,y)){
                    message("You can't make this wall unless You change the player's position");
                }else{
                    message("");
                    decreaseStarNum(x,y);
                    changeDutyOfButton("Wall",x,y,"image/wall.jpg",40,40);
                }
                break;
            case "setRed":
                if(bluePlayer.getxCore()==x&&bluePlayer.getyCore()==y){
                    message("Choose another place! Blue has taken this one");
                }
                else {
                    message("");
                    decreaseStarNum(x,y);
                    if(redPlayer.isSet()){
                        changeDutyOfButton("none",redPlayer.getxCore(),redPlayer.getyCore(),"image/blankpink.jpg",40,40);
                    }
                    redPlayer.setCoordinates(x,y);
                    changeDutyOfButton("Red",x,y,"image/red.jpg",40,40);
                }
                break;
            case "setBlue":
                if(redPlayer.getxCore()==x&&redPlayer.getyCore()==y){
                    message("Choose another place! Red has taken this one");
                }
                else{
                    message("");
                    decreaseStarNum(x,y);
                    if(bluePlayer.isSet()){
                        changeDutyOfButton("none",bluePlayer.getxCore(),bluePlayer.getyCore(),"image/blankpink.jpg",40,40);
                    }
                    bluePlayer.setCoordinates(x,y);
                    changeDutyOfButton("Blue",x,y,"image/blue.png",40,40);
                }
                break;
            case "start":
                message("");
                playTheGame(x,y);
                break;
            default:
                message("No command has clicked!");
        }
    }

    private void message(String s) {
        problemLabel.setText(s);
    }

    private void decreaseStarNum(int x,int y){
        if(house[x][y].getText().equals("Star"))
            starNum--;
    }

    private void changeDutyOfButton(String newName,int xCoordinate, int yCoordinate, String path,int width,int height) {
        house[xCoordinate][yCoordinate].setText(newName);
        Image image=new Image(path);
        ImageView imageView=new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        house[xCoordinate][yCoordinate].setGraphic(imageView);
    }

    private void playTheGame(int x,int y) {
        if(isEnded())
            return;
        if(turn%2==0){//blue's turn

            if(x==bluePlayer.getxCore()&&y==bluePlayer.getyCore()){
                turn++;
                sayTurn("Red");
            }
            else if(x==bluePlayer.getxCore()||y==bluePlayer.getyCore()){
                if(house[x][y].getText().equals("Red")){
                    forbidden("Blue");
                    return;
                }
                else if(x==bluePlayer.getxCore()){//in same row
                    if(y>bluePlayer.getyCore()){
                        for (int i = bluePlayer.getyCore()+1; i <=y ; i++) {
                            if(house[x][i].getText().equals("Wall")){
                                forbidden("Blue");
                                return;
                            }
                        }
                        for(int i= bluePlayer.getyCore(); i <=y ; i++){
                            receiveStar(bluePlayer,x,i);
                        }
                        changePositionOfPlayer(bluePlayer,x,y,"Red");
                    }
                    else {
                        for(int i=y;i<=bluePlayer.getyCore();i++){
                            if(house[x][i].getText().equals("Wall")){
                                forbidden("Blue");
                                return;
                            }
                        }
                        for(int i=y;i<=bluePlayer.getyCore();i++){
                           receiveStar(bluePlayer,x,i);
                        }
                        changePositionOfPlayer(bluePlayer,x,y,"Red");
                    }
                }
                else{//in same column
                    if(x>bluePlayer.getxCore()){
                        for(int i=bluePlayer.getxCore()+1;i<=x;i++){
                            if(house[i][y].getText().equals("Wall")){
                                forbidden("Blue");
                                return;
                            }
                        }
                        for(int i=bluePlayer.getxCore()+1;i<=x;i++){
                            receiveStar(bluePlayer,i,y);
                        }
                        changePositionOfPlayer(bluePlayer,x,y,"Red");
                    }
                    else{
                        for(int i=x;i<bluePlayer.getxCore();i++){
                            if(house[i][y].getText().equals("Wall")){
                                forbidden("Blue");
                                return;
                            }
                        }
                        for(int i=x;i<=bluePlayer.getxCore();i++){
                            receiveStar(bluePlayer,i,y);
                        }
                        changePositionOfPlayer(bluePlayer,x,y,"Red");
                    }
                }
            }
            else{
                forbidden("Blue");
            }

        }else{//red's turn
            if(x==redPlayer.getxCore()&&y==redPlayer.getyCore()){
                turn++;
                sayTurn("Blue");
            }
            else if(x==redPlayer.getxCore()||y==redPlayer.getyCore()){
                if(house[x][y].getText().equals("Blue")){
                    forbidden("Red");
                    return;
                }
                else if(x==redPlayer.getxCore()){//in same row
                    if(y>redPlayer.getyCore()){
                        for (int i = redPlayer.getyCore()+1; i <=y ; i++) {
                            if(house[x][i].getText().equals("Wall")){
                                forbidden("Red");
                                return;
                            }
                        }
                        for(int i= redPlayer.getyCore(); i <=y ; i++){
                            receiveStar(redPlayer,x,i);
                        }
                        changePositionOfPlayer(redPlayer,x,y,"Blue");
                    }
                    else {
                        for(int i=y;i<=redPlayer.getyCore();i++){
                            if(house[x][i].getText().equals("Wall")){
                                forbidden("Red");
                                return;
                            }
                        }
                        for(int i=y;i<=redPlayer.getyCore();i++){
                            receiveStar(redPlayer,x,i);
                        }
                        changePositionOfPlayer(redPlayer,x,y,"Blue");
                    }
                }
                else{//in same column
                    if(x>redPlayer.getxCore()){
                        for(int i=redPlayer.getxCore()+1;i<=x;i++){
                            if(house[i][y].getText().equals("Wall")){
                                forbidden("Red");
                                return;
                            }
                        }
                        for(int i=redPlayer.getxCore()+1;i<=x;i++){
                            receiveStar(redPlayer,i,y);
                        }
                        changePositionOfPlayer(redPlayer,x,y,"Blue");
                    }
                    else{
                        for(int i=x;i<redPlayer.getxCore();i++){
                            if(house[i][y].getText().equals("Wall")){
                                forbidden("Red");
                                return;
                            }
                        }
                        for(int i=x;i<=redPlayer.getxCore();i++){
                            receiveStar(redPlayer,i,y);
                        }
                        changePositionOfPlayer(redPlayer,x,y,"Blue");
                    }
                }
            }
            else{
                forbidden("Red");
            }
        }
        turn%=2;
    }

    private void sayResult(){
        scoreLabelOfRed.setText("Red  : "+redPlayer.getScore());
        scoreLabelOfBlue.setText("Blue : "+bluePlayer.getScore());
    }

    private boolean isEnded(){
        if(starNum==0){
            if(redPlayer.getScore()>bluePlayer.getScore())
               message("Game ended! Red is winner");
            else if(bluePlayer.getScore()>redPlayer.getScore())
                message("Game ended! Blue is winner");
            else
                message("Game ended! draw");
            return true;
        }
        return false;
    }

    private void forbidden(String name) {
        message("forbidden move! "+name+" try again!");
    }

    private void receiveStar(Player player, int x, int y) {
        if(!house[x][y].getText().equals("Star"))
            return;
        decreaseStarNum(x,y);
        player.newStar();
        changeDutyOfButton("none",x,y,"image/blankpink.jpg",40,40);
    }

    private void sayTurn(String name) {
        message(name+" make a move");
    }

    public void changePositionOfPlayer(Player player,int x,int y,String against){
        changeDutyOfButton("none",player.getxCore(),player.getyCore(),"image/blankpink.jpg",40,40);
        if(player.getName().equals("Red")){
            changeDutyOfButton(player.getName(),x,y,"image/red.jpg",40,40);
        }else {
            changeDutyOfButton(player.getName(),x,y,"image/blue.png",40,40);
        }
        player.setCoordinates(x,y);
        boolean t=isEnded();
        sayResult();
        if(!t)
            sayTurn(against);
        turn++;
    }
}

