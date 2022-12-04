import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

/**
 * A GUI displaying big two game
 * 
 * @author Tom
 */
public class BigTwoGUI implements CardGameUI {
    private BigTwo game;
    private boolean[] selected = new boolean[13];
    private int activePlayer;
    private ArrayList<CardGamePlayer> playerList; // the list of players
    private ArrayList<Hand> handsOnTable; // the list of hands played on the table

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu messageMenu;
    private JMenuItem connect;
    private JMenuItem quit;
    private JMenuItem clearGameMessage;
    private JMenuItem clearChatMessage;
    private JPanel bigTwoPanel;

    private JLabel label5;
    private JPanel drawPanel1;
    private JPanel drawPanel2;
    private JPanel drawPanel3;
    private JPanel drawPanel4;
    private JPanel drawPanel5;
    /**
     * player 1 Panel
     */
    public JPanel player1;
    /**
     * player 2 Panel
     */
    public JPanel player2;
    /**
     * player 3 Panel
     */
    public JPanel player3;
    /**
     * player 4 Panel
     */
    public JPanel player4;
    private JPanel cardPlayed;

    private JButton playButton;
    private JButton passButton;
    private JTextArea msgArea;
    private JTextArea chatArea;
    private JTextField chatInput;
    private JPanel bottomPanel = new JPanel();
    private String playerName;

    /**
     * constructor to initialize a GUI
     * 
     * @param game bigtwo game
     */
    public BigTwoGUI(BigTwo game) {

        this.playerName = JOptionPane.showInputDialog("Please input your name");

        this.game = game;

        playerList = game.getPlayerList();
        handsOnTable = game.getHandsOnTable();

        frame = new JFrame();
        frame.setTitle(playerName);
        bigTwoPanel = new BigTwoPanel();

        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        messageMenu = new JMenu("Message");
        connect = new JMenuItem("Connect");
        quit = new JMenuItem("Quit");

        quit.addActionListener(new QuitMenuItemListener());
        connect.addActionListener(new ConnectMenuItemListener());
        clearChatMessage = new JMenuItem("Clear Chat Message");
        clearGameMessage = new JMenuItem("Clear Game Message");
        JPanel buttonPanel = new JPanel();

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        playButton = new JButton("Play");
        passButton = new JButton("Pass");
        playButton.addActionListener(new PlayButtonListener());
        passButton.addActionListener(new PassButtonListener());
        buttonPanel.add(playButton);
        buttonPanel.add(passButton);

        JPanel textPanel = new JPanel();

        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));

        JLabel textLabel = new JLabel("Message: ");
        textPanel.add(textLabel);

        chatInput = new JTextField();
        chatInput.addActionListener(new chatInputListener());
        textPanel.add(chatInput);

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(buttonPanel);
        bottomPanel.add(textPanel);

        msgArea = new JTextArea(10, 20);
        chatArea = new JTextArea(10, 20);
        msgArea.setLineWrap(true);
        chatArea.setLineWrap(true);
        JScrollPane scroller1 = new JScrollPane(msgArea);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(scroller1);

        JScrollPane scroller2 = new JScrollPane(chatArea);
        scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.add(scroller1);
        rightPanel.add(scroller2);

        menuBar.add(gameMenu);
        menuBar.add(messageMenu);
        gameMenu.add(connect);
        gameMenu.add(quit);
        messageMenu.add(clearChatMessage);
        messageMenu.add(clearGameMessage);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(bigTwoPanel, BorderLayout.CENTER);
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.pack();
        disable();
    }

    /**
     * get the name of the player
     * 
     * @return playerName
     */
    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * make GUI visible
     */
    public void start() {
        frame.setVisible(true);
    }

    /**
     * set active player
     */
    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    /**
     * update the frame
     */
    public void repaint() {
        frame.repaint();
    }

    /**
     * print message to msgArea
     * 
     * @param msg message
     */
    public void printMsg(String msg) {
        msgArea.append(msg);
    }

    /**
     * print message to chatArea
     * 
     * @param msg message
     */
    public void printChat(String msg) {
        chatArea.append(msg);
    }

    /**
     * clear the message area
     */
    public void clearMsgArea() {
        msgArea.setText("");
        chatArea.setText("");
        chatInput.setText("");
    }

    /**
     * reset area
     */
    public void reset() {
        resetSelected();
        clearMsgArea();
        enable();
    }

    /**
     * enable chat input and button press
     */
    public void enable() {
        chatInput.setEnabled(true);
        this.playButton.setEnabled(true);
        this.passButton.setEnabled(true);
    }

    /**
     * disable chatInput
     */
    public void mute() {
        chatInput.setEnabled(false);
    }

    /**
     * disable chat input and button press
     */
    public void disable() {
        this.playButton.setEnabled(false);
        this.passButton.setEnabled(false);
    }

    private int[] getSelected() {
        int count = 0;
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                count += 1;
            }
        }
        int[] cardSelected = new int[count];
        int j = 0;
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                cardSelected[j] = i;
                j++;
            }
        }
        return cardSelected;
    }

    private void resetSelected() {
        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
        }
    }

    /**
     * make move
     */
    public void promptActivePlayer() {
        int[] cardIdx = getSelected();
        resetSelected();
        game.makeMove(activePlayer, cardIdx);
    }

    /**
     * get the label on the table
     * 
     * @return the label on the table
     */
    public JLabel getLabel() {
        return this.label5;
    }

    /**
     * set the name of player just playing a card on the table
     * 
     * @param name player name
     */
    public void setLabel(String name) {
        this.label5.setText("Played by " + name);
    }

    /**
     * remove all information in player Panel
     */
    public void removeAll() {
        player1.removeAll();
        player2.removeAll();
        player3.removeAll();
        player4.removeAll();
    }

    /**
     * add player to the table
     */
    public synchronized void addPlayer() {
        JPanel playerPanels[] = { player1, player2, player3, player4 };
        JPanel drawPanels[] = { drawPanel1, drawPanel2, drawPanel3, drawPanel4 };
        String filenames[] = { "cards\\player1.png", "cards\\player2.png", "cards\\player3.png", "cards\\player4.png" };
        removeAll();
        for (int i = 0; i < game.getPlayerList().size(); i++) {
            CardGamePlayer player = game.getPlayerList().get(i);
            if (player.getName() != "") {
                playerPanels[i].add(new JLabel(player.getName()), BorderLayout.NORTH);
                playerPanels[i].add(new JLabel(new ImageIcon(filenames[i])), BorderLayout.WEST);
                playerPanels[i].add(drawPanels[i], BorderLayout.CENTER);
            }
        }

        repaint();
    }

    /**
     * a panel to display the whole table
     * 
     * @author Tom
     */
    class BigTwoPanel extends JPanel {
        private int canvasSize = 800;

        /**
         * a constructor to initialize components
         */
        public BigTwoPanel() {
            setBackground(Color.GREEN);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            player1 = new JPanel(new BorderLayout());
            player2 = new JPanel(new BorderLayout());
            player3 = new JPanel(new BorderLayout());
            player4 = new JPanel(new BorderLayout());
            cardPlayed = new JPanel(new BorderLayout());

            drawPanel1 = new playerPanel(0);
            drawPanel2 = new playerPanel(1);
            drawPanel3 = new playerPanel(2);
            drawPanel4 = new playerPanel(3);
            drawPanel5 = new playerPanel(4); // cards on table

            label5 = new JLabel("");
            cardPlayed.add(label5, BorderLayout.NORTH);
            cardPlayed.add(new JLabel(new ImageIcon("cards\\b.gif")), BorderLayout.WEST);

            player1.add(drawPanel1, BorderLayout.CENTER);
            player2.add(drawPanel2, BorderLayout.CENTER);
            player3.add(drawPanel3, BorderLayout.CENTER);
            player4.add(drawPanel4, BorderLayout.CENTER);
            cardPlayed.add(drawPanel5, BorderLayout.CENTER);

            player1.setBackground(Color.green);
            player2.setBackground(Color.green);
            player3.setBackground(Color.green);
            player4.setBackground(Color.green);
            cardPlayed.setBackground(Color.green);

            this.add(player1);
            this.add(player2);
            this.add(player3);
            this.add(player4);
            this.add(cardPlayed);

            setPreferredSize(new Dimension(canvasSize, canvasSize));
        }
    }

    /**
     * a panel to draw the cards for each player
     * 
     * @author tom
     */
    class playerPanel extends JPanel implements MouseListener {
        private int printNum;

        /**
         * add a mouse listener to the panel and set player
         * 
         * @param player player
         */
        public playerPanel(int player) {
            this.printNum = player;
            if (printNum < 4) {
                this.addMouseListener(this);
            }
        }

        private String judgeFile(Card card) {
            String suit = "";
            if (card.suit == 3) {
                suit = "s";
            }

            if (card.suit == 2) {
                suit = "h";
            }

            if (card.suit == 1) {
                suit = "c";
            }

            if (card.suit == 0) {
                suit = "d";
            }

            return (card.rank + 1) + suit;
        }

        /**
         * paint the cards
         * 
         * @param g the graphics
         */
        public void paintComponent(Graphics g) {
            int x = 0;
            int y = 20;
            if (this.printNum < 4) {
                CardGamePlayer player = playerList.get(this.printNum);
                CardList hand = player.getCardsInHand();
                if (hand != null) {
                    for (int i = 0; i < hand.size(); i++) {
                        if (this.printNum == game.getClient().getPlayerID()) {
                            String filename = judgeFile(hand.getCard(i));
                            if (selected[i]) {
                                g.drawImage(new ImageIcon("cards\\" + filename + ".gif").getImage(), x, y - 20, this);
                            } else {
                                g.drawImage(new ImageIcon("cards\\" + filename + ".gif").getImage(), x, y, this);
                            }
                            x += 20;
                        } else {
                            g.drawImage(new ImageIcon("cards\\b.gif").getImage(), x, y, this);
                            x += 20;
                        }
                    }
                }
            }

            else if (this.printNum == 4) {
                if (!handsOnTable.isEmpty()) {
                    Hand cards = handsOnTable.get(handsOnTable.size() - 1);
                    for (int i = 0; i < cards.size(); i++) {
                        String filename = judgeFile(cards.getCard(i));
                        g.drawImage(new ImageIcon("cards\\" + filename + ".gif").getImage(), x, y, this);
                        x += 20;
                    }
                }
            }

        }

        @Override
        /**
         * not used
         * 
         * @param e an mouse event
         */
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        /**
         * not used
         * 
         * @param e an mouse event
         */
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        /**
         * handle mouse release
         * 
         * @param e an mouse event
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            if (activePlayer == printNum && activePlayer == game.getClient().getPlayerID()) {
                int x = e.getX();
                int y = e.getY();
                for (int i = playerList.get(printNum).getCardsInHand().size() - 1; i >= 0; i--) {
                    int cardX = 20 * i;
                    if (!selected[i]) {
                        if (x >= cardX && x < cardX + 73 && y >= 20 && y < 20 + 97) {
                            selected[i] = true;
                            break;
                        }
                    } else {
                        if (x >= cardX && x < cardX + 73 && y >= 0 && y < 0 + 97) {
                            selected[i] = false;
                            break;

                        }
                    }
                }
                frame.repaint();

            }
        }

        /**
         * not used
         * 
         * @param e an mouse event
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        /**
         * not used
         * 
         * @param e an mouse event
         */
        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * a class to handle play button
     * 
     * @author Tom
     */
    class PlayButtonListener implements ActionListener {

        private boolean clickable() {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    return true;
                }
            }
            return false;
        }

        /**
         * make move and update the frame
         * 
         * @param event the action event
         */
        public void actionPerformed(ActionEvent event) {
            if (clickable()) {
                promptActivePlayer();
            }
        }
    }

    /**
     * a class to handle play button
     * 
     * @author Tom
     */
    class PassButtonListener implements ActionListener {

        /**
         * make a pass move and update the frame
         * 
         * @param event the action event
         */
        public void actionPerformed(ActionEvent event) {
            if (activePlayer == game.getClient().getPlayerID()) {
                int[] cardIdx = null;
                resetSelected();
                game.makeMove(activePlayer, cardIdx);

            }
        }
    }

    /**
     * reconnect the game
     * 
     * @author Tom
     */
    class ConnectMenuItemListener implements ActionListener {

        /**
         * reconnect the game
         * 
         * @param event the action event
         */
        public void actionPerformed(ActionEvent event) {
            game.getClient().connect();
        }
    }

    /**
     * quit the game
     * 
     * @author Tom
     */
    class QuitMenuItemListener implements ActionListener {

        /**
         * quit the game
         * 
         * @param event the action event
         */
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    /**
     * send chat input
     * 
     * @author Tom
     */
    class chatInputListener implements ActionListener {

        /**
         * send chat input
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            game.getClient().sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, chatInput.getText()));
            chatInput.setText("");
        }
    }

}
