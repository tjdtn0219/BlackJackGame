import java.util.List;
import java.util.ArrayList;

public class Player implements People{
	private String name;
	private List<Card> Cards;
	private int openIdx;
	
	public Player(String name) {
		this.name=name;
		Cards=new ArrayList<>();
		openIdx=2;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public List<Card> getCards(){
		return Cards;
	}
	@Override
	/*Pick a random card from a deck*/
	public void PickCard(Card card) {
		Cards.add(card);
	}
	@Override
	public String getCardStr() {
		return Cards.get(openIdx++).getStr();
	}
	@Override
	/* @return sum of values of owning cards */
	public int getSumOfValues() {
		int sum=0;
		for(Card card:Cards)
			sum+=card.getValue();
		return sum;
	}
	//@Override
	/*
	 * @param CardIdx which is the index of picking a card at deck
	 * @return if worth of a picked card is A true, else false
	 */
	public boolean isHaveA(int CardIdx) {
		Card card=Cards.get(CardIdx);
		if(card.getValue()==0)
			return true;
		else
			return false;
	}
	@Override
	/*
	 * Decide a value of A 1 or 11.
	 * @param n is 1 or 11 which is chosen value of A,
	 * CardIdx which is the index of picking a card at deck
	 */
	public void setValueA(int n,int CardIdx) {
		Cards.get(CardIdx).AToValue(n);
	}
}
