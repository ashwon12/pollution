package pollutionproject;

public class QuizItem {

	private String quiz; // 퀴즈 문제
	private boolean answer; // 퀴즈 답

	public QuizItem(String quiz, boolean answer) { // 생성자 메소드
		this.quiz = quiz;
		this.answer = answer;
	}

	public boolean isRight(boolean user) {
		if (user == answer) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getAnswer() {
		return answer;
	}

	public String getQuiz() {
		return quiz;
	}
}
