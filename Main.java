import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("메뉴를 선택하세요:");
            System.out.println("1. 고객 정보 입력");
            System.out.println("2. 방 예약");
            System.out.println("3. 예약 정보 출력");
            System.out.println("4. 체크인");
            System.out.println("5. 체크아웃");
            System.out.println("6. 고객 정보 저장");
            System.out.println("7. 종료");

            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    
                    break;
                case "2":
                    
                    break;
                case "3":
                   
                    break;
                case "4":
                  
                    break;
                case "5":
                    
                    break;
                case "6":
                    
                    break;
                case "7":
                    System.out.println("프로그램을 종료합니다.");
                    System.out.println();
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
            }
        }
    }
}