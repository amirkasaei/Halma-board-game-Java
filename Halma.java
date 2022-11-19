import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Halma extends JFrame {

//  size of the board & number of rows of pieces
    private byte boardSize, rowsOfPieces;
//buttons
    private Button[][] buttons;
//frame size
    private int frameSize;
//players
    Player player1;
    Player player2;

    void setBoardSize(byte boardSize) {
        this.boardSize = boardSize;
    }

    Byte getBoardSize(){
        return boardSize;
    }

    void setRowsOfPieces(byte rowsOfPieces) {
        this.rowsOfPieces = rowsOfPieces;
    }

    Byte getRowsOfPieces(){
        return rowsOfPieces;
    }

//  get board size in pixel
    int getsize() {
        return frameSize;
    }

//restart the game
    void restart() {
        //erase old data
        for (byte i = 0; i < boardSize; i++) {
            for (byte j = 0; j < boardSize; j++) {
                    buttons[i][j].selected = false;
                    buttons[i][j].setColor(null);
                    buttons[i][j].setPlayer(null);
            }
        }
        player1.setMoves(0);
        player1.setTurn(true);
        player2.setMoves(0);
        player2.setTurn(false);
        //insert pieces
        for (int i = 0, n = rowsOfPieces; i < rowsOfPieces; i++, n--) {
            for (int j = 0; j < n; j++) {
                buttons[i][j].setColor(Color.BLUE);
                buttons[i][j].setPlayer(player1);
            }
        }
        for (int i = boardSize-1, n = boardSize-rowsOfPieces-1; i > boardSize-rowsOfPieces-1; i--, n++) {
            for (int j = boardSize-1; j > n; j--) {
                buttons[i][j].setColor(Color.RED);
                buttons[i][j].setPlayer(player2);
            }
        }
    }

//  halma frame
    public Halma(Main main, String player1Name, String player2Name, Byte boardSize, Byte rowsOfPieces){
        super("Halma");
        setBoardSize(boardSize);
        setRowsOfPieces(rowsOfPieces);

        //frame logo
        ImageIcon logo = new ImageIcon("logo.png");
        setIconImage(logo.getImage());

        //setting board size in pixel according to number of rows & columns
        if (boardSize > 9) {
            frameSize = 710;
        } else if (boardSize < 9 && boardSize > 4){
            frameSize = 500;
        } else {
            frameSize = 300;
        }
        setSize(frameSize, frameSize+30);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(boardSize,boardSize,0,0));

        //menubar
        HalmaMenu halmaMenu = new HalmaMenu(this);
        setJMenuBar(halmaMenu);

        //for handling the window closing of halma and showing menu after that
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Button.buttons.clear();
                Player.players.clear();
                main.setVisible(true);
            }
        });

        //player setup
        player1 = new Player(player1Name, Color.BLUE);
        player1.setTurn(true);
        player2 = new Player(player2Name, Color.RED);

        //creating buttons
        buttons = new Button[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                buttons[i][j] = new Button(i, j, this);
                add(buttons[i][j]);
                buttons[i][j].setFocusPainted(false);
            }
        }

        //insert pieces
        for (int i = 0, n = rowsOfPieces; i < rowsOfPieces; i++, n--) {
            for (int j = 0; j < n; j++) {
                buttons[i][j].setColor(Color.BLUE);
                buttons[i][j].setPlayer(player1);
            }
        }
        for (int i = boardSize-1, n = boardSize-rowsOfPieces-1; i > boardSize-rowsOfPieces-1; i--, n++) {
            for (int j = boardSize-1; j > n; j--) {
                buttons[i][j].setColor(Color.RED);
                buttons[i][j].setPlayer(player2);
            }
        }
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
