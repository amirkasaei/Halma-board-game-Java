import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main extends JFrame {

//  start the game
    private void start(Main main){
        //player inputs
        String player1Name = JOptionPane.showInputDialog("Please Enter First Player's Name:");
        String player2Name = JOptionPane.showInputDialog("Please Enter Second Player's Name:");

    //board inputs and checking the validity of inputs
        boolean wrongEntrance = false;
        //board size
        String  boardSizeTemp;
        Byte  boardSize = 0;

        do {
            try {
                if (!wrongEntrance)
                    boardSizeTemp =  JOptionPane.showInputDialog("Please Enter Size of the Board:");
                else
                    boardSizeTemp =  JOptionPane.showInputDialog("Wrong Entrance!\nPlease Enter Size of the Board:");

                wrongEntrance = false;

                boardSize =  Byte.parseByte(boardSizeTemp);
                if (boardSize%2 != 0 || boardSize == 0)
                    wrongEntrance = true;
                
            } catch (NumberFormatException e) {
                e.getStackTrace();
                wrongEntrance = true;
            }
        }while (wrongEntrance);

        //number of rows of pieces
        String rowsOfPiecesTemp;
        Byte  rowsOfPieces = 0;

        do {
            try {
                if (!wrongEntrance)
                    rowsOfPiecesTemp = JOptionPane.showInputDialog("Please Enter Number of Rows of Pieces:");
                else
                    rowsOfPiecesTemp = JOptionPane.showInputDialog("Wrong Entrance!\nPlease Enter Number of Rows of Pieces:");

                wrongEntrance = false;

                rowsOfPieces =  Byte.parseByte(rowsOfPiecesTemp);
                if (rowsOfPieces < 1 || rowsOfPieces >= boardSize)
                    wrongEntrance = true;
            } catch (NumberFormatException e) {
                e.getStackTrace();
                wrongEntrance = true;
            }
        }while (wrongEntrance);

        new Halma(main, player1Name, player2Name, boardSize, rowsOfPieces);
        //making menu frame invisible
        main.setVisible(false);
    }

//  show records
    private void records(Main main){

        JFrame records = new JFrame("records");
        // Frame Size
        records.setSize(400, 400);
        records.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //frame logo
        ImageIcon logo = new ImageIcon("logo.png");
        records.setIconImage(logo.getImage());

        //handle closing of records frame
        records.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                main.setVisible(true);
            }
        });

        //reading records
        main.setVisible(false);
        //line counter
        int i = 0;
        try {
            Path path = Paths.get("records.txt");
            i = (int)(Files.lines(path).count());
        } catch (IOException e) {
            e.getStackTrace();
        }

        //data
        String[][] data = new String[i][];

        //sorting file data
        try (FileReader fileReader = new FileReader("records.txt")) {
            Scanner scanner = new Scanner(fileReader);
            i = 0;
            String tmp;
            while (scanner.hasNextLine()) {
                tmp = scanner.nextLine();
                data[i] = tmp.split("/");
                i++;
            }
            scanner.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        //columns of table
        String[] columnNames = {"Player Name" , "Number of Moves"};

        //data table
        JTable recordTable = new JTable(data, columnNames);
        recordTable.setBounds(0, 0, 400, 400);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(recordTable);
        records.add(sp);
        //border
        records.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(159, 253, 200, 255), 20));

        records.setLocationRelativeTo(null);
        records.setVisible(true);
    }

//  exit
    private void exit(){
        System.exit(0);
    }

//  main menu frame
    public Main() {

        super("Menu");
        setSize(450, 350);
        getContentPane().setBackground(new Color(159, 253, 200, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //frame logo
        ImageIcon logo = new ImageIcon("logo.png");
        setIconImage(logo.getImage());

        //a reference for inside of action listener block
        Main main = this;

        //logo
        JLabel halmaLogo = new JLabel(new ImageIcon("halma.png"));
        halmaLogo.setBounds(60,30,300,80);
        add(halmaLogo);

        //creating menu buttons and their action listener and adding them to the frame
        JButton[] menuButton = new JButton[3];
        for (byte i = 0; i < 3; i++) {
            menuButton[i] = new JButton();
            if (i == 0) {
                menuButton[i].setText("Start");
                menuButton[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        start(main);
                    }
                });
            }else if (i == 1) {
                menuButton[i].setText("Records");
                menuButton[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        records(main);
                    }
                });
            }else {
                menuButton[i].setText("Exit");
                menuButton[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int ans = JOptionPane.showConfirmDialog(null, "Are You Sure You Want to Exit?", "", JOptionPane.YES_NO_OPTION);
                        if (ans == 0)
                            exit();
                    }
                });
            }
            menuButton[i].setFocusPainted(false);
            menuButton[i].setBounds(165,140+i*40, 100, 30);
            add(menuButton[i]);
        }

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        //changing look and feel into windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.getStackTrace();
        }
        //main menu frame
        new Main();
    }
}
