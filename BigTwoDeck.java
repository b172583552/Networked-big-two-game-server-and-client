/**
 * this class models a big two deck
 * 
 * @author Tom Wong
 */
public class BigTwoDeck extends Deck {
	/**
	 * initialize a big two deck
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}
}
