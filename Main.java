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
        
        //�ʱ� �� ������ �߰� (�׽�Ʈ��)
       rooms.add(new Room(101, "Single", 2, 50000, false));
       rooms.add(new Room(102, "Single", 2, 50000, false));
       rooms.add(new Room(201, "Double", 4, 80000, false));
       rooms.add(new Room(202, "Double", 4, 80000, false));
       
       
       //��¥ ���� ���� ex) 2025-03-20 14:00
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        while (true) {
            System.out.println("1. �� ���� �Է�");
            System.out.println("2. �� ����");
            System.out.println("3. ���� ���� ���");
            System.out.println("4. üũ��");
            System.out.println("5. üũ�ƿ�");
            System.out.println("6. �� ���� ���");
            System.out.println("7. ����");
            System.out.print("�޴��� �����ϼ���: ");

            String menu = sc.nextLine();

            switch (menu) {
                case "1": //�� ���� �Է� 
                	Customer customer = new Customer(); // �� ��ü ����
                	customer.inputInfo();
                	customers.add(customer);
                    break;
                    
                case "2": //�� ���� 
                	//�� ��ȭ��ȣ �Է� (+���� ���� Ȯ��)
                    System.out.print("������ �� ��ȭ��ȣ�� �Է��ϼ���: ");
                    String phoneNum = sc.nextLine();
                    Customer foundCustomer = customers.stream()
                    		.filter(c -> c.getPhoneNum().equals(phoneNum))
                    		.findFirst().orElse(null);
                    if (foundCustomer == null) {
                    	System.out.println("���� ã�� �� �����ϴ�.");
                    	break;
                    }
                    
                    //���� ��¥ �Է�
                    System.out.println("üũ�� ��¥�� �Է��ϼ��� (ex. 2025-03-20 14:00): ");
                    String startDateStr = sc.nextLine();
                    System.out.println("üũ�ƿ� ��¥�� �Է��ϼ��� (ex. 2025-03-20 14:00): ");
                    String endDateStr = sc.nextLine();
                   
                    LocalDateTime startDate, endDate;
                    try {
                	    startDate = LocalDateTime.parse(startDateStr, formatter);
                	    endDate = LocalDateTime.parse(endDateStr, formatter);
                	    
                	    // ��ȿ�� ���� ��¥���� Ȯ�� ���� (���Է� ����)
                	    if (startDate.isAfter(endDate)||startDate.isBefore(LocalDateTime.now())) {
                	 	   System.out.println("��ȿ���� ���� ��¥�Դϴ�. (üũ��, üũ�ƿ� ��¥�� �ٽ� Ȯ�� ���ֽʽÿ�)");
                	  	   break;
                	    }
                    } catch (Exception e) {
                    	System.out.println("��¥ ������ �߸��Ǿ����ϴ�. (ex. 2025-03-20 14:00)");
                    	break;
                    }
                    
                    //�ش� ��¥�� ���� ������ �� ��� (���͸�)
                    Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
                    if (availableRooms.isEmpty()) {
                 	    System.out.println(startDate.toLocalDate() + "�� ���� ������ ���� �����ϴ�.");
                 	    break;
                    }
                   
                    //�� Ÿ�Ժ��� �׷�ȭ �ؼ� ����ϱ�
                    System.out.println(startDate.toLocalDate() + "�� ���� ������ ��: ");
                    availableRooms.forEach((type, count) -> 
                    	System.out.println(type + "�� " + count + "�� ����"));
                  
                    //�� Ÿ�� ����
                    System.out.println("������ �� Ÿ���� �Է��ϼ��� (single/double)");
                    String roomType = sc.nextLine();
                    Room selectedRoom = system.getAvailableRooms(startDate, endDate).stream()
                	 	   .filter(r -> r.getType().equalsIgnoreCase(roomType))
                	 	   .findFirst().orElse(null);
                   
                    if (selectedRoom == null) {
                	    System.out.println("�����Ͻ� ���� �����Ͻ� �� �����ϴ�.");
                	    break;
                    }
                   
                    //���� ����
                    Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer.getMembers(), startDate, endDate);
                    foundCustomer.setReservID(reservation);
                    System.out.println("������ �Ϸ�Ǿ����ϴ�.\n���� ID: " + reservation.getReservID());
                    break;
                    
                case "3": //���� ���� ��� 
                	reservations.forEach(Reservation::printReserv);
                    break;
                    
                case "4": //üũ�� 
                	System.out.print("üũ���� ���� ID�� �Է��ϼ���: ");
                	try {
						int checkInId = Integer.parseInt(sc.nextLine());
						system.checkIn(checkInId);
					} catch (NumberFormatException e) {
						System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
					}
                    break;
                    
                case "5": //üũ�ƿ� 
                    System.out.println("üũ�ƿ��� ���� ID�� �Է��ϼ���: ");
                    try {
						int checkOutId = Integer.parseInt(sc.nextLine());
						system.checkOut(checkOutId);
					} catch (NumberFormatException e) {
						System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
					}
                    break;
                    
                case "6": //�� ���� ��� 
                    customers.forEach(Customer::saveInfo);
                    break;
                    
                case "7": //���� 
                    System.out.println("���α׷��� �����մϴ�.");
                    return;
                    
                default:
                    System.out.println("�߸��� �����Դϴ�. �ٽ� �õ��ϼ���.");
            }
        }
    }
}
