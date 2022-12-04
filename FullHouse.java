/**
 * this class models a hand of FullHouse
 * 
 * @author Tom Wong
 */
public class FullHouse extends Hand {
    /**
     * construct a hand of FullHouse
     * 
     * @param player player played
     * @param cards  cards played
     */
    public FullHouse(CardGamePlayer player, CardList cards) {
        super(player, cards);
    }

    /**
     * get the type of hand
     * 
     * @return the type of hand
     */
    public String getType() {
        return "FullHouse";
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
            // triple + pair
            if (card0.rank == card1.rank && card1.rank == card2.rank && card2.rank != card3.rank
                    && card3.rank == card4.rank) {
                return true;
            }
            // pair + triple
            else if (card0.rank == card1.rank && card1.rank != card2.rank && card2.rank == card3.rank
                    && card3.rank == card4.rank) {
                return true;
            } else {
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
            // triple + pair
            if (this.getCard(1).rank == this.getCard(2).rank) {
                Card maxCard = this.getCard(0);
                for (int i = 0; i < 3; i++) {
                    if (this.getCard(i).suit > maxCard.suit) {
                        maxCard = this.getCard(i);
                    }
                }
                return maxCard;
            }
            // pair + triple
            else {
                Card maxCard = this.getCard(2);
                for (int i = 2; i < 5; i++) {
                    if (this.getCard(i).suit > maxCard.suit) {
                        maxCard = this.getCard(i);
                    }
                }
                return maxCard;
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
        } else if (hand.getType() == "Straight" || hand.getType() == "Flush") {
            return true;
        }
        // higher rank
        else if (hand.getType() == this.getType() && this.getTopCard().compareTo(hand.getTopCard()) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
