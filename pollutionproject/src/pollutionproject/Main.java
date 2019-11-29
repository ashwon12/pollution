package pollutionproject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JFrame{
	//큰 틀
	static public Container contentPane;
	//안에 넣을 카드 패널
	private JPanel cards;
	
	//메뉴바, 툴바 넣기
	private JMenuBar menubar;//메뉴바
	
	private	JMenu Home;//홈화면
	private JMenuItem goHome;
	
	private	JMenu Inquiry;//조회
	private JMenuItem m_area;//지역별
	private JMenuItem m_gas;//기체 별
	
	private JMenu Average; //농도평균
	private JMenuItem m_month; //월별
	private JMenuItem m_season; //계절별
	
	private	JMenu Today; //오늘의~
	private JMenuItem m_talk; //말
	private JMenuItem m_quiz; //퀴즈
	
	private JMenu File;
	private JMenuItem m_save; //엑셀파일로 저장
	
	//툴바에 들어갈 버튼들
	private JButton t_home;
	private JButton t_area;
	private JButton t_gas;
	private JButton t_month;
	private JButton t_season;
	private JButton t_talk;
	private JButton t_quiz;
	private JButton t_save;
	
	//각 기능 패널(카드)
	private JPanel c_home;
	private JPanel c_quiz;
	private JPanel c_talk;
	private JPanel c_month;
	private JPanel c_season;
	private JPanel c_gas;
	private JPanel c_area;

	//생성자, 여기에서 컴포넌트들 설정
	public Main() {
		//현재 큰 틀
		contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(new BorderLayout());
		setTitle("꽃2조");
		setSize(1100,790);
		setLocation(400,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CreateMenu(); //메뉴생성
		CreateToolBar(); //툴바생성
		
		//카드레이아웃 생성
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		//넣을 카드들 생성
		c_home = new Home();
		c_quiz = new Quiz();
		c_talk = new Talk();
		c_month = new Month();
		c_season = new Season();
		c_gas = new Gas();
		c_area = new Area();
		
		cards.add(c_home,"function_Home");
		cards.add(c_area,"function_Area");
		cards.add(c_gas,"function_Gas");
		cards.add(c_month,"function_Month");
		cards.add(c_season,"function_Season");
		cards.add(c_talk,"function_Talk");
		cards.add(c_quiz,"function_Quiz");

		
		//큰 틀에 카드 넣기
		contentPane.add(cards);
	}
	//메뉴만들기
	private void CreateMenu() {
		Listener MenuListner = new Listener();
		
		menubar = new JMenuBar(); //메뉴바 생성
	
		Home = new JMenu("HOME");
		Home.setPreferredSize(new Dimension(100,40));
		Home.setFont(Home.getFont().deriveFont(23.0f));
		Inquiry= new JMenu("SHOW");
		Inquiry.setPreferredSize(new Dimension(100,40));
		Inquiry.setFont(Inquiry.getFont().deriveFont(23.0f));
		Today= new JMenu("TODAY'S");
		Today.setPreferredSize(new Dimension(125,40));
		Today.setFont(Today.getFont().deriveFont(23.0f));
		File = new JMenu("FILE");
		File.setPreferredSize(new Dimension(100,40));
		File.setFont(Inquiry.getFont().deriveFont(23.0f));
		
		goHome = new JMenuItem("Main");
		goHome.setPreferredSize(new Dimension(100,40));
		goHome.setFont(Today.getFont().deriveFont(15.0f));
		//리스너를 달아준다.
		goHome.addActionListener(MenuListner);
		
		m_area = new JMenuItem("Area");
		m_area.setPreferredSize(new Dimension(100,40));
		m_area.setFont(Today.getFont().deriveFont(15.0f));
		m_area.addActionListener(MenuListner);
		
		m_gas = new JMenuItem("Gas");
		m_gas.setPreferredSize(new Dimension(100,40));
		m_gas.setFont(Today.getFont().deriveFont(15.0f));
		m_gas.addActionListener(MenuListner);
		
		Average= new JMenu("Average");
		Average.setPreferredSize(new Dimension(100,40));
		Average.setFont(Today.getFont().deriveFont(15.0f));
		m_month = new JMenuItem("Month");
		m_month.setPreferredSize(new Dimension(100,40));
		m_month.setFont(Today.getFont().deriveFont(15.0f));
		m_month.addActionListener(MenuListner);
		m_season = new JMenuItem("Season");
		m_season.setPreferredSize(new Dimension(100,40));
		m_season.setFont(Today.getFont().deriveFont(15.0f));
		m_season.addActionListener(MenuListner);
		
		m_talk = new JMenuItem("Talk");
		m_talk.setPreferredSize(new Dimension(125,40));
		m_talk.setFont(Today.getFont().deriveFont(15.0f));
		m_talk.addActionListener(MenuListner);
	
		m_quiz = new JMenuItem("Quiz");
		m_quiz.setPreferredSize(new Dimension(125,40));
		m_quiz.setFont(Today.getFont().deriveFont(15.0f));
		m_quiz.addActionListener(MenuListner);
		
		m_save = new JMenuItem("Save");
		m_save.setPreferredSize(new Dimension(100,40));
		m_save.setFont(Today.getFont().deriveFont(15.0f));
		m_save.addActionListener(MenuListner);
		
		menubar.add(Home);
		menubar.add(Inquiry);
		menubar.add(Today);
		menubar.add(File);
		
		Home.add(goHome);
		
		Inquiry.add(m_area);
		Inquiry.add(m_gas);
		Average.add(m_month);
		Average.add(m_season);
		Inquiry.add(Average);
		
		Today.add(m_talk);
		Today.add(m_quiz);
		
		File.add(m_save);
		
		//메뉴바 색상 지정
		menubar.setBorder(BorderFactory.createLineBorder(Color.gray));
		menubar.setBackground(Color.white);
		
		setJMenuBar(menubar);
	}
	//툴바만들기
	private void CreateToolBar() {
		//툴바리스너 생성
		Listener ToolbarListener = new Listener();
		//툴바 생성
		JToolBar tool = new JToolBar("toolbar");
		tool.setBackground(Color.LIGHT_GRAY);
		
		t_home = new JButton(new ImageIcon("./images/home.png"));		
		t_home.addActionListener(ToolbarListener);
	
		t_area= new JButton(new ImageIcon("./images/area.png"));
		t_area.addActionListener(ToolbarListener);
		
		t_gas= new JButton(new ImageIcon("./images/기체별.jpg"));
		t_gas.addActionListener(ToolbarListener);
		
		t_month= new JButton(new ImageIcon("./images/월별.png"));
		t_month.addActionListener(ToolbarListener);
		
		t_season= new JButton(new ImageIcon("./images/계절별.png"));
		t_season.addActionListener(ToolbarListener);
		
		t_talk = new JButton(new ImageIcon("./images/talk.png"));
		t_talk.addActionListener(ToolbarListener);
		
		t_quiz= new JButton(new ImageIcon("./images/퀴즈.png"));
		t_quiz.addActionListener(ToolbarListener);
		
		t_save = new JButton(new ImageIcon("./images/save.png"));
		t_save.addActionListener(ToolbarListener);
		
		tool.add(t_home);
		tool.addSeparator();
		tool.add(t_area);
		tool.addSeparator();
		tool.add(t_gas);
		tool.addSeparator();
		tool.add(t_month);
		tool.addSeparator();
		tool.add(t_season);
		tool.addSeparator();
		tool.add(t_talk);
		tool.addSeparator();
		tool.add(t_quiz);
		tool.addSeparator();
		tool.add(t_save);
		
		contentPane.add(tool,BorderLayout.NORTH);

	}

	//리스너
	class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			CardLayout pickCard = (CardLayout) cards.getLayout();
			if(e.getSource() == goHome || e.getSource() == t_home) {
				System.out.println("메인이 눌렸어용!");
				pickCard.show(cards,"function_Home");
			}else if(e.getSource() == m_area|| e.getSource() == t_area) {
				System.out.println("지역별 조회가 눌렸어용!");
				pickCard.show(cards,"function_Area");
			}else if(e.getSource() == m_gas|| e.getSource() == t_gas) {
				System.out.println("기체별조회가 눌렸어용!");
				pickCard.show(cards,"function_Gas");
			}else if(e.getSource() == m_month|| e.getSource() == t_month) {
				System.out.println("월별 평균조회가 눌렸어용!");
				pickCard.show(cards,"function_Month");
			}else if(e.getSource() == m_season|| e.getSource() == t_season) {
				System.out.println("계절별 평균 조회가 눌렸어용!");
				pickCard.show(cards,"function_Season");
			}else if(e.getSource() == m_talk|| e.getSource() == t_talk) {
				System.out.println("오늘의 말이 눌렸어용!");
				pickCard.show(cards,"function_Talk");
			}else if(e.getSource() == m_quiz|| e.getSource() == t_quiz) {
				System.out.println("오늘의 퀴즈가 눌렸어용!");
				pickCard.show(cards,"function_Quiz");
			}else if(e.getSource() == m_save || e.getSource() == t_save) {
				System.out.println("저장이 눌렸어여");
				
			}
		}
	}
}
