/**
 * This class is used for modeling the BigTwoCard in BigTwo card game.
 * 
 * @author Tom Wong
 */
public class BigTwoCard extends Card {
    /**
     * creates and returns an instance of the BigTwoCard
     * 
     * @param suit the suit of a card
     * @param rank the rank of a card
     */
    public BigTwoCard(int suit, int rank) {
        super(suit, rank);
    }

    /**
     * define the compare order in big two
     * 
     * @param card a card to be compared
     * @return a negative integer, zero, or a positive integer as this card is less than, equal to, or greater than the specified card
     */
    public int compareTo(Card card) {
        int newRank;
        int cardRank;
        if (this.rank == 0 || this.rank == 1) {
            newRank = this.rank + 13;
        } else {
            newRank = this.rank;
        }

        if (card.rank == 0 || card.rank == 1) {
            cardRank = card.rank + 13;
        } else {
            cardRank = card.rank;
        }

        if (newRank > cardRank) {
            return 1;
        } else if (newRank < cardRank) {
            return -1;
        } else if (this.suit > card.suit) {
            return 1;
        } else if (this.suit < card.suit) {
            return -1;
        } else {
            return 0;
        }
    }
}