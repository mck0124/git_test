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
                	System.out.println("===== 고객 정보 입력 =====");
                	Customer customer = new Customer(); // 고객 객체 생성
                	customer.inputInfo();
                	customers.add(customer);
                	System.out.println();
                    break;
                    
                case "2": //방 예약 
                	//고객 전화번호 입력 (+존재 여부 확인)
                	System.out.println("===== 방 예약 =====");
                    System.out.print("예약할 고객 전화번호를 입력하세요: ");
                    String phoneNum = sc.nextLine();
                    System.out.println();
                    Customer foundCustomer = customers.stream()
                    		.filter(c -> c.getPhoneNum().equals(phoneNum))
                    		.findFirst().orElse(null);
                    if (foundCustomer == null) {
                    	System.out.println("고객을 찾을 수 없습니다.");
                    	System.out.println();
                    	break;
                    }
                    
                    //예약 날짜 입력
                    System.out.println("----- 예약 날짜 입력 -----");
                    System.out.println("체크인 날짜를 입력하세요 (ex. 2025-03-20 14:00): ");
                    String startDateStr = sc.nextLine();
                    System.out.println();
                    System.out.println("체크아웃 날짜를 입력하세요 (ex. 2025-03-20 14:00): ");
                    String endDateStr = sc.nextLine();
                    System.out.println();
                    LocalDateTime startDate, endDate;
                    try {
                	    startDate = LocalDateTime.parse(startDateStr, formatter);
                	    endDate = LocalDateTime.parse(endDateStr, formatter);
                	    
                	    // 유효한 예약 날짜인지 확인 절차 (오입력 방지)
                	    if (startDate.isAfter(endDate)||startDate.isBefore(LocalDateTime.now())) {
                	 	   System.out.println("유효하지 않은 날짜입니다. (체크인, 체크아웃 날짜를 다시 확인 해주십시오)");
                	  	   System.out.println();
                	 	   break;
                	    }
                    } catch (Exception e) {
                    	System.out.println("날짜 형식이 잘못되었습니다. (ex. 2025-03-20 14:00)");
                    	System.out.println();
                    	break;
                    }
                    
                    //해당 날짜에 예약 가능한 방 목록 (필터링)
                    Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
                    if (availableRooms.isEmpty()) {
                 	    System.out.println(startDate.toLocalDate() + "에 예약 가능한 방이 없습니다.");
                 	    System.out.println();
                 	    break;
                    }
                   
                    //방 타입별로 그룹화 해서 출력하기
                    System.out.println("----- " + startDate.toLocalDate() + "에 예약 가능한 방 -----");
                    availableRooms.forEach((type, count) -> 
                    	System.out.println(type + "룸: " + count + "개 남음"));
                    System.out.println();

                    //방 타입 선택
                    System.out.print("예약할 방 타입을 입력하세요 (single/double): ");
                    String roomType = sc.nextLine();
                    System.out.println();
                    Room selectedRoom = system.getAvailableRooms(startDate, endDate).stream()
                	 	   .filter(r -> r.getType().equalsIgnoreCase(roomType))
                	 	   .findFirst().orElse(null);
                   
                    if (selectedRoom == null) {
                	    System.out.println("선택하신 방은 예약하실 수 없습니다.");
                	    System.out.println();
                	    break;
                    }
                   
                    //예약 생성
                    Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer, startDate, endDate);
                    foundCustomer.setReservID(reservation);
                    System.out.println("----- 예약 완료 -----");
                    System.out.println("예약이 완료되었습니다.\n예약 ID: " + reservation.getReservID());
                    System.out.println();
                    break;
                    
                case "3": //예약 정보 출력 
                	System.out.println("===== 예약 정보 출력 =====");
                	reservations.forEach(Reservation::printReserv);
                	System.out.println();
                    break;
                    
                case "4": //체크인 
                	System.out.println("===== 체크인 =====");
                	System.out.print("체크인할 예약 ID를 입력하세요: ");
                	try {
						int checkInId = Integer.parseInt(sc.nextLine());
						system.checkIn(checkInId);
					} catch (NumberFormatException e) {
						System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
					}
                	System.out.println();
                    break;
                    
                case "5": //체크아웃 
                	System.out.println("===== 체크아웃 =====");
                    System.out.print("체크아웃할 예약 ID를 입력하세요: ");
                    try {
						int checkOutId = Integer.parseInt(sc.nextLine());
						system.checkOut(checkOutId);
					} catch (NumberFormatException e) {
						System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
					}
                    System.out.println();
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
