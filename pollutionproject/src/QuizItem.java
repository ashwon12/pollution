
public class QuizItem {
	public String quiz; //퀴즈 문제
	public String answer; //퀴즈 답
	public String tf = ""; //정답 여부 판별
	      
	public QuizItem(String quiz, String answer) { //생성자 메소드
		this.quiz = quiz;
		this.answer = answer;
	}
	      
	public String getT(String choice) {//정답 여부 판별해서 돌려주는 함수 (o를 선택한 경우)
		if (choice.equals("O")) {
			if(getAnswer().equals("O")) {
				tf = "정답입니다.";
			}else {
				tf = "아쉽지만 틀렸습니다.";
			}         
		}     
		return tf; 	      
	}	   
	   
	public String getF(String choice2) {  //정답 여부 판별해서 돌려주는 함수 (x를 선택한 경우)
		if (choice2.equals("X")) {
			if(getAnswer().equals("X")) {
				tf = "정답입니다.";
			}else {
				tf = "아쉽지만 틀렸습니다.";
			}
		}
		return tf;
	}
	   
	public void setQuiz(String quiz) {//입력받은 퀴즈 문제 변경함수
		this.quiz = quiz;
	         
	}
	public String getQuiz() {//입력받은 퀴즈 문제 돌려주는 함수
		return quiz;
	         
	}
	public void setAnswer(String answer) {//입력받은 퀴즈 정답 변경하는 함수
		this.answer = answer;
	}
	public String getAnswer() {//입력받은 퀴즈 정답 돌려주는 함수
		return answer;      
	}	      
}
