/**
 * this class models a hand of Straight
 * 
 * @author Tom Wong
 */
public class Straight extends Hand {
    /**
     * construct a hand of Straight
     * 
     * @param player player played
     * @param cards  cards played
     */
    public Straight(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "Straight";
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
            this.sort();
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
}
