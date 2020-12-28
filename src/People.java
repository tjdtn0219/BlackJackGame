import java.util.List;

public interface People {
	public String getName();
	
	public List<Card> getCards();
	
	public void PickCard(Card card);
	
	public int getSumOfValues();
	
	public void setValueA(int n,int CardIdx);
	
	public boolean isHaveA(int CardIdx);
	
	public String getCardStr();
}
