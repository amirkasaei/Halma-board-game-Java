import java.awt.*;
import java.util.ArrayList;

public class Player {
    public static ArrayList<Player> players = new ArrayList<Player>();
    private String name;
    private Color color;
    private boolean isTurn = false;
    private int moves = 0;

    void setMoves(int moves) {
        this.moves = moves;
    }

    int getMoves() {
        return moves;
    }

    void plusMove(){
        moves++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    static Player getPlayer(Color color) {
        for (Player player: players) {
            if (player.getColor().equals(color)) {
                return player;
            }
        }
        return null;
    }

    public Player(String name, Color color){
        setName(name);
        setColor(color);
        players.add(this);
    }
}
