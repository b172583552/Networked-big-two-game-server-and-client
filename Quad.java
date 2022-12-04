/**
 * this class models a hand of Quad
 * 
 * @author Tom Wong
 */
public class Quad extends Hand {
    /**
     * construct a hand of Quad
     * 
     * @param player player played
     * @param cards  cards played
     */
    public Quad(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "Quad";
    }

    /**
     * determine if the hand is valid
     * 
     * @return true if valid. Otherwise, false
     */
    public boolean isValid() {
        if (this.size() != 5) {
            return false;
        } else {
            BigTwoCard card0 = (BigTwoCard) this.getCard(0);
            BigTwoCard card1 = (BigTwoCard) this.getCard(1);
            BigTwoCard card2 = (BigTwoCard) this.getCard(2);
            BigTwoCard card3 = (BigTwoCard) this.getCard(3);
            BigTwoCard card4 = (BigTwoCard) this.getCard(4);
            // quad + single
            if (card0.rank == card1.rank && card1.rank == card2.rank && card2.rank == card3.rank) {
                return true;
            }
            // single + quad
            else if (card1.rank == card2.rank && card2.rank == card3.rank && card3.rank == card4.rank) {
                return true;
            }

            else {
                return false;
            }
        }
    }

    /**
     * get the top card of the hand
     * 
     * @return a top card
     */
    public Card getTopCard() {
        if (!this.isEmpty()) {
            this.sort();
            // quad + single
            if (this.getCard(0).rank == this.getCard(1).rank) {
                return this.getCard(3);
            }
            // single + quad
            else {
                return this.getCard(4);
            }
        }

        else {
            return null;
        }
    }

    /**
     * to compare whether the hand played beats another hand
     * 
     * @param hand a hand to be compared
     * @return true if the hand played beats another hand. otherwise, false
     */
    public boolean beats(Hand hand) {
        if (hand == null || !this.isValid() || !hand.isValid() || hand.size() != this.size()) {
            return false;
        } 
        else if (hand.getType() == "Straight" || hand.getType() == "Flush" || hand.getType() == "FullHouse") {
            return true;
        }
        // higher rank
        else if (hand.getType() == this.getType() && this.getTopCard().compareTo(hand.getTopCard()) > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}
