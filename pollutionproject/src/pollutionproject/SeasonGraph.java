package pollutionproject;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;

public class SeasonGraph extends JFrame {
	
	//패널
	Container contentpane;
	private JPanel South;
	private JButton close;
	
	//변수
	private double max; //최댓값
	private double min; // 최소값
	private double yVal; // y축간격
	private int xVal; // x축간격
	private Double[] range = new Double[16]; 
	private Double temp;
	private String yIndex;// y축 입력값  
	
	//가져온 값
	private String[] season = new String[5];
	private String[] data = new String[5];
	private String myGas;
	
	//폰트
	private Font small = new Font("",Font.PLAIN, 15);
	private Font middle = new Font("맑은 고딕", Font.BOLD, 17);
	
	public SeasonGraph(String [] season, String[] data,String myGas) {
			
		// 
		this.season = season;
		this.data =data;
		this.myGas = myGas;
		
		this.setTitle("계절별"+ myGas+ "평균 조회 그래프");
		this.setSize(700,600);
		this.setLocation(500,200); 
		
		//패널 및 클래스
		contentpane = getContentPane();
		contentpane.setLayout(new BorderLayout());
		South = new JPanel(new FlowLayout(FlowLayout.CENTER,0,30));
		
		//버튼
        close = new JButton("close");       
        close.setFont(middle);
        
        //색상 지정
        contentpane.setBackground(Color.white);
        close.setBackground(new Color(242,242,242));
        South.setBackground(Color.white);
        
        //리스너 호출
        Listener listener = new Listener();
        close.addActionListener(listener);
        
        //추가
        South.add(close);
        contentpane.add(new drawGraph(),BorderLayout.CENTER);
        contentpane.add(South,BorderLayout.SOUTH);
	
		this.setVisible(true); 
	}
	
	
	class drawGraph extends JPanel {
		public void paint(Graphics g) {
			
			super.paint(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawLine(100, 400, 600, 400); // x축
			g.drawLine(100, 50, 100, 400); // y축
			
			//최대값
			max = Double.parseDouble(data[0]);
			for(int i = 0; i < data.length; i++) {
				max = max > Double.parseDouble(data[i]) ? max : Double.parseDouble(data[i]);
			}
			//최솟값
			min = Double.parseDouble(data[0]);
			for(int i = 0; i < data.length; i++) {
				min = min < Double.parseDouble(data[i]) ? min : Double.parseDouble(data[i]);
			}

			//간격
			if(myGas == "sulfur") {
				yVal = Math.round(((max-min)/10)*10000) / 10000.0;
			}
			else {
				yVal = Math.round(((max-min)/10)*1000) / 1000.0;
			}
			xVal = (500/data.length+1);
			
			//y축 그리기 
            int a = 0;
            for(int cnt=80; cnt<400; cnt=cnt+20) {
               g.setColor(new Color(189,189,189));
               g.drawLine(100, cnt, 600, cnt);
               g.setColor(new Color(0,0,0));
               
               if( myGas == "sulfur") {
            	   if(max == min) {
            		   temp = Math.round((max-(a*0.0001))*10000) / 10000.0; 
            	   }
            	   else {
            		   temp = Math.round((max-(a*yVal))*10000) / 10000.0; 
            	   }
               }
               else {
            	   if(max == min) {
            		   temp = Math.round((max-(a*0.001))*1000) / 1000.0; 
            	   }
            	   else{
            		   temp = Math.round((max-(a*yVal))*1000) / 1000.0; 
            	   }
               }
               range[a] = temp;
               yIndex = Double.toString(temp);   
               g.drawString(yIndex, 60, cnt+5);    // yIndex을  (60,cnt+5) 좌표에 그리기
               a++;
            }
            
            //x축
            int i=0;
            g.setFont(small);
            for(int cnt=80+xVal/2; cnt<700 && i< data.length; cnt=cnt+xVal){	
            	g.drawString(season[i],cnt, 425);
            	i++;
            }
            
            //막대그리기
            g.setColor(new Color(248, 224, 230));
            for(i = 0; i <data.length; i++) {
            	double mg = Double.parseDouble((String)data[i]);
            	for(int j = 0; j <15 ; j++) {
            		if(mg == range[j]) {
            			g.fillRect((78+xVal/2)+i*xVal,80+j*20,35,320-j*20);
            		}else if (mg < range[j] && mg > range[j+1]) {
            			g.fillRect((78+xVal/2)+i*xVal,80+(j*20+10),35,320-(j*20+10));
            		}
            		if(mg < range[14]) {
            			g.fillRect((78+xVal/2)+i*xVal,80+(15*20+10),35,320-(15*20+10));
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
