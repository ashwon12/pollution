package pollutionproject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JFrame {
	// 큰 틀
	static public Container contentPane;
	// 안에 넣을 카드 패널
	private JPanel cards;

	// 메뉴바, 툴바 넣기
	private JMenuBar menubar;// 메뉴바

	private JMenu Home;// 홈화면
	private JMenuItem goHome;

	private JMenu Inquiry;// 조회
	private JMenuItem m_area;// 지역별
	private JMenuItem m_gas;// 기체 별

	private JMenu Average; // 농도평균
	private JMenuItem m_month; // 월별
	private JMenuItem m_season; // 계절별

	private JMenu Today; // 오늘의~
	private JMenuItem m_talk; // 말
	private JMenuItem m_quiz; // 퀴즈

	private JMenu File;
	public JMenuItem m_save; // 엑셀파일로 저장

	private JMenu Modify; // 데이터 수정
	private JMenuItem m_plus; // 삽입+수정
	private JMenuItem m_minus; // 삭제

	// 툴바에 들어갈 버튼들
	private JButton t_home;
	private JButton t_area;
	private JButton t_gas;
	private JButton t_month;
	private JButton t_season;
	private JButton t_talk;
	private JButton t_quiz;
	private JButton t_plus;
	private JButton t_minus;
	public JButton t_save;
	private JButton t_minimize;

	// 각 기능 패널(카드)
	private JPanel c_home;
	private JPanel c_quiz;
	private JPanel c_talk;
	private JPanel c_month;
	private JPanel c_season;
	private JPanel c_gas;
	private JPanel c_area;
	private JPanel c_plus;
	private JPanel c_minus;

	private boolean isminimize=false;
	
	// 생성자, 여기에서 컴포넌트들 설정
	public Main() {
		// 현재 큰 틀
		contentPane = getContentPane();
		contentPane.setBackground(Color.white);
		contentPane.setLayout(new BorderLayout());
		setTitle("꽃2조");
		setSize(1100, 790);
		setLocation(400, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CreateMenu(); // 메뉴생성
		CreateToolBar(); // 툴바생성

		// 카드레이아웃 생성
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		// 넣을 카드들 생성
		c_home = new Home();
		c_quiz = new Quiz();
		c_talk = new Talk();
		c_month = new Month();
		c_season = new Season();
		c_gas = new Gas();
		c_area = new Area();
		c_plus = new Plus();
		c_minus = new Minus();

		cards.add(c_home, "function_Home");
		cards.add(c_area, "function_Area");
		cards.add(c_gas, "function_Gas");
		cards.add(c_month, "function_Month");
		cards.add(c_season, "function_Season");
		cards.add(c_talk, "function_Talk");
		cards.add(c_quiz, "function_Quiz");
		cards.add(c_plus, "function_Plus");
		cards.add(c_minus, "function_Minus");

		// 큰 틀에 카드 넣기
		contentPane.add(cards);
	}

	// 메뉴만들기
	private void CreateMenu() {
		Listener MenuListner = new Listener();

		menubar = new JMenuBar(); // 메뉴바 생성

		Home = new JMenu("HOME");
		Home.setPreferredSize(new Dimension(100, 40));
		Home.setFont(Home.getFont().deriveFont(23.0f));
		Inquiry = new JMenu("SHOW");
		Inquiry.setPreferredSize(new Dimension(100, 40));
		Inquiry.setFont(Inquiry.getFont().deriveFont(23.0f));
		Today = new JMenu("TODAY'S");
		Today.setPreferredSize(new Dimension(125, 40));
		Today.setFont(Today.getFont().deriveFont(23.0f));
		Modify = new JMenu("MODIFY");
		Modify.setPreferredSize(new Dimension(135, 40));
		Modify.setFont(Today.getFont().deriveFont(23.0f));
		File = new JMenu("FILE");
		File.setPreferredSize(new Dimension(100, 40));
		File.setFont(Inquiry.getFont().deriveFont(23.0f));

		goHome = new JMenuItem("Main");
		goHome.setPreferredSize(new Dimension(100, 40));
		goHome.setFont(Today.getFont().deriveFont(15.0f));
		// 리스너를 달아준다.
		goHome.addActionListener(MenuListner);

		m_area = new JMenuItem("Area");
		m_area.setPreferredSize(new Dimension(100, 40));
		m_area.setFont(Today.getFont().deriveFont(15.0f));
		m_area.addActionListener(MenuListner);

		m_gas = new JMenuItem("Gas");
		m_gas.setPreferredSize(new Dimension(100, 40));
		m_gas.setFont(Today.getFont().deriveFont(15.0f));
		m_gas.addActionListener(MenuListner);

		Average = new JMenu("Average");
		Average.setPreferredSize(new Dimension(100, 40));
		Average.setFont(Today.getFont().deriveFont(15.0f));
		m_month = new JMenuItem("Month");
		m_month.setPreferredSize(new Dimension(100, 40));
		m_month.setFont(Today.getFont().deriveFont(15.0f));
		m_month.addActionListener(MenuListner);
		m_season = new JMenuItem("Season");
		m_season.setPreferredSize(new Dimension(100, 40));
		m_season.setFont(Today.getFont().deriveFont(15.0f));
		m_season.addActionListener(MenuListner);

		m_talk = new JMenuItem("Talk");
		m_talk.setPreferredSize(new Dimension(125, 40));
		m_talk.setFont(Today.getFont().deriveFont(15.0f));
		m_talk.addActionListener(MenuListner);

		m_quiz = new JMenuItem("Quiz");
		m_quiz.setPreferredSize(new Dimension(125, 40));
		m_quiz.setFont(Today.getFont().deriveFont(15.0f));
		m_quiz.addActionListener(MenuListner);

		m_plus = new JMenuItem("Insert / Update");
		m_plus.setPreferredSize(new Dimension(135, 40));
		m_plus.setFont(Today.getFont().deriveFont(15.0f));
		m_plus.addActionListener(MenuListner);
		m_minus = new JMenuItem("Delete");
		m_minus.setPreferredSize(new Dimension(135, 40));
		m_minus.setFont(Today.getFont().deriveFont(15.0f));
		m_minus.addActionListener(MenuListner);

		m_save = new JMenuItem("Save");
		m_save.setPreferredSize(new Dimension(100, 40));
		m_save.setFont(Today.getFont().deriveFont(15.0f));

		menubar.add(Home);
		menubar.add(Inquiry);
		menubar.add(Today);
		menubar.add(Modify);
		menubar.add(File);

		Home.add(goHome);

		Inquiry.add(m_area);
		Inquiry.add(m_gas);
		Average.add(m_month);
		Average.add(m_season);
		Inquiry.add(Average);

		Today.add(m_talk);
		Today.add(m_quiz);

		Modify.add(m_plus);
		Modify.add(m_minus);

		File.add(m_save);

		// 메뉴바 색상 지정
		menubar.setBorder(BorderFactory.createLineBorder(Color.gray));
		menubar.setBackground(Color.white);

		setJMenuBar(menubar);
	}

	// 툴바만들기
	private void CreateToolBar() {
		// 툴바리스너 생성
		Listener ToolbarListener = new Listener();
		// 툴바 생성
		JToolBar tool = new JToolBar("toolbar");
		tool.setBackground(Color.LIGHT_GRAY);

		t_home = new JButton(new ImageIcon("./images/home.png"));
		t_home.addActionListener(ToolbarListener);

		t_area = new JButton(new ImageIcon("./images/area.png"));
		t_area.addActionListener(ToolbarListener);

		t_gas = new JButton(new ImageIcon("./images/기체별.jpg"));
		t_gas.addActionListener(ToolbarListener);

		t_month = new JButton(new ImageIcon("./images/월별.png"));
		t_month.addActionListener(ToolbarListener);

		t_season = new JButton(new ImageIcon("./images/계절별.png"));
		t_season.addActionListener(ToolbarListener);

		t_talk = new JButton(new ImageIcon("./images/talk.png"));
		t_talk.addActionListener(ToolbarListener);

		t_quiz = new JButton(new ImageIcon("./images/퀴즈.png"));
		t_quiz.addActionListener(ToolbarListener);

		t_plus = new JButton(new ImageIcon("./images/plus.png"));
		t_plus.addActionListener(ToolbarListener);

		t_minus = new JButton(new ImageIcon("./images/minus.png"));
		t_minus.addActionListener(ToolbarListener);

		t_save = new JButton(new ImageIcon("./images/save.png"));

		t_minimize = new JButton(new ImageIcon("./images/minimize.png"));
		t_minimize.addActionListener(ToolbarListener);

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
		tool.add(t_plus);
		tool.addSeparator();
		tool.add(t_minus);
		tool.addSeparator();
		tool.add(t_save);
		tool.addSeparator();
		tool.add(t_minimize);

		contentPane.add(tool, BorderLayout.NORTH);

	}

	// 저장 리스너 추가해주기
	private void addSaveListener(ActionListener add) {
		if (t_save.getActionListeners().length != 0) {
			// 삭제
			for (int i = 0; i < t_save.getActionListeners().length; i++) {
				t_save.removeActionListener(t_save.getActionListeners()[i]);
				m_save.removeActionListener(m_save.getActionListeners()[i]);
			}
		}
		// 새로운것 추가
		if (add == null) {// home,talk,quiz
			System.out.println("리스너 추가가 필요 없습니다.");
			t_save.addActionListener(e -> JOptionPane.showMessageDialog(null, "저장할 수 있는 데이터가 없습니다!"));
			m_save.addActionListener(e -> JOptionPane.showMessageDialog(null, "저장할 수 있는 데이터가 없습니다!"));
			return;
		}
		t_save.addActionListener(add);
		m_save.addActionListener(add);
	}

	// 리스너
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			CardLayout pickCard = (CardLayout) cards.getLayout();
			if (e.getSource() == goHome || e.getSource() == t_home) {
				System.out.println("메인이 눌렸어용!");
				pickCard.show(cards, "function_Home");

				addSaveListener(null);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_area || e.getSource() == t_area) {
				System.out.println("지역별 조회가 눌렸어용!");
				pickCard.show(cards, "function_Area");

				addSaveListener(Area.areaSave);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_gas || e.getSource() == t_gas) {
				System.out.println("기체별조회가 눌렸어용!");
				pickCard.show(cards, "function_Gas");

				addSaveListener(Gas.gasSave);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_month || e.getSource() == t_month) {
				System.out.println("월별 평균조회가 눌렸어용!");
				pickCard.show(cards, "function_Month");

				addSaveListener(Month.monthSave);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_season || e.getSource() == t_season) {
				System.out.println("계절별 평균 조회가 눌렸어용!");
				pickCard.show(cards, "function_Season");

				addSaveListener(Season.seasonSave);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_talk || e.getSource() == t_talk) {
				System.out.println("오늘의 말이 눌렸어용!");
				pickCard.show(cards, "function_Talk");

				addSaveListener(null);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_quiz || e.getSource() == t_quiz) {
				System.out.println("오늘의 퀴즈가 눌렸어용!");
				pickCard.show(cards, "function_Quiz");

				addSaveListener(null);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_plus || e.getSource() == t_plus) {
				System.out.println("데이터 추가/수정이 눌렷쪙!");
				pickCard.show(cards, "function_Plus");

				addSaveListener(null);

				if (isminimize) {
					setScreenMax();
				}

			} else if (e.getSource() == m_minus || e.getSource() == t_minus) {
				System.out.println("데이터 삭제가 눌렸졍!");
				pickCard.show(cards, "function_Minus");

				addSaveListener(null);

				if (isminimize) {
					setScreenMax();
				}

			}else if(e.getSource() == t_minimize) {
				System.out.println("미니마이즈 눌렷어어어어");
				if(!isminimize) {
					setScreenMin();
				}else {
					setScreenMax();
				}
			}
		}

		 public void setScreenMin() {
	         Main.this.setSize(930, 165);
	         Main.this.setLocation(996, 0);
	         isminimize = !isminimize;
	      }

	      public void setScreenMax() {
	         Main.this.setSize(1100, 790);
	         Main.this.setLocation(400, 100);
	         isminimize = !isminimize;
	      }


	}
}
