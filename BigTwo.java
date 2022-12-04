import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * this class models a big two game
 * 
 * @author Tom Wong
 */
public class BigTwo implements CardGame {
    private int numOfPlayers;
    private Deck deck;
    private ArrayList<CardGamePlayer> playerList = new ArrayList<CardGamePlayer>();
    private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();
    private int currentPlayerIdx;
    private int count = 0;
    private BigTwoGUI gui;
    private BigTwoClient client;

    /**
     * construct a big two game
     */
    public BigTwo() {
        for (int i = 0; i < 4; i++) {
            CardGamePlayer player = new CardGamePlayer("");
            playerList.add(player);
        }

        gui = new BigTwoGUI(this);
        client = new BigTwoClient(this, gui);

        client.connect();
    }

    /**
     * return the client
     * 
     * @return client
     */
    public BigTwoClient getClient() {
        return client;
    }

    /**
     * get the number of players
     * 
     * @return number of players
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * get the deck
     * 
     * @return the deck in the game
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * get the player list
     * 
     * @return the player list in the game
     */
    public ArrayList<CardGamePlayer> getPlayerList() {
        return playerList;
    }

    /**
     * get the hands on table
     * 
     * @return the hands on table in the game
     */
    public ArrayList<Hand> getHandsOnTable() {
        return handsOnTable;
    }

    /**
     * get the current player index
     * 
     * @return the current player index
     */
    public int getCurrentPlayerIdx() {
        return currentPlayerIdx;
    }

    /**
     * start a game
     * 
     * @param deck deck shuffled
     */
    public void start(Deck deck) {
        gui.reset();
        for (CardGamePlayer i : playerList) {
            i.removeAllCards();
        }

        handsOnTable.clear();

        // distribute cards, identify 3 diamond, set active player
        for (int i = 0; i < 13; i++) {
            Card card = deck.getCard(i);
            playerList.get(0).addCard(card);
            if (card.rank == 2 && card.suit == 0) {
                currentPlayerIdx = 0;
            }
        }

        for (int i = 13; i < 26; i++) {
            Card card = deck.getCard(i);
            playerList.get(1).addCard(card);
            if (card.rank == 2 && card.suit == 0) {
                currentPlayerIdx = 1;
            }
        }

        for (int i = 26; i < 39; i++) {
            Card card = deck.getCard(i);
            playerList.get(2).addCard(card);
            if (card.rank == 2 && card.suit == 0) {
                currentPlayerIdx = 2;
            }
        }

        for (int i = 39; i < 52; i++) {
            Card card = deck.getCard(i);
            playerList.get(3).addCard(card);
            if (card.rank == 2 && card.suit == 0) {
                currentPlayerIdx = 3;
            }
        }

        for (CardGamePlayer player : playerList) {
            player.sortCardsInHand();
        }
        gui.getLabel().setText("");
        gui.printMsg(playerList.get(currentPlayerIdx).getName() + "'s turn: \n");
        gui.setActivePlayer(currentPlayerIdx);
        gui.start();
    }

    /**
     * make move according to playerIdx and cardIdx
     * 
     * @param playerIdx the player index
     * @param cardIdx   the index of cards selected
     */
    public void makeMove(int playerIdx, int[] cardIdx) {
        client.sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
    }

    /**
     * make a legal move
     * 
     * @param hand the hand played
     */
    public void legalMove(Hand hand) {
        if (hand == null) {
            this.count += 1;
            gui.printMsg("{Pass}\n");
        } else {
            this.count = 0;
            gui.setLabel(playerList.get(currentPlayerIdx).getName());
            gui.printMsg("{" + hand.getType() + "} " + hand.toString() + "\n");
        }
        if (!endOfGame()) {
            this.currentPlayerIdx = (this.currentPlayerIdx + 1) % 4;
            gui.printMsg(playerList.get(currentPlayerIdx).getName() + "'s turn: \n");
            gui.setActivePlayer(currentPlayerIdx);
        }

        else {
            String message = "";
            message += "Game ends\n";
            for (int i = 0; i < playerList.size(); i++) {
                int handSize = playerList.get(i).getCardsInHand().size();
                if (handSize == 0) {
                    message += "Player " + i + " wins the game.\n";
                } else {
                    message += "Player " + i + " has " + handSize + " cards in hand.\n";
                }
            }
            gui.disable();
            JOptionPane.showMessageDialog(null, message);
            client.sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
        }

    }

    /**
     * make an illegal move
     */
    public void illegalMove() {
        gui.printMsg("Not a legal move!!!\n");
    }

    /**
     * check and make the move
     * 
     * @param playerIdx the player index
     * @param cardIdx   the index of card selected
     */
    public void checkMove(int playerIdx, int[] cardIdx) {
        if (handsOnTable.isEmpty()) {
            if (cardIdx == null) {
                this.illegalMove();
            } else {
                CardGamePlayer activePlayer = playerList.get(playerIdx);
                CardList cardSelected = activePlayer.play(cardIdx);
                Card card = new Card(0, 2);
                Hand hand = composeHand(activePlayer, cardSelected);
                if (hand != null && hand.contains(card)) {
                    activePlayer.removeCards(hand);
                    handsOnTable.add(hand);
                    this.legalMove(hand);
                } else {
                    this.illegalMove();
                }
            }
        } else {
            if (cardIdx == null) {
                if (count > 2) {
                    this.illegalMove();
                } else {
                    this.legalMove(null);
                }
            } else {
                CardGamePlayer activePlayer = playerList.get(currentPlayerIdx);
                CardList cardSelected = activePlayer.play(cardIdx);
                Hand hand = composeHand(activePlayer, cardSelected);
                if (hand == null) {
                    this.illegalMove();
                }

                else {
                    if (count == 3) {
                        activePlayer.removeCards(hand);
                        handsOnTable.add(hand);
                        this.legalMove(hand);
                    }

                    else {
                        Hand lasthand = handsOnTable.get(handsOnTable.size() - 1);
                        if (lasthand.size() != hand.size()) {
                            this.illegalMove();
                        } else {
                            if (hand.beats(lasthand)) {
                                activePlayer.removeCards(hand);
                                handsOnTable.add(hand);
                                this.legalMove(hand);
                            } else {
                                this.illegalMove();
                            }
                        }
                    }
                }

            }

        }
    }

    /**
     * check if it is end of game
     * 
     * @return true if it is the end of the game. Otherwise, false
     */
    public boolean endOfGame() {
        for (CardGamePlayer player : playerList) {
            if (player.getCardsInHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * compose a legal hand
     * 
     * @param player the current player
     * @param cards  cards selected
     * @return a legal hand
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards) {
        Hand hand;
        hand = new StraightFlush(player, cards);
        if (hand.isValid()) {
            return hand;
        }
        hand = new Quad(player, cards);
        if (hand.isValid()) {
            return hand;
        }
        hand = new FullHouse(player, cards);
        if (hand.isValid()) {
            return hand;
        }
        hand = new Flush(player, cards);
        if (hand.isValid()) {
            return hand;
        }
        hand = new Straight(player, cards);
        if (hand.isValid()) {
            return hand;
        }
        hand = new Triple(player, cards);
        if (hand.isValid()) {
            return hand;
        }
        hand = new Pair(player, cards);
        if (hand.isValid()) {
            return hand;
        }
        hand = new Single(player, cards);
        if (hand.isValid()) {
            return hand;
        }

        else {
            return null;
        }
    }

    /**
     * main to run the game
     * 
     * @param args not used
     */
    public static void main(String[] args) {
        BigTwo game = new BigTwo();
    }
}
