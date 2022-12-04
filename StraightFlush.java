/**
 * this class models a hand of StraightFlush
 * 
 * @author Tom Wong
 */
public class StraightFlush extends Hand {
    /**
     * construct a hand of StraightFlush
     * 
     * @param player player played
     * @param cards  cards played
     */
    public StraightFlush(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "StraightFlush";
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
            }

            int rank0 = this.getCard(0).rank;
            int rank1 = this.getCard(1).rank;
            int rank2 = this.getCard(2).rank;
            int rank3 = this.getCard(3).rank;
            int rank4 = this.getCard(4).rank;

            if (rank3 == 0 || rank3 == 1) {
                rank3 += 13;
            }

            if (rank4 == 0 || rank4 == 1) {
                rank4 += 13;
            }

            if (rank0 + 1 != rank1 || rank1 + 1 != rank2 || rank2 + 1 != rank3 || rank3 + 1 != rank4) {
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
        if (hand == null || !this.isValid() || !hand.isValid() || hand.size() != this.size()) {
            return false;
        }

        else if (hand.getType() != this.getType()) {
            return true;
        }

        else if (hand.getType() == this.getType() && this.getTopCard().compareTo(hand.getTopCard()) > 0) {
            return true;
        }

        else {
            return false;
        }
    }
}
