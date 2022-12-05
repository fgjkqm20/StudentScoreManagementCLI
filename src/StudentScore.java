import java.io.Serializable;
import java.util.Objects;

public class StudentScore implements Comparable<StudentScore>, Serializable {
	private static final long serialVersionUID = -1144973333616009726L;
	
	private Student student = new Student();
	private Score score = new Score();
	
	public StudentScore(Student student, Score score) {
		this.student = student;
		this.score = score;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		return Objects.hash(student);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentScore other = (StudentScore) obj;
		return Objects.equals(student, other.student);
	}

	@Override
	public int compareTo(StudentScore o) {
		if(this.getStudent().getGrade() == o.getStudent().getGrade()) {
			if(this.getStudent().getClassNumber() == o.getStudent().getClassNumber()) {
				if(this.getStudent().getStudentNumber() == o.getStudent().getStudentNumber()) {
					return 0;
				} else {
					return this.getStudent().getStudentNumber() - o.getStudent().getStudentNumber();
				}
			} else {
				return this.getStudent().getClassNumber() - o.getStudent().getClassNumber();
			}
		} else {
			return this.getStudent().getGrade() - o.getStudent().getGrade();
		}
	}
}
