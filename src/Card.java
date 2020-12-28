
public class Card {
	private String shape;
	private String worth;
	private int value;
	
	public Card(String shape, int num) {
		this.shape=shape;
		this.worth=NumToWorth(num);
		this.value=NumToValue(num);
	}
	
	public int getValue() {
		return value;
	}
	/*
	 * @return String type of Card for card's image
	 */
	public String getStr() {
		return shape+""+worth;
	}
	/*
	 * @param n the number that is a second parameter when we create a new Object of Card
	 * @return worth such as 1->"A", 2->"2",...13->"K"
	 */
	private String NumToWorth(int n) {
		if(n==1)
			return "A";
		else if(n==11)
			return "J";
		else if(n==12)
			return "Q";
		else if(n==13)
			return "K";
		else
			return String.valueOf(n);
	}
	
	public void AToValue(int num) {
		value=num;
		
	}
	/*
	 * @param n the number that is a second parameter when we create a new Object of Card
	 * @return value such as A=0(because A(1 or 11) is not decided), J,Q,K=10, Others are same
	 */
	private int NumToValue(int n) {
		if(n==1)
			return 0;//초기에 A값은 미정이므로 0으로 설정한다.
		else if(n==11 || n==12 || n==13)
			return 10;
		else
			return n;
	}
	
}
