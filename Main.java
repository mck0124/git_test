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
        
        //초기 �? ?��?��?�� 추�?? (?��?��?��?��)
       rooms.add(new Room(101, "Single", 2, 50000, false));
       rooms.add(new Room(102, "Single", 2, 50000, false));
       rooms.add(new Room(201, "Double", 4, 80000, false));
       rooms.add(new Room(202, "Double", 4, 80000, false));
       
       
       //?���? ?��?�� �??�� ex) 2025-03-20 14:00
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        while (true) {
        	System.out.println("===== 메뉴 =====");
            System.out.println("1. 고객 ?���? ?��?��");
            System.out.println("2. �? ?��?��");
            System.out.println("3. ?��?�� ?���? 출력");
            System.out.println("4. 체크?��");
            System.out.println("5. 체크?��?��");
            System.out.println("6. ?���? ?��?�� ?��?�� ?��?��");
            System.out.println("7. 종료");
            System.out.print("메뉴�? ?��?��?��?��?��: ");
            String menu = sc.nextLine();
            System.out.println();

            switch (menu) {
                case "1": //고객 ?���? ?��?�� 
                	System.out.println("===== 고객 ?���? ?��?�� =====");
                	Customer customer = new Customer(); // 고객 객체 ?��?��
                	customer.inputInfo();
                	customers.add(customer);
                	System.out.println();
                    break;
                    
                case "2": //�? ?��?�� 
                	//고객 ?��?��번호 ?��?�� (+존재 ?���? ?��?��)
                	System.out.println("===== �? ?��?�� =====");
                    System.out.print("?��?��?�� 고객 ?��?��번호�? ?��?��?��?��?��: ");
                    String phoneNum = sc.nextLine();
                    System.out.println();
                    Customer foundCustomer = customers.stream()
                    		.filter(c -> c.getPhoneNum().equals(phoneNum))
                    		.findFirst().orElse(null);
                    if (foundCustomer == null) {
                    	System.out.println("고객?�� 찾을 ?�� ?��?��?��?��.");
                    	System.out.println();
                    	break;
                    }
                    
                    //?��?�� ?���? ?��?��
                    System.out.println("----- ?��?�� ?���? ?��?�� -----");
                    LocalDateTime startDate = null, endDate = null;
                    boolean validDate = false;
                    while (!validDate) {
                        System.out.println("체크?�� ?��짜�?? ?��?��?��?��?�� (ex. 2025-03-20 14:00): ");
                        String startDateStr = sc.nextLine();
                        System.out.println();
                        System.out.println("체크?��?�� ?��짜�?? ?��?��?��?��?�� (ex. 2025-03-20 14:00): ");
                        String endDateStr = sc.nextLine();
                        System.out.println();
                        try {
                    	    startDate = LocalDateTime.parse(startDateStr, formatter);
                    	    endDate = LocalDateTime.parse(endDateStr, formatter);
                    	    
                    	    // ?��?��?�� ?��?�� ?��짜인�? ?��?�� ?���? (?��?��?�� 방�??)
                    	    if (startDate.isAfter(endDate)) {
                    	    	System.out.println("체크?�� ?��짜는 체크?��?�� ?��짜보?�� ?��?��?��?��?�� ?��?��?��. ?��?�� ?��?��?�� 주세?��.");
                    	    } else if (startDate.isBefore(LocalDateTime.now())) {
                    	    	System.out.println("체크?�� ?��짜는 ?��?��보다 미래?��?�� ?��?��?��. ?��?�� ?��?��?��주세?��.");
                    	    } else validDate = true;
                        } catch (Exception e) {
                        	System.out.println("?���? ?��?��?�� ?��못되?��?��?��?��. (ex. 2025-03-20 14:00)");
                        	System.out.println();
                        }
                    }
                    
                    //?��?�� ?��짜에 ?��?�� �??��?�� �? 목록 (?��?���?)
                    Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
                    if (availableRooms.isEmpty()) {
                 	    System.out.println(startDate.toLocalDate() + "?�� ?��?�� �??��?�� 방이 ?��?��?��?��.");
                 	    System.out.println();
                 	    break;
                    }
                   
                    //�? ????��별로 그룹?�� ?��?�� 출력?���?
                    System.out.println("----- " + startDate.toLocalDate() + "?�� ?��?�� �??��?�� �? -----");
                    availableRooms.forEach((type, count) -> 
                    	System.out.println(type + "�?: " + count + "�? ?��?��"));
                    System.out.println();

                    //�? ????�� ?��?��
                    System.out.print("?��?��?�� �? ????��?�� ?��?��?��?��?�� (single/double): ");
                    String roomType = sc.nextLine();
                    System.out.println();
                    Room selectedRoom = system.getAvailableRooms(startDate, endDate).stream()
                	 	   .filter(r -> r.getType().equalsIgnoreCase(roomType))
                	 	   .findFirst().orElse(null);
                   
                    if (selectedRoom == null) {
                	    System.out.println("?��?��?��?�� 방�?? ?��?��?��?�� ?�� ?��?��?��?��.");
                	    System.out.println();
                	    break;
                    }
                   
                    //?��?�� ?��?��
                    Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer, startDate, endDate);
                    foundCustomer.setReservID(reservation);
                    System.out.println("----- ?��?�� ?���? -----");
                    System.out.println("?��?��?�� ?��료되?��?��?��?��.\n?��?�� ID: " + reservation.getReservID());
                    System.out.println();
                    break;
                    
                case "3": //���� ���� ��� 
                    System.out.println("===== ���� ���� ��� =====");
                    System.out.print("���� ID�� �Է��ϼ���: ");
                    int reservID = Integer.parseInt(sc.nextLine());
                    Reservation foundReservation = reservations.stream()
                        .filter(r -> r.getReservID() == reservID)
                        .findFirst().orElse(null);
                    if (foundReservation != null) {
                        foundReservation.printReserv(reservID);
                    } else {
                        System.out.println("������ ã�� �� �����ϴ�.");
                    }
                    System.out.println();
                    break;
                    
                case "4": //체크?�� 
                	System.out.println("===== 체크?�� =====");
                	System.out.print("체크?��?�� ?��?�� ID�? ?��?��?��?��?��: ");
                	try {
						int checkInId = Integer.parseInt(sc.nextLine());
						system.checkIn(checkInId);
					} catch (NumberFormatException e) {
						System.out.println("?��?��?�� ?��?�� ID(?��?��)�? ?��?��?��?��?��.");
					}
                	System.out.println();
                    break;
                    
                case "5": //체크?��?�� 
                	System.out.println("===== 체크?��?�� =====");
                    System.out.print("체크?��?��?�� ?��?�� ID�? ?��?��?��?��?��: ");
                    try {
						int checkOutId = Integer.parseInt(sc.nextLine());
						system.checkOut(checkOutId);
					} catch (NumberFormatException e) {
						System.out.println("?��?��?�� ?��?�� ID(?��?��)�? ?��?��?��?��?��.");
					}
                    System.out.println();
                    break;
                    
                case "6": //고객 ?���? ?���? 
                	System.out.println("===== ?���? ?��?�� ?��?�� ?��?�� =====");
                    for (Customer c : customers) {
                    	system.saveInfo(c, c.getReservID().getReservID(), c.getMembers());
                    }
                    System.out.println("고객 ?��?��?��?�� 리스?��.txt ?��?��?�� ?��록되?��?��?��?��.");
                    System.out.println();
                    break;
                    
                case "7": //종료 
                    System.out.println("?��로그?��?�� 종료?��?��?��.");
                    return;
                    
                default:
                    System.out.println("?��못된 ?��?��?��?��?��. ?��?�� ?��?��?��?��?��.");
                    System.out.println();
            }
        }
    }
}
