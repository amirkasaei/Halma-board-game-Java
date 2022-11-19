import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Button extends JButton {
    //for limiting the number of jumps and preventing stack overflow
    static int jump = 0;
    static ArrayList<Button> buttons = new ArrayList<Button>();
    boolean selected = false;
    private int x,y;
    private Color color = null;
    private Player player = null;
    private Halma halma;
    private int size;

    private Button getButton(int x, int y) {
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getx() == x && buttons.get(i).gety() == y) {
                return buttons.get(i);
            }
        }
        return null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color){
        this.color = color;
        try {
            if (color.equals(Color.BLUE)) {
                try {
                    File image = new File("blue.png");
                    BufferedImage blue = ImageIO.read(image);
                    //add piece
                    this.setIcon(new ImageIcon(blue.getScaledInstance(size,size, Image.SCALE_SMOOTH)));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }else {
                try {
                    File image = new File("red.png");
                    BufferedImage red = ImageIO.read(image);
                    //add piece
                    this.setIcon(new ImageIcon(red.getScaledInstance(size,size, Image.SCALE_SMOOTH)));
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        } catch (Exception e) {
            //set null if button is empty
            this.setIcon(null);
        }
    }

    public int getx(){
        return x;
    }

    public int gety(){
        return y;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

//  checking if game is finished and indicating the winner
    public void finish() {
        //for counting the number of pieces that crossed to the opponent's area
        int blue = 0, red = 0;
        //counting pieces
        for (Button btn: buttons) {
            try {
                if (btn.getColor().equals(Color.BLUE) && btn.getx() + btn.gety() > halma.getBoardSize() - 1) {
                    blue++;
                } else if (btn.getColor().equals(Color.RED) && btn.getx() + btn.gety() < halma.getBoardSize() - 1)
                    red++;
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        //checking if someone is the winner
        if (blue == (halma.getRowsOfPieces()*(halma.getRowsOfPieces()+1)/2)) {
            JOptionPane.showMessageDialog(null, Player.getPlayer(Color.BLUE).getName() + " won!!", "Winner", JOptionPane.INFORMATION_MESSAGE);
            try (FileWriter fileWriter = new FileWriter("records.txt", true)) {
                fileWriter.write(Player.getPlayer(Color.BLUE).getName() + "/" + Player.getPlayer(Color.BLUE).getMoves() + "\n");
            } catch (IOException e) {
                e.getStackTrace();
            }
            halma.dispose();
        } else if (red == (halma.getRowsOfPieces()*(halma.getRowsOfPieces()+1)/2)) {
            JOptionPane.showMessageDialog(null, Player.getPlayer(Color.RED).getName() + " won!!", "Winner", JOptionPane.INFORMATION_MESSAGE);
            try (FileWriter fileWriter = new FileWriter("records.txt", true)) {
                fileWriter.write(Player.getPlayer(Color.RED).getName() + "/" + Player.getPlayer(Color.RED).getMoves() + "\n");
            } catch (IOException e) {
                e.getStackTrace();
            }
            halma.dispose();
        }
    }

    public void move(int i){
        setColor(buttons.get(i).getColor());
        buttons.get(i).setColor(null);
        setPlayer(buttons.get(i).player);
        buttons.get(i).setPlayer(null);
        buttons.get(i).selected = false;

        //change turn
        player.setTurn(false);
        player.plusMove();
        for (Player subPlayer : Player.players) {
            if (subPlayer != player) {
                subPlayer.setTurn(true);
            }
        }
        finish();
    }

    public boolean canMove(int x, int y) {
        if ((x+1 == getx() && y+1 == gety()) || (x+1 == getx() && y-1 == gety()) ||
            (x-1 == getx() && y+1 == gety()) || (x-1 == getx() && y-1 == gety()) ||
            (x == getx() && y+1 == gety()) || (x == getx() && y-1 == gety()) ||
            (x+1 == getx() && y == gety()) || (x-1 == getx() && y == gety())) {
            return true;
        }
        return false;
    }

    public boolean canJump(int x, int y, int n) {
        jump++;
        if (jump > halma.getBoardSize()*halma.getBoardSize()/4) {
            return false;
        }

    //one step jump
        //right bottom
        if (getButton((x+1),(y+1)) != null ) {
            if (getButton((x+1),(y+1)).getColor() != null) {
                if (x+2 == getx() && y+2 == gety()) {
                    return true;
                }
            }
        }
        //center bottom
        if (getButton((x+1),(y)) != null ) {
            if (getButton((x+1),(y)).getColor() != null) {
                if (x+2 == getx() && y == gety()) {
                    return true;
                }
            }
        }
        //left bottom
        if (getButton((x+1),(y-1)) != null ) {
            if (getButton((x+1),(y-1)).getColor() != null) {
                if (x+2 == getx() && y-2 == gety()) {
                    return true;
                }
            }
        }
        //right
        if (getButton((x),(y+1)) != null ) {
            if (getButton((x),(y+1)).getColor() != null) {
                if (x == getx() && y+2 == gety()) {
                    return true;
                }
            }
        }
        //left
        if (getButton((x),(y-1)) != null ) {
            if (getButton((x),(y-1)).getColor() != null) {
                if (x == getx() && y-2 == gety()) {
                    return true;
                }
            }
        }
        //right top
        if (getButton((x-1),(y+1)) != null ) {
            if (getButton((x-1),(y+1)).getColor() != null) {
                if (x-2 == getx() && y+2 == gety()) {
                    return true;
                }
            }
        }
        //top
        if (getButton((x-1),(y)) != null ) {
            if (getButton((x-1),(y)).getColor() != null) {
                if (x-2 == getx() && y == gety()) {
                    return true;
                }
            }
        }
        //left top
        if (getButton((x-1),(y-1)) != null ) {
            if (getButton((x-1),(y-1)).getColor() != null) {
                if (x-2 == getx() && y-2 == gety()) {
                    return true;
                }
            }
        }

    //multiple step jump

        if (n != 1) {
            if (getButton((x+1),(y+1)) != null ) {
                if (getButton((x+1),(y+1)).getColor() != null) {
                    if (getButton((x+2),(y+2)) != null) {
                        if (getButton((x+2),(y+2)).getColor() == null) {
                            if (canJump((x+2), (y+2), 8)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        if (n != 2) {
            if (getButton((x+1),(y)) != null ) {
                if (getButton((x+1),(y)).getColor() != null) {
                    if (getButton((x+2),(y)) != null) {
                        if (getButton((x+2),(y)).getColor() == null) {
                            if (canJump((x+2), (y), 7)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        if (n != 3) {
            if (getButton((x+1),(y-1)) != null ) {
                if (getButton((x+1),(y-1)).getColor() != null) {
                    if (getButton((x+2),(y-2)) != null) {
                        if (getButton((x+2),(y-2)).getColor() == null) {
                            if (canJump((x+2), (y-2), 6)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        if (n != 4) {
            if (getButton((x),(y+1)) != null ) {
                if (getButton((x),(y+1)).getColor() != null) {
                    if (getButton((x),(y+2)) != null) {
                        if (getButton((x),(y+2)).getColor() == null) {
                            if (canJump((x), (y+2), 5)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        if (n != 5) {
            if (getButton((x),(y-1)) != null ) {
                if (getButton((x),(y-1)).getColor() != null) {
                    if (getButton((x),(y-2)) != null) {
                        if (getButton((x),(y-2)).getColor() == null) {
                            if (canJump((x), (y-2), 4)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        if (n != 6) {
            if (getButton((x-1),(y+1)) != null ) {
                if (getButton((x-1),(y+1)).getColor() != null) {
                    if (getButton((x-2),(y+2)) != null) {
                        if (getButton((x-2),(y+2)).getColor() == null) {
                            if (canJump((x-2), (y+2), 3)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        if (n != 7) {
            if (getButton((x-1),(y)) != null ) {
                if (getButton((x-1),(y)).getColor() != null) {
                    if (getButton((x-2),(y)) != null) {
                        if (getButton((x-2),(y)).getColor() == null) {
                            if (canJump((x-2), (y), 2)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        if (n != 8) {
            if (getButton((x-1),(y-1)) != null ) {
                if (getButton((x-1),(y-1)).getColor() != null) {
                    if (getButton((x-2),(y-2)) != null) {
                        if (getButton((x-2),(y-2)).getColor() == null) {
                            if (canJump((x-2), (y-2), 1)) {
                                return true;
                            }
                            jump--;
                        }
                    }
                }
            }
        }
        return false;
    }

//  mouse listener method
    private void btnMouseClicked(MouseEvent e) {

        //beginning button index
        int i;
        //check if a piece has been selected
        boolean isPieceSelected = false;
        for (i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).selected && buttons.get(i).getColor() != null){
                isPieceSelected = true;
                break;
            }
        }
        if (isPieceSelected) {
            //move
            if (color == null) {
                //move possibility check
                jump = 0;
                if (canMove(buttons.get(i).getx(), buttons.get(i).gety())) {
                    move(i);
                }else if (canJump(buttons.get(i).getx(), buttons.get(i).gety(), 0)) {
                    move(i);
                }else {
                    JOptionPane.showMessageDialog(null, "cant move here!", "warning", JOptionPane.WARNING_MESSAGE);
                }

            }else {
                //player changes selected piece
                if (player.isTurn()) {
                    buttons.get(i).selected = false;
                    selected = true;
                }
                //player selected a full button for destination
                else {
                    JOptionPane.showMessageDialog(null, "select empty button!", "warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }else {
            //player selected empty button
            if (color == null) {
                JOptionPane.showMessageDialog(null, "first select a piece", "warning", JOptionPane.WARNING_MESSAGE);
            } else {
                //player selected a piece
                if (player.isTurn()) {
                    selected = true;
                }
                //player selected opponents piece
                else {
                    JOptionPane.showMessageDialog(null, "select your piece!", "warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public Button(int x, int y, Halma halma){
        buttons.add(this);
        this.x = x;
        this.y = y;
        this.halma = halma;
        this.size = (2*halma.getsize()/3)/halma.getBoardSize();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnMouseClicked(e);
            }
        });
    }
}
