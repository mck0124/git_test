import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Customer> customers = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();
        ReservationSystem system = new ReservationSystem(reservations, rooms);
        
        //초기 방 데이터 추가 (테스트용)
       rooms.add(new Room(101, "Single", 2, 50000, false));
       rooms.add(new Room(102, "Single", 2, 50000, false));
       rooms.add(new Room(201, "Double", 4, 80000, false));
       rooms.add(new Room(202, "Double", 4, 80000, false));
       
       
       //날짜 형식 지정 ex) 2025-03-20 14:00
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        while (true) {
            System.out.println("===== 메뉴 =====");
            System.out.println("1. 고객 정보 입력");
            System.out.println("2. 방 예약");
            System.out.println("3. 예약 정보 출력");
            System.out.println("4. 체크인");
            System.out.println("5. 체크아웃");
            System.out.println("6. 전체 예약 파일 생성");
            System.out.println("7. 종료");
            System.out.print("메뉴를 선택하세요: ");
            String menu = sc.nextLine();
            System.out.println();

            switch (menu) {
                case "1": //고객 정보 입력 
                    Customer customer = new Customer(); // 고객 객체 생성
                    customer.inputInfo();
                    customers.add(customer);
                    System.out.println();
                    break;
                    
                case "2": //방 예약 
                    Reservation.createReservation(sc, customers, system, formatter);
                    break;
                    
                case "3": //예약 정보 출력 
                    Reservation reservation = new Reservation();
                    reservation.printReserv(sc, reservations);
                    System.out.println();
                    break;
                    
                case "4": //체크인 
                    system.checkIn(sc);
                    break;
                    
                case "5": //체크아웃 
                    system.checkOut(sc);
                    break;
                    
                case "6": //고객 정보 등록 
                    System.out.println("===== 전체 예약 파일 생성 =====");
                    for (Customer c : customers) {
                        system.saveInfo(c, c.getReservID().getReservID(), c.getMembers());
                    }
                    System.out.println("고객 예약내역 리스트.txt 파일에 등록되었습니다.");
                    System.out.println();
                    break;
                    
                case "7": //종료 
                    System.out.println("프로그램을 종료합니다.");
                    return;
                    
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                    System.out.println();
            }
        }
    }
}