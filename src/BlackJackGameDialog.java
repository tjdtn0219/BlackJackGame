import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class BlackJackGameDialog extends JPanel{
	private static final int DEFAULT_WIDTH=600;
	private static final int DEFAULT_HEIGHT=200;
	
	private boolean Q;
	private JDialog dialog;
	private String pName;
	private String pMoney;
	private String bettingMoney;
	private JLabel MoneyLabel;
	private JLabel BettingLabel;
	private JLabel dScoreLabel;
	private JLabel pScoreLabel;
	
	private JPanel showPanel;
	
	private JPanel dealerPanel;
	private JPanel playerPanel;
	private JPanel dealerNorthPanel;
	private JPanel playerNorthPanel;
	private ShowCardsPanel pshowcardsPanel;
	private ShowCardsPanel dshowcardsPanel;
	
	private JButton BetButton;
	private JButton StartGameButton;
	private JButton QuitButton;
	private JButton HitButton;
	private JButton StayButton;

	private static final int FirstPickCnt=2;
	private static final int StdOfDealerDraw=17;
	
	private	int pScore;
	private int pCardIdx;
	private int dCardIdx;
	private Dealer dealer;
	private Player player;
	private Result result;
	private CardDeck carddeck;
	private Wallet wallet;
	
	public BlackJackGameDialog(String name, String money) {
		wallet=new Wallet(Integer.parseInt(money));
		pName=name;
		pMoney=money;
		
		result=new Result();
	
		setLayout(new BorderLayout());

		JPanel bettingPanel=new JPanel();
		JPanel buttonPanel=new JPanel();
		
		bettingPanel.setLayout(new GridLayout(1,0));
		bettingPanel.add(new JLabel("현재 남아있는 금액 : ", SwingConstants.RIGHT));
		bettingPanel.add(MoneyLabel=new JLabel(pMoney));
		bettingPanel.add(new JLabel("베팅한 금액 : ", SwingConstants.RIGHT));
		bettingPanel.add(BettingLabel=new JLabel(""));
		
		BetButton=new JButton("BET(RETRY)");
		StartGameButton=new JButton("START GAME");
		HitButton=new JButton("HIT");
		StayButton=new JButton("STAY");
		QuitButton=new JButton("QUIT");
		
		StartGameButton.setEnabled(false);
		HitButton.setEnabled(false);
		StayButton.setEnabled(false);
		
		BetButton.addActionListener(new Bet_Quit_Action());
		StartGameButton.addActionListener(new StartAction());
		QuitButton.addActionListener(new Bet_Quit_Action());
		HitButton.addActionListener(new HitAction());
		StayButton.addActionListener(new StayAction());
		
		buttonPanel.add(BetButton);
		buttonPanel.add(StartGameButton);
		buttonPanel.add(HitButton);
		buttonPanel.add(StayButton);
		buttonPanel.add(QuitButton);
		
		add(bettingPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	class Bet_Quit_Action implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("BET(RETRY)")) {
				/*BET버튼을 누르면*/
				showPanel=new JPanel();
				dealerPanel=new JPanel();
				playerPanel=new JPanel();
				dealerNorthPanel=new JPanel();
				playerNorthPanel=new JPanel();
				
				pCardIdx=0;
				dCardIdx=0;
				dealer=new Dealer();
				player=new Player(pName);
				carddeck=new CardDeck();
				
				dealerPanel.setLayout(new BorderLayout());
				dealerNorthPanel.setLayout(new GridLayout(1,0));
				dealerNorthPanel.add(new JLabel("DEALER"));
				dealerNorthPanel.add(new JLabel("SCORE : ", SwingConstants.RIGHT));
				dealerNorthPanel.add(dScoreLabel=new JLabel("?"));
				dealerPanel.add(dealerNorthPanel, BorderLayout.NORTH);
				dealerPanel.add(dshowcardsPanel=new ShowCardsPanel(), BorderLayout.CENTER);
				
				playerPanel.setLayout(new BorderLayout());
				playerNorthPanel.setLayout(new GridLayout(1,0));
				playerNorthPanel.add(new JLabel(pName));
				playerNorthPanel.add(new JLabel("SCORE : ", SwingConstants.RIGHT));
				playerNorthPanel.add(pScoreLabel=new JLabel("0"));
				playerPanel.add(playerNorthPanel, BorderLayout.NORTH);
				playerPanel.add(pshowcardsPanel=new ShowCardsPanel(), BorderLayout.CENTER);
			
				showPanel.setLayout(new GridLayout(2,1));
				showPanel.add(dealerPanel);
				showPanel.add(playerPanel);
				
				add(showPanel, BorderLayout.CENTER);
				while(true) {
				try {
				bettingMoney=JOptionPane.showInputDialog(dialog, "베팅할 금액을 입력하시오.", "BettingMoney", JOptionPane.OK_CANCEL_OPTION);
				BettingLabel.setText(bettingMoney);
			    Integer.parseInt(bettingMoney);
			    if( Integer.parseInt(bettingMoney)<0) {
			    	JOptionPane.showMessageDialog(dialog,"베팅금액은 양수로 입력해 주세요.", "error",JOptionPane.PLAIN_MESSAGE);
	                continue;
			    }
	            if( Integer.parseInt(bettingMoney)>(wallet.getMoney())/2) {
	            	JOptionPane.showMessageDialog(dialog, "최대 베팅금액 보유금액의 절반만 베팅 가능합니다.", "error",JOptionPane.PLAIN_MESSAGE);
	                continue;
				}
				break;
				}
				catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(dialog, "잘못 입력하셨습니다. 다시입력해 주세요.", "error",JOptionPane.PLAIN_MESSAGE);}
				}
				FirstPickTwoCards();
				
				dshowcardsPanel.addCard(dealer.getCardStr());
				dshowcardsPanel.addCard("hide");
				List<Card> pcards=player.getCards();
				for(Card c:pcards) {
					pshowcardsPanel.addCard(c.getStr());
				}
				dshowcardsPanel.repaint();
				pshowcardsPanel.repaint();
				
				settingButtonPanel_2();
				
				 try {
		                Thread.sleep(500);
		            	pshowcardsPanel.repaint();
		            } catch (InterruptedException exeption) {
		                exeption.printStackTrace();
		            }
			}
			else {
				/*QUIT버튼 누르면*/
				int confirm_result=JOptionPane.showConfirmDialog(dialog, "게임을 그만하시겠습니까?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
				if(confirm_result==JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(dialog , "게임을 종료합니다.\n\n"+"남아있는 금액 : "+Integer.toString(wallet.getMoney()),
							"GAMEOVER", JOptionPane.PLAIN_MESSAGE);
					Q=true;
					dialog.setVisible(false);
				}
			}
		}
	}
	
     private class StartAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			dshowcardsPanel.repaint();
			pshowcardsPanel.repaint();
			
			JOptionPane.showMessageDialog(dialog, "블랙잭게임을 시작합니다.", "GAMESTART", JOptionPane.PLAIN_MESSAGE);
			
			for(int i=0; i<FirstPickCnt; i++) {
				/*플레이어가 뽑은 처음 두장의 카드 중 A가 있다면 A값을 설정한다.*/
				pChooseValueOfA();
			}
			dScoreLabel.setText("?");
			pScoreLabel.setText(Integer.toString(player.getSumOfValues()));
			
			settingButtonPanel_3();
			
			if(result.isBurst(player)) {
				JOptionPane.showMessageDialog(dialog, "Burst! 카드합이"+result.NumOfBurst+"을 초과하였습니다.\n"
						+ "딜러가 이겼습니다.\n\n"+bettingMoney+"을 잃었습니다."
						, "GAMEOVER", JOptionPane.PLAIN_MESSAGE);
				wallet.lostMoney(Integer.parseInt(bettingMoney));
				
				settingButtonPanel_1();
			}
		}
	}
	
	private class HitAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			PickOneCard(player);
			pshowcardsPanel.addCard(player.getCardStr());
			pshowcardsPanel.repaint();
			pChooseValueOfA();
			pScoreLabel.setText(Integer.toString(player.getSumOfValues()));
			if(result.isBurst(player)) {
				/*Player의 Burst로 Dealer가 이겼을 경우*/
				JOptionPane.showMessageDialog(dialog, "Burst! 카드합이"+result.NumOfBurst+"을 초과하였습니다.\n"
						+ "딜러가 이겼습니다.\n\n"+bettingMoney+"을 잃었습니다."
						, "GAMEOVER", JOptionPane.PLAIN_MESSAGE);
				wallet.lostMoney(Integer.parseInt(bettingMoney));
				
				settingButtonPanel_1();
			}
			
			MoneyLabel.setText(Integer.toString(wallet.getMoney()));
		}
	}
	
	private class StayAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int confirm_result=JOptionPane.showConfirmDialog(dialog, "STAY를 하시면 딜러의 패가 공개됩니다.\n"
					+"STAY를 하시겠습니까?", "Stay", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(confirm_result==JOptionPane.YES_OPTION) {//--if(confirm)
				dshowcardsPanel.setCard(dealer.getCardStr(), 1);//딜러의 공개되지 않은 패를 공개시킨다.
				dshowcardsPanel.repaint();
				dFirstChooseValueOfA();//딜러의 처음받은 2장의 카드패 중에 A가 있다면 
									  //dFirstChooseValueOfA함수의 조건에 맞게 A값 결정
				dScoreLabel.setText(Integer.toString(dealer.getSumOfValues()));
				
				while((dealer.getSumOfValues()<StdOfDealerDraw)||(dealer.getSumOfValues()<pScore)) {
					/*딜러의 카드합이 17이상이 될때까지 또는 플레이어의 카드합 보다 클때까지 카드를 뽑는다.*/
					JOptionPane.showMessageDialog(dialog, "딜러는 카드를 한장 더 뽑습니다.", "Dealer_Hit", JOptionPane.PLAIN_MESSAGE);
					PickOneCard(dealer);//딜러는 카드를 한장 뽑는다.
					dshowcardsPanel.addCard(dealer.getCardStr());//뽑은 카드를 dshowcardsPanel에 추가한다.
					dshowcardsPanel.repaint();
					if(dealer.isHaveA(dCardIdx)) {//딜러가 뽑은 카드가 A일때
						if(dealer.getSumOfValues()+11>21)//이때 A의 값을 11이라 하였을때 카드합이 Burst라면
							dealer.setValueA(1, dCardIdx++);//A의 값을 1로 한다.
						else
							dealer.setValueA(11, dCardIdx++);//A의 값을 11로 하였을때 Burst가 안되면 A의 값을 11로 한다.
					}
					dScoreLabel.setText(Integer.toString(dealer.getSumOfValues()));//딜러의 Score을 갱신한다.
				}//While

				if(result.isBurst(dealer)) {//--if(1)
					/*dealer가 Burst라면*/
					if(player.getSumOfValues()==21) {
						/*블랙잭으로 player가 이겼을 경우*/
						wallet.winbyBlackJack(Integer.parseInt(bettingMoney));
						JOptionPane.showMessageDialog(dialog, "BLACKJACK으로 "+player.getName()+"님이 이겼습니다.\n\n"
								+wallet.DoubleMoney(bettingMoney)+"을 얻었습니다.","GAMEOVER", JOptionPane.PLAIN_MESSAGE);
					}
					else {
						/*player가 이겼을 경우*/
						wallet.winMoney(Integer.parseInt(bettingMoney));
						JOptionPane.showMessageDialog(dialog, player.getName()+"님이 이겼습니다.\n\n"
								+bettingMoney+"을 얻었습니다.","GAMEOVER", JOptionPane.PLAIN_MESSAGE);
					}
				}//--if(1)
				else {
					/*Dealer와 Player모두 Burst없이 게임이 종료됬을 경우*/
					result.getWinner1(dialog, dealer, player, wallet, Integer.parseInt(bettingMoney));
				}
				MoneyLabel.setText(Integer.toString(wallet.getMoney()));
				
				settingButtonPanel_1();
			}//--if(confirm)
		}
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
    private void settingButtonPanel_1() {
		BetButton.setEnabled(true);
		StartGameButton.setEnabled(false);
		QuitButton.setEnabled(true);
		HitButton.setEnabled(false);
		StayButton.setEnabled(false);
	}
    private void settingButtonPanel_2() {
		BetButton.setEnabled(false);
		StartGameButton.setEnabled(true);
		QuitButton.setEnabled(false);
		HitButton.setEnabled(false);
		StayButton.setEnabled(false);
	}
    private void settingButtonPanel_3() {
		BetButton.setEnabled(false);
		StartGameButton.setEnabled(false);
		QuitButton.setEnabled(false);
		HitButton.setEnabled(true);
		StayButton.setEnabled(true);
	}
	/*
	 * When the game starts, player and dealer pick each two cards.
	 * And then they open cards.
	 * @param carddeck, player, dealer
	 */
	private void FirstPickTwoCards() {
		//System.out.println("카드를 나눠드리겠습니다.");
		for(int i=0;i<FirstPickCnt;i++) {
			Card pickedcard=carddeck.PickedCard();
			player.PickCard(pickedcard);
			
			pickedcard=carddeck.PickedCard();
			dealer.PickCard(pickedcard);
		}
		/*player.FirstOpenCards();
		System.out.println();
		dealer.FirstOpenCards();
		System.out.println();*/
	}
	/*
	 * Pick a card after pick two cards at first.
	 * @param carddeck, person who can be dealer or player
	 */
	private void PickOneCard(People person) {
		Card pickedcard=carddeck.PickedCard();
		person.PickCard(pickedcard);
	}
	/*
	 * If a last card picked by player is A, player have to decide the value of A 1 or 11.
	 * @param sc, player
	 * @param pCardIdx which is the index of player picking
	 */
	private void pChooseValueOfA() {
	
		if(player.isHaveA(pCardIdx++)) {
			String[] buttons= {"1","11"};			
			int option_result=JOptionPane.showOptionDialog(dialog, pCardIdx+"번째 카드의 A의 값을 선택하시오.",
					"ChooseValueA", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					null, buttons, buttons[0]);
			int a;
			if(option_result==0) a=1;
			else a=11;
			player.setValueA(a, pCardIdx-1);
			}
	}
	/*
	 * If it has A among two cards picked at first, set value A according to following algorithm.
	 * @param dealer
	 * @param dCardIdx which is the index of dealer picking
	 */
	private void dFirstChooseValueOfA() {
		if(dealer.isHaveA(dCardIdx)) {
			/*처음 뽑은 2장의 카드 중 첫번재 카드가 A일 때 이때 A의 값은 11로 계산한다.*/
			dealer.setValueA(11, dCardIdx);
			dCardIdx++;
			if(dealer.isHaveA(dCardIdx))
				/*처음 뽑은 2장의 카드가 모두 A일 때 두번째 카드의 A의 값은 1로 계산한다.*/
				dealer.setValueA(1, dCardIdx);
		}
		else {
			dCardIdx++;
			if(dealer.isHaveA(dCardIdx))
				/*처음 뽑은 2장의 카드 중 첫번재 카드는 A가 아니고 두번째 카드가 A일 때 이때 A의 값은 11로 계산한다*/
				dealer.setValueA(11, dCardIdx);
		}
		dCardIdx++;
		//함수 실행후 dCardIdx=2
	}
	
	public boolean StartGameDialog(Component parent, String title) {
		Q=false;
		
		Frame owner=null;
		if(parent instanceof Frame)
			owner=(Frame)parent;
		else
			owner=(Frame)SwingUtilities.getAncestorOfClass(Frame.class, parent);
		
		if(dialog==null || dialog.getOwner()!=owner) {
			dialog=new JDialog(owner, true);
			dialog.add(this);
			dialog.pack();
		}
		
		dialog.setTitle(title);
		dialog.setVisible(true);
		return Q;
	}
}

