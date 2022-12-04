import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * this class models a client in bigtwo
 * 
 * @author tom
 */
public class BigTwoClient implements NetworkGame {
    private BigTwo game;
    private BigTwoGUI gui;
    private Socket sock;
    private ObjectOutputStream oos;
    private int playerID;
    private String playerName;
    private String serverIP;
    private int serverPort;

    /**
     * initialize the client by game and gui
     * 
     * @param game game
     * @param gui  gui
     */
    public BigTwoClient(BigTwo game, BigTwoGUI gui) {
        this.game = game;
        this.gui = gui;
    }

    /**
     * get the playerID
     * 
     * @return playerID
     */
    @Override
    public int getPlayerID() {
        return this.playerID;
    }

    /**
     * set the playerID
     */
    @Override
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * get the playername
     * 
     * @return playerName
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    /**
     * set the playerName
     */
    @Override
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * get the server IP
     */
    @Override
    public String getServerIP() {
        return serverIP;
    }

    /**
     * set the server IP
     */
    @Override
    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    /**
     * get the serverPort
     * 
     * @return serverPort
     */
    @Override
    public int getServerPort() {
        return serverPort;
    }

    /**
     * set the server Port
     */
    @Override
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * connect the game
     */
    @Override
    public synchronized void connect() {
        try {
            sock = new Socket("127.0.0.1", 2396);
            oos = new ObjectOutputStream(sock.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Thread readerThread = new Thread(new ServerHandler());
        readerThread.start();

    }

    /**
     * parse the message
     */
    @Override
    public synchronized void parseMessage(GameMessage message) {
        if (message.getType() == CardGameMessage.PLAYER_LIST) {
            this.playerID = message.getPlayerID();
            String[] playerNames = (String[]) message.getData();
            for (int i = 0; i < playerNames.length; i++) {
                if (playerNames[i] != null) {
                    CardGamePlayer player = game.getPlayerList().get(i);
                    player.setName(playerNames[i]);
                }
            }
            this.playerName = gui.getPlayerName();
            sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, playerName));

        }

        if (message.getType() == CardGameMessage.JOIN) {
            String newPlayerName = (String) message.getData();
            int playerID = message.getPlayerID();
            CardGamePlayer newPlayer = game.getPlayerList().get(playerID);
            newPlayer.setName(newPlayerName);
            gui.addPlayer();
            gui.start();
            gui.repaint();
            if (this.playerID == playerID) {
                gui.printMsg("Connected to /127.0.0.1:2396\n");
                sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
            }
        }

        if (message.getType() == CardGameMessage.FULL) {
            gui.printMsg("server full and cannot join the game\n");
            try {
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (message.getType() == CardGameMessage.QUIT) {
            CardGamePlayer leavePlayer = game.getPlayerList().get(message.getPlayerID());
            gui.printMsg(leavePlayer.getName() + " leave the game\n");
            leavePlayer.setName("");
            if (message.getPlayerID() == 0) {
                gui.player1.removeAll();
            }
            if (message.getPlayerID() == 1) {
                gui.player2.removeAll();
            }
            if (message.getPlayerID() == 2) {
                gui.player3.removeAll();
            }
            if (message.getPlayerID() == 3) {
                gui.player4.removeAll();
            }

            for (CardGamePlayer i : game.getPlayerList()) {
                i.removeAllCards();
            }
            game.getHandsOnTable().clear();
            gui.getLabel().setText("");
            ;
            gui.repaint();

            gui.disable();
            sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
        }

        if (message.getType() == CardGameMessage.READY) {
            gui.printMsg(game.getPlayerList().get(message.getPlayerID()).getName() + " is ready\n");
        }

        if (message.getType() == CardGameMessage.START) {
            game.start((BigTwoDeck) message.getData());
            gui.repaint();
        }

        if (message.getType() == CardGameMessage.MOVE) {
            game.checkMove(message.getPlayerID(), (int[]) message.getData());
            gui.repaint();
        }

        if (message.getType() == CardGameMessage.MSG) {
            gui.printChat((String) message.getData() + "\n");
        }

    }

    /**
     * send the message to server
     */
    @Override
    public synchronized void sendMessage(GameMessage message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * a class to handle incoming message
     * 
     * @author tom
     */
    class ServerHandler implements Runnable {
        ObjectInputStream ois;

        /**
         * initialize the serverHandler
         */
        public ServerHandler() {
            try {
                ois = new ObjectInputStream(sock.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * receive message
         */
        public void run() {
            CardGameMessage message;
            try {
                // reads incoming messages from the server
                while ((message = (CardGameMessage) ois.readObject()) != null) {
                    parseMessage(message);
                }
            } catch (Exception ex) {
                gui.start();
                gui.disable();
                for (CardGamePlayer i : game.getPlayerList()) {
                    i.removeAllCards();
                    i.setName("");
                }
                game.getHandsOnTable().clear();
                gui.getLabel().setText("");
                gui.mute();
                gui.removeAll();
                gui.repaint();
            }
        }

    }
}
