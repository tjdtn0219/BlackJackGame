
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BlackJackFrame extends JFrame{
	JTextField playername;
	JTextField money;
	private BlackJackGameDialog dialog=null;
	
	public BlackJackFrame() {
		
		JPanel EnterPanel=new JPanel();
		JPanel ButtonPanel=new JPanel();
		
		EnterPanel.setLayout(new GridLayout(2,2));
		EnterPanel.add(new JLabel("사용할 이름을 입력하시오 : ", SwingConstants.RIGHT));
		EnterPanel.add(playername=new JTextField(""));
		EnterPanel.add(new JLabel("충전할 금액을 입력하시오 : ", SwingConstants.RIGHT));
		EnterPanel.add(money=new JTextField("0"));

		add(EnterPanel,BorderLayout.CENTER);

		JButton start=new JButton("START");
		JButton exit=new JButton("EXIT");
		start.addActionListener(new StartAction());
		exit.addActionListener((event)->System.exit(0));
		
		ButtonPanel.add(start);
		ButtonPanel.add(exit);
		add(ButtonPanel,BorderLayout.SOUTH);
		pack();
		
	}

	private class StartAction implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try {
				int m=Integer.parseInt(money.getText());
				if(m<=0) {
					throw new Exception();
				}
			if(dialog==null) dialog=new BlackJackGameDialog(playername.getText(), money.getText());
			if(dialog.StartGameDialog(BlackJackFrame.this, "BlackJackGame")){
				System.exit(0);}
		        }
		 catch(NumberFormatException nfe) {
			  JOptionPane.showMessageDialog(dialog,"숫자를 입력해주세요.", "error",JOptionPane.PLAIN_MESSAGE);}
		 catch(Exception e) {
		   JOptionPane.showMessageDialog(dialog,"양수만 입력해주세요.","error",JOptionPane.PLAIN_MESSAGE);}
	}
  }
}


