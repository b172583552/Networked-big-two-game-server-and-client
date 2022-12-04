/**
 * this class models a hand of Pair
 * 
 * @author Tom Wong
 */
public class Pair extends Hand {
    /**
     * construct a hand of pair
     * 
     * @param player player played
     * @param cards  cards played
     */
    public Pair(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "Pair";
    }

    /**
     * determine if the hand is valid
     * 
     * @return true if valid. Otherwise, false
     */
    public boolean isValid() {
        if (this.size() != 2) {
            return false;
        } else {
            BigTwoCard card0 = (BigTwoCard) this.getCard(0);
            BigTwoCard card1 = (BigTwoCard) this.getCard(1);
            if (card0.rank != card1.rank) {
                return false;
            } else {
                return true;
            }
        }
    }
}
