
public class Wallet {
	private int money;
	
	public Wallet(int money) {
		this.money=money;
	}
	public int getMoney() {
		return money;
	}
	/*
	 * If player wins the game, he gets betting money;
	 * @param betting money, player who wins the game.
	 */
	public void winMoney(int bmoney) {
		money+=bmoney;
	}
	/*
	 * If player wins the game by BlackJack, he gets double betting money;
	 * @param betting money
	 */
	public void winbyBlackJack(int bmoney) {
		money+=bmoney*2;
	}
	/*
	 * If player loses the game, he loses betting money;
	 * @param betting money
	 */
	public void lostMoney(int bmoney) {
		money-=bmoney;
	}
	/*
	 * If player loses the game by the BlackJack, he loses betting money;
	 * @param betting money
	 */
	public void lostbyBlackJack(int bmoney) {
		money-=bmoney*2;
	}
	
	/*
	 * @return Doubled money
	 * @param betting money(String type)
	 */
	public String DoubleMoney(String money) {
		int m=Integer.parseInt(money);
		m*=2;
		return Integer.toString(m);
	}
}
