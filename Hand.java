
/**
 * this abstract class models a hand of cards
 * 
 * @author Tom Wong
 */
public abstract class Hand extends CardList {
   private final CardGamePlayer player;

   /**
    * constructs a hand with specified player and cards
    * 
    * @param player player to play this hand
    * @param cards  cards played
    */
   public Hand(CardGamePlayer player, CardList cards) {
      this.player = player;
      for (int i = 0; i < cards.size(); i++) {
         this.addCard(cards.getCard(i));
      }
      this.sort();
   }

   /**
    * get the player to play this hand
    * 
    * @return player to play this hand
    */
   public CardGamePlayer getPlayer() {
      return this.player;
   }

   /**
    * get the top card of the hand
    * 
    * @return a top card
    */
   public Card getTopCard() {
      if (!this.isEmpty()) {
         this.sort();
         return this.getCard(this.size() - 1);
      } else {
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
      if (hand == null || !hand.isValid() || !this.isValid() || !(this.getType() == hand.getType())) {
         return false;
      } else {
         return this.getTopCard().compareTo(hand.getTopCard()) > 0;
      }

   }

   /**
    * abstract method to be overriden
    * 
    * @return true if valid; otherwise, false
    */
   public abstract boolean isValid();

   /**
    * abstract method to be overriden
    * 
    * @return the type of hand
    */
   public abstract String getType();
}
