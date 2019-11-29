package pollutionproject;
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class Talk extends JPanel{
	
	private JPanel main;
	private JLabel result;
	private Random r;
	
	private String[] talk = {"자연과 가까울수록 병은 멀어지고, 자연과 멀수록 병은 가까워진다.  <br>-괴테-",
			"자연이 아니면 몸 안의 질병을 결코 이겨낼 수 없다. <br>-히포크라테스-",
			"지구 상의 생물들 중 어느 한 종을 잃는다는 것은 비행기 날개에 달린 나사못을 빼는 것과 같다. <br>-폴 에를리히-",
			"사람의 손으로 만든 모든 것은 반드시 아름답거나 추한 모습을 띠게 된다. <br>만약 자연과 조화를 이루면 아름다운 것이고, <br>자연과 조화를 이루지 못하거나 자연에 위협을 가하면 추한 것이다. <br>-윌리암 모리스-",
			"사람은 바람과 물결이 그를 만들었다. <br>-짐 툴리-",
			"다음 세대를 위협하는 많은 화학물질은 우리의 몸으로 들어올 길을 찾고 있다. <br>안전하고 오염되지 않은 곳은 어디에도 없다. <br>-테오 콜본-",
			"나무는 행동의 상징이다. <br>내일 당장 변화가 오지 않더라도 약간의 차이는 분명 생긴다.<br>작은 차이의 첫 걸음은 나무를 심는 것이다. <br>-왕가리 마타이-",
			"자연계에서 등을 돌리는 것은 결국 우리 행복에서 등을 돌리는 것과 같다. <br>-사무엘 존슨-",
			"자연에는 비약이 없다. <br>-다윈-",
			"자연에는 완벽한 신의 형상이라고 느끼게 하는 것이 있는가 하면,<br>전혀 그렇지 않다고 느끼게 하는 불완전한 것도 있다. <br>-파스칼-",
			"자연에 따라 살아야 한다. <br>-제논-",
			"자연계에서 멀어져 가는 일없이 자연이 우리에게 보여주는 법칙과 본보기에 따라 <br>우리의 행동 방침을 정해 나간다면 우리는 참된 지혜를 터득할 수 있을 것이다. <br>-세네카-",
			"자연에는 보수도 형벌도 없고 결과가 있을 뿐이다. <br>- 로버트 그린 잉거솔-",
			"자연은 결코 우리를 속이지 않는다. 우리를 속이는 것은 언제나 우리 자신이다. <br>-루소-",
			"자연은 사원이 아니고 한갓 커다란 공장이다. 그리고 인간은 거기서 일하는 노동자이다. <br>-투르게네프-",
			"자연의 도는 말로써 표현할 수 없고 자연의 덕은 인위적인 노력으로 이룰 수 없다. <br>-장자-",
			"자연은 신의 묵시이며, 예술은 인간의 묵시이다. <br>-H.W. 롱펠로우-",
			"자연의 자태만 보아도 그것은 하나의 즐거움이다. <br>-R.W. 에머슨-",
			"자연은 신의 예술품이다. <br>-단테-",
			"자연은 자연을 사랑한 마음을 결코 저버리지 않는다.<br>우리의 일생 전체를 통해서 즐거움에서 즐거움으로 인도해 주는 것은 자연의 특권이다. <br>-윌리엄 워즈워스-",
			"자연은 언제나 완전하다. 결코 잘못을 저지르지 않는다.<br>우리의 입장, 우리의 눈에서 잘못이 있는 것이다. <br>-로댕-",
			"자연은 살아있는 한 권의 책이다. 불가해하면서도 뚜렷하고 명백하다. <br>-괴테-"};
	
	public Talk() {
		
		setLayout(new BorderLayout());
		setSize(1000,600);
	
		main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setBackground(Color.white);
	
		result = new JLabel();
		result.setFont(result.getFont().deriveFont(20.0f));
		result.setBackground(Color.white);
		result.setHorizontalAlignment(JLabel.CENTER);
		
		r = new Random();
		String s = "<html><hr>";
		s += talk[r.nextInt(22)];
		s +="<hr></html>";
		result.setText(s);

		main.add(result,BorderLayout.CENTER);
		
		add(main);

	}
}
