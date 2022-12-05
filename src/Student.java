import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = -2350391819865137487L;
	
	private String name;
	private int grade;
	private int classNumber;
	private int studentNumber;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}
	public int getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		Student student;
		
		if(obj instanceof Student) {
			student = (Student)obj;
			
			if(this.name.equals(student.name) &&
				this.grade == student.grade &&
				this.classNumber == student.classNumber &&
				this.studentNumber == student.studentNumber) return true; 
		}
		
		return false;
	}
}
