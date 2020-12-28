//import java.util.List;
import javax.swing.*;
import java.awt.*;

public class Result {
	public final int NumOfBurst=21;

	/*
	 * Check whether burst or not. Burst occurs when score is over 21.
	 * @param person which can be a dealer or player
	 * @return if burst true, or not false
	 */
	public boolean isBurst(People person) {
		if(person.getSumOfValues()>NumOfBurst) {
			return true;
		}
		else
			return false;
	}
	/*
	 * if both player and dealer are not burst, compare scores of them.
	 * And then select winner following rule.
	 * @param dealer,player,player's wallet,betting money;
	 */
	public void getWinner1(Component parent, People dealer, People player, Wallet wallet, int b) {
		int dScore=dealer.getSumOfValues();
		int pScore=player.getSumOfValues();
		
		if(dScore>pScore) {
			/*딜러가 이겼을 경우*/
			if(dScore<21) {
				/*그냥 이겼을 경우*/
				wallet.lostMoney(b);
				JOptionPane.showMessageDialog(parent, dealer.getName()+"가 이겼습니다.\n\n"
						+Integer.toString(b)+"을 잃었습니다.", "GAMEOVER", JOptionPane.PLAIN_MESSAGE);
			}
			else {
				/*딜러가 블랙잭으로 이겼을 경우*/
				wallet.lostbyBlackJack(b);
				JOptionPane.showMessageDialog(parent, "BLACKJACK으로 "+dealer.getName()+"가 이겼습니다.\n\n"
						+wallet.DoubleMoney(Integer.toString(b))+"을 잃었습니다.", "GAMEOVER", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else if(dScore<pScore) {
			/*플레이어가 이겼을 경우*/
			if(pScore<21) {
				/*그냥 이겼을 경우*/
				wallet.winMoney(b);
				JOptionPane.showMessageDialog(parent, player.getName()+"가 이겼습니다.\n\n"
						+Integer.toString(b)+"을 얻었습니다.", "GAMEOVER", JOptionPane.PLAIN_MESSAGE);
			}
			else {
				/*플레이어가 블랙잭으로 이겼을 경우*/
				wallet.winbyBlackJack(b);
				JOptionPane.showMessageDialog(parent, "BLACKJACK으로 "+player.getName()+"가 이겼습니다.\n\n"
						+wallet.DoubleMoney(Integer.toString(b))+"을 얻었습니다.", "GAMEOVER", JOptionPane.PLAIN_MESSAGE);
		
			}
		}
		else {
			/*무승부일 경우*/
			JOptionPane.showMessageDialog(parent, "무승부 입니다.", "GAMEOVER", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
