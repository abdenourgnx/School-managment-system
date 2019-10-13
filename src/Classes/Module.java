package Classes ;

public class Module {

	private float test;
	private float exam ;

	public Module(float test , float exam){
		this.test = test ;
		this.exam = exam ;

	}

	public void setTest(float test){
		this.test = test;
	}
	public void setExam(float exam){
		this.exam = exam;
	}

	public float getTest(){
		return test;
	}

	public float getExam(){
		return exam;
	}

	public void reset(){
		exam= 0;
		test = 0;

	}

}