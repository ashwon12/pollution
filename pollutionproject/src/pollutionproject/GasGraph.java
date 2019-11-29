package pollutionproject;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;

public class GasGraph extends JFrame{
	
	private Container contentPane;
	private JPanel panel1;
	private JButton close;
	
	//폰트
	private Font big = new Font("맑은 고딕", Font.BOLD, 23);
	private Font middle = new Font("맑은 고딕", Font.BOLD, 17);
	private Font small = new Font("맑은 고딕", Font.BOLD, 10);
	private String[] date = new String[25];
	private String[] area = new String[25];
	private String[] data = new String[25];
	private String[] col = new String[25];
	private String myGas;

	
	
	public GasGraph(String[] date,String[] area, String[] data, String[] col, String myGas){
		
		this.date = date;
		this.area = area;
		this.myGas = myGas;
		this.data = data;
		this.col = col;
		
		this.setTitle(this.myGas + " 그래프");
		this.setSize(1450,600);
		this.setLocation(45, 150);
		
		contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(new BorderLayout());
	    panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER,0,30));
		
	    //닫기 버튼
	    close = new JButton("close");
	    close.setFont(middle);
		
	    //색상 지정
        close.setBackground(new Color(242,242,242));
        contentPane.setBackground(Color.white);
        panel1.setBackground(Color.white);

        //리스너 호출
        Listener listener = new Listener();
        close.addActionListener(listener);

		panel1.add(close);
		contentPane.add(new drawGraph(),BorderLayout.CENTER);
        contentPane.add(panel1,BorderLayout.SOUTH);

		
		this.setVisible(true);
		
	}
	
	class drawGraph extends JPanel{
		public void paint(Graphics g) {
			 super.paint(g);
	         
	         g.clearRect(0, 0, getWidth(), getHeight());
	         g.drawLine(100, 400, 1380, 400); // x축
	         g.drawLine(100, 50, 100, 400); // y축
	         
	         //최대값
	         double max = Double.parseDouble((String)data[0]);
	         //최소값
	         double min = Double.parseDouble((String)data[24]);
	         //간격
	         double val = Math.round(((max-min)/15)*1000) / 1000.0;
	         
	         
	         
	         	//y축
	         	int y = 0;
	         	Double[] range = new Double[16];
	            for(int cnt=80; cnt<400; cnt=cnt+20) {
	               g.setColor(new Color(189,189,189));
	               g.drawLine(100, cnt, 1380, cnt);
	               g.setColor(new Color(0,0,0));
	               
	               Double temp = Math.round((max-(y*val))*1000) / 1000.0;
	               range[y] = temp;
	               String str1 = Double.toString(temp);
	               g.drawString(str1, 50, cnt+5);
	               
	               y++;

	            }
	            
	            
	            //x축
	            int i = 0;
	            for(int cnt=110; cnt<1350 && i <25; cnt=cnt+64) {
	              g.drawString(area[i], cnt, 420);
	              g.drawString(date[i], cnt-10, 440);
	              i ++;
	            }
	            
	            
	            
	            //막대
	            for(i = 0; i < 20; i++) {
	            	double mg = Double.parseDouble((String)data[i]);
	            	for(int j = 0; j <15 ; j++) {
	            		//범위에 따라 막대 색 바꾸기
	            		if(col[i].equals("보통")) {
	            			g.setColor(Color.yellow);
	            		}else if(col[i].equals("좋음")) {
	            			g.setColor(Color.green);
	            		}else if(col[i].equals("나쁨")) {
	            			g.setColor(Color.ORANGE);
	            		}else if(col[i].equals("매우나쁨")) {
	            			g.setColor(Color.RED);
	            		}
	            		if(mg == range[j]) {
	            			g.fillRect(115+i*64,80+j*20,30,320-j*20);
	            		}else if (mg < range[j] && mg > range[j+1]) {
	            			g.fillRect(115+i*64,80+(j*20+10),30,320-(j*20+10));
	            		}
	            		if(mg < range[14]) {
	            			g.fillRect(115+i*64,80+(15*20+10),30,320-(15*20+10));
	            		}
	            	}
	            }
		}
	
	}
	private class Listener implements ActionListener{
	      @Override
	      public void actionPerformed(ActionEvent e) {;
	         if(e.getSource() == close) { //결과보기 버튼을 눌렀을 경우
	            //System.out.println("닫기 버튼 클릭");
	            setVisible(false);
	         }
	      }
	}
	
}
