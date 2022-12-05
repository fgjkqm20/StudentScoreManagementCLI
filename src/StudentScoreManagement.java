import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.ListIterator;
import java.util.Scanner;

public class StudentScoreManagement {
	private Scanner scanner;
	private ArrayList<StudentScore> ssl;
	
	public StudentScoreManagement() {
		scanner = new Scanner(System.in);
		ssl = new ArrayList<>();
		
		try {
			FileInputStream fis = new FileInputStream("학생 점수.save");
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object readObject = ois.readObject();
			
			if(readObject instanceof ArrayList<?>) {
				for(Object obj : (ArrayList<?>)readObject) {
					if(obj instanceof StudentScore) {
						ssl.add((StudentScore)obj);
					}
				}
			}
			
			ois.close();
		} catch (IOException | ClassNotFoundException e) {}
	}
	
	public void showMenu() {
		int select;
		
		while(true) {
			System.out.println("1. 학생 추가");
			System.out.println("2. 학생 점수 입력");
			System.out.println("3. 학생 삭제");
			System.out.println("4. 검색");
			System.out.println("5. 목록 보기");
			System.out.println("6. 종료");
			System.out.print("선택 : ");
			
			try {
				select = scanner.nextInt();
			} catch(InputMismatchException e) {
				System.out.println();
				System.out.println("숫자를 입력해 주세요.");
				System.out.println();
				continue;
			}
			
			System.out.println();
			switch(select) {
			case 1: 
				addStudent(inputStudent()); 
				break;
			case 2:
				inputStudentScore(); 
				break;
			case 3: 
				removeStudent();
				break;
			case 4: 
				search(); 
				break;
			case 5: 
				showList(); 
				break;
			case 6: 
				break;
			default: 
				System.out.println("1~5의 숫자를 입력해주세요.");
				break;
			}
			
			if(select == 6) break;
		}
	}
	
	private Student inputStudent() {
		Student student = new Student();
		
		System.out.println("학생 정보를 입력하세요.");
		student.setName(nameCheck());
		student.setGrade(numberCheck("학년", 1, 6));
		student.setClassNumber(numberCheck("반", 1, 10));
		student.setStudentNumber(numberCheck("번호", 1, 30));
		System.out.println();
		
		return student;
	}
	
	private void addStudent(Student student) {
		boolean add = true;
		
		for(StudentScore ss : ssl) {
			if(ss.getStudent().getGrade() == student.getGrade() &&
				ss.getStudent().getClassNumber() == student.getClassNumber() &&
				ss.getStudent().getStudentNumber() == student.getStudentNumber()) {
				
				System.out.println("이미 등록된 학생입니다.");
				System.out.println();
				add = false;
				break;
			}
		}
		
		if(add) {
			ssl.add(new StudentScore(student, new Score()));
			System.out.println("학생이 추가되었습니다.");
			System.out.println();
			Collections.sort(ssl);
			fileSave();
		}
	}
	
	private void inputStudentScore() {
		Student student;
		boolean isStudent = false;

		student = inputStudent();
		
		for(StudentScore ss : ssl) {
			if(ss.getStudent().equals(student)) {
				isStudent = true;
				ss.getScore().setKorean(numberCheck("국어", 0, 100));
				ss.getScore().setMath(numberCheck("수학", 0, 100));
				ss.getScore().setSocial(numberCheck("사회", 0, 100));
				ss.getScore().setScience(numberCheck("과학", 0, 100));
				System.out.println();
				fileSave();
				
				System.out.println("학생 점수가 입력되었습니다.");
				System.out.println();
				break;
			}
		}
		
		if(!isStudent) System.out.println("존재하지 않는 학생입니다.");
	}
	
	private void removeStudent() {
		Student student;
		boolean isStudent = false;

		student = inputStudent();
		
		for(ListIterator<StudentScore> ss = ssl.listIterator(); ss.hasNext();) {
			if(ss.next().getStudent().equals(student)) {
				isStudent = true;
				ss.remove();
				fileSave();

				System.out.println("삭제되었습니다.");
				System.out.println();
			}
		}
		
		if(!isStudent) System.out.println("존재하지 않는 학생입니다.");
	}
	
	private void search() {
		String name;
		
		System.out.println("검색을 종료하려면 \"종료\"라고 입력해주세요. ");
		
		while(true) {
			name = nameCheck();
			System.out.println();
			if(name.equals("종료")) break;
	
			for(StudentScore ss : ssl) {
				if(ss.getStudent().getName().equals(name)) {
					showStudentScore(ss);
				}
			}
		}
	}
	
	private void showList() {
		int select;
		int grade;
		int classNumber;
		boolean isStudent = false;
		
		while(true) {
			System.out.println("1. 전체 목록 보기");
			System.out.println("2. 특정 학년 목록 보기");
			System.out.println("3. 특정 반 목록 보기");
			System.out.println("4. 메뉴로 돌아가기");
			System.out.print("선택 : ");
			
			try {
				select = scanner.nextInt();
			} catch(InputMismatchException e) {
				System.out.println();
				System.out.println("숫자를 입력해 주세요.");
				System.out.println();
				continue;
			}
			
			System.out.println();
			if(select == 1) {
				if(ssl.isEmpty()) {
					System.out.println("학생이 없습니다.");
					System.out.println();
				} else for(StudentScore ss : ssl) showStudentScore(ss);
			}
			else if(select == 2) {
				grade = numberCheck("학년", 1, 6);
				System.out.println();
				for(StudentScore ss : ssl) {
					if(ss.getStudent().getGrade() == grade) {
						isStudent = true;
						showStudentScore(ss);
					}
				}
				if(!isStudent) {
					System.out.println("학생이 없습니다.");
					System.out.println();
				}
			}
			else if(select == 3) {
				grade = numberCheck("학년", 1, 6);
				classNumber = numberCheck("반", 1, 10);
				System.out.println();
				for(StudentScore ss : ssl) {
					if(ss.getStudent().getGrade() == grade && 
						ss.getStudent().getClassNumber() == classNumber) {
						isStudent = true;
						showStudentScore(ss);
					}
				}
				if(!isStudent) {
					System.out.println("학생이 없습니다.");
					System.out.println();
				}
			}
			else if(select == 4) break;
			else System.out.println("1~5의 숫자를 입력해주세요.");
		}
	}
	
	private String nameCheck() {
		String name;
		
		while(true) {
			boolean loop = false;
			
			System.out.print("이름 : ");
			name = scanner.nextLine();
			
			for(int i=0; i<name.length(); i++) {
				if(name.charAt(i) < 0xAC00 || name.charAt(i) > 0xD7AF) {
					System.out.println("한글만 입력해주세요.");
					System.out.println();
					loop = true;
					break;
				}
			}
			
			if(!loop) break;
		}
		
		return name;
	}
	
	private int numberCheck(String info, int start, int end) {
		int number;
		
		while(true) {
			System.out.print(info + " : ");
			
			try {
				number = scanner.nextInt();
			} catch(InputMismatchException e) {
				System.out.println("숫자를 입력해 주세요.");
				System.out.println();
				continue;
			}
			
			if(number < start || number > end) {
				System.out.println(start + " 이상, " + end + " 이하로 입력해주세요.");
				System.out.println();
				continue;
			}
			
			break;
		}
		
		return number;
	}
	
	private void showStudentScore(StudentScore ss) {
		System.out.print(ss.getStudent().getName());
		System.out.print(" " + ss.getStudent().getGrade() + "학년");
		System.out.print(" " + ss.getStudent().getClassNumber() + "반");
		System.out.print(" " + ss.getStudent().getStudentNumber() + "번");
		System.out.println(); 

		System.out.print("국어: " + ss.getScore().getKorean() + "점 ");
		System.out.print("수학: " + ss.getScore().getMath() + "점 ");
		System.out.print("사회: " + ss.getScore().getSocial() + "점 ");
		System.out.print("과학: " + ss.getScore().getScience() + "점");
		System.out.println();
		System.out.println();
	}
	
	private void fileSave() {
		try {
			FileOutputStream fos = new FileOutputStream("학생 점수.save");
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			
			oos.writeObject(ssl);
			oos.flush();
			oos.close();
		} catch (IOException e) {
			System.out.println("save 파일 생성에 문제가 생겼습니다.");
			System.out.print("엔터를 누르면 종료됩니다. ");
			scanner.nextLine();
			System.exit(-1);
		}
	}
}
