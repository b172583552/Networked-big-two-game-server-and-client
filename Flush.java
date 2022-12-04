
/**
 * this class models a hand of Flush
 * 
 * @author Tom Wong
 */
public class Flush extends Hand {
    /**
     * construct a hand of Flush
     * 
     * @param player player played
     * @param cards  cards played
     */
    public Flush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "Flush";
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
            if (card0.suit != card1.suit || card1.suit != card2.suit || card2.suit != card3.suit
                    || card3.suit != card4.suit) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * to compare whether the hand played beats another hand
     * 
     * @param hand a hand to be compared
     * @return true if the hand played beats another hand. otherwise, false
     */
    public boolean beats(Hand hand) {
        if (hand == null || !this.isValid() || !hand.isValid() || hand.size() != this.size()){
            return false;
        }
        else if (hand.getType() == "Straight") {
            return true;
        }
        // higher suit
        else if (hand.getType() == this.getType() && this.getTopCard().suit > hand.getTopCard().suit) {
            return true;
        }
        // same suit
        else if (hand.getType() == this.getType() && this.getTopCard().suit == hand.getTopCard().suit
                && this.getTopCard().compareTo(hand.getTopCard()) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
