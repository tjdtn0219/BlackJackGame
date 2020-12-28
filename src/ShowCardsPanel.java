import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShowCardsPanel extends JComponent{
	private String imagePath="images/";
	private static final int DEFAULT_WIDTH=600;
	private static final int DEFAULT_HEIGHT=100;
	private static final int DEFAULT_GAP=35;
	private ArrayList<Image> Cards;
	
	public ShowCardsPanel() {
		Cards=new ArrayList<>();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void paintComponent(Graphics g) {
		int d=0;
		for(Image card:Cards) {
			g.drawImage(card, 5+d, 5, this);
			d+=DEFAULT_GAP;
		}
	}
	
	public void addCard(String str) {
		Image Img=StrToImage(str);
		Cards.add(Img);		
	}
	
	private Image StrToImage(String str) {
		ImageIcon originIcon=new ImageIcon(imagePath+""+str+".png");
		Image Img=setImageScale(originIcon).getImage();
		return Img;
	}
	
	private ImageIcon setImageScale(ImageIcon originIcon) {
		Image originImg=originIcon.getImage();
		Image changedImg=originImg.getScaledInstance(30, 40, Image.SCALE_SMOOTH);
		ImageIcon Icon=new ImageIcon(changedImg);
		
		return Icon;
	}
	
	public void setCard(String str, int idx) {
		Image Img=StrToImage(str);
		Cards.set(idx, Img);
	}

}
