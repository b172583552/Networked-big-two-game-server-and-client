/**
 * this class models a hand of Triple
 * 
 * @author Tom Wong
 */
public class Triple extends Hand {
    /**
     * construct a hand of Triple
     * 
     * @param player player played
     * @param cards  cards played
     */
    public Triple(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "Triple";
    }

    /**
     * determine if the hand is valid
     * 
     * @return true if valid. Otherwise, false
     */
    public boolean isValid() {
        if (this.size() != 3) {
            return false;
        } else {
            BigTwoCard card0 = (BigTwoCard) this.getCard(0);
            BigTwoCard card1 = (BigTwoCard) this.getCard(1);
            BigTwoCard card2 = (BigTwoCard) this.getCard(2);
            if (card0.rank != card1.rank || card0.rank != card2.rank) {
                return false;
            } else {
                return true;
            }
        }
    }
}