/**
 * this class models a hand of single
 * 
 * @author Tom Wong
 */
public class Single extends Hand {
    /**
     * construct a hand of Single
     * 
     * @param player player played
     * @param cards  cards played
     */
    public Single(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "Single";
    }

    /**
     * determine if the hand is valid
     * 
     * @return true if valid. Otherwise, false
     */
    public boolean isValid() {
        if (this.size() != 1) {
            return false;
        } else {
            return true;
        }
    }

}
