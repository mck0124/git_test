import java.io.FileWriter;
import java.util.Scanner;
import java.lang.System;

public class Customer {
	// fields
    private String name;
    private String phoneNum;
    private Reservation reservID;
    private int members;
    private int visitCount = 0;
    
    FileWriter writer = null;

	// default 생성자
	public Customer() {}
    
	// 생성자
    public Customer(String name, String phoneNum) {
        super();
        this.name = name;
        this.phoneNum = phoneNum;
    }
    
	// 고객 정보 저장
    public void saveInfo() {
        try {
            String fileName;
            visitCount++;
            
            if (visitCount == 1) {
                fileName = "신규고객 리스트.txt";
            } else if (visitCount == 2) {
                fileName = "재방문고객 리스트.txt";
            } else {
                fileName = "단골고객 리스트.txt";
            }
            
            writer = new FileWriter(fileName, true);
            String reservStr = (reservID != null) ? reservID.getReservID() + "" : "없음";
            writer.write("이름: " + name + ", 전화번호: " + phoneNum + ", 예약번호: " + reservID.toString() + ", 예약 인원: " + members + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e2) {
            }
        }
    }
    
	// 고객 정보 입력
    public void inputInfo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("이름: ");
        name = sc.nextLine();
        System.out.println("전화번호 (ex. 010-1234-5678): ");
        while(true) {
        	phoneNum = sc.nextLine();
        	if (phoneNum.matches("\\d{3}-\\d{4}-\\d{4}")) break; //전화번호 형식이 올바른지 확인
        	else {
        		System.out.println("전화번호 형식이 잘못되었습니다.\n다시 입력해 주십시오");
        	}
        }
        
        System.out.println("예약 인원: ");
        try {
        	members = Integer.parseInt(sc.nextLine());
        	if (members <= 0) throw new NumberFormatException(); //인원 숫자가 양수임을 확인
		} catch (Exception e) {
			System.out.println("유효하지 않은 인원입니다. 1명으로 설정됩니다.");
			members = 1;
		}
        
    }
    
	// 고객 정보 출력
    public void printInfo() {
        System.out.println("이름: " + name);
        System.out.println("전화번호: " + phoneNum);
        System.out.println("예약번호: " + reservID);
        System.out.println("예약 인원: " + members);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Reservation getReservID() {
        return reservID;
    }

    public void setReservID(Reservation reservID) {
        this.reservID = reservID;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}
