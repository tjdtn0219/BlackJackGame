import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class CardDeck {
	private static final String[] Shapes= {"Spade", "Hearts", "Diamond", "Club"};
	private static final int CardIDX=13;//A,1,...,10,J,Q,K 총 13개
	
	private List<Card> Deck=CreateDeck();
	
	/*
	 * Create a collection of 52cards. and then
	 * @return a created collection.
	 */
	private List<Card> CreateDeck() {
		List<Card> Deck=new LinkedList<>();
		
		Deck=new ArrayList<>();
		for(String shape:Shapes) {
			for(int i=1;i<=CardIDX;i++) {
				Card card=new Card(shape, i);
				Deck.add(card);
			}
		}
		return Deck;
	}
	
	/*
	 * Pick a random card of a Deck out.
	 * @return the picked card
	 */
	public Card PickedCard() {
		Card pickedCard=getRandomCard();
		Deck.remove(pickedCard);
		return pickedCard;
	}
	/*
	 * @return a random card of a Deck
	 */
	public Card getRandomCard() {
		int size=Deck.size();
		int random=(int)(Math.random()*(size-1));
		return Deck.get(random);
	}
	
}
