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
            System.out.println("===== �޴� =====");
            System.out.println("1. �� ���� �Է�");
            System.out.println("2. �� ����");
            System.out.println("3. ���� ���� ���");
            System.out.println("4. üũ��");
            System.out.println("5. üũ�ƿ�");
            System.out.println("6. ��ü ���� ���� ����");
            System.out.println("7. ����");
            System.out.print("�޴��� �����ϼ���: ");
            String menu = sc.nextLine();
            System.out.println();

            switch (menu) {
                case "1": //�� ���� �Է� 
                    System.out.println("===== �� ���� �Է� =====");
                    Customer customer = new Customer(); // �� ��ü ����
                    customer.inputInfo();
                    customers.add(customer);
                    System.out.println();
                    break;
                    
                case "2": //�� ���� 
                    Reservation.createReservation(sc, customers, system, formatter);
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
                    
                case "4": //üũ�� 
                    System.out.println("===== üũ�� =====");
                    System.out.print("üũ���� ���� ID�� �Է��ϼ���: ");
                    try {
                        int checkInId = Integer.parseInt(sc.nextLine());
                        system.checkIn(checkInId);
                    } catch (NumberFormatException e) {
                        System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
                    }
                    System.out.println();
                    break;
                    
                case "5": //üũ�ƿ� 
                    System.out.println("===== üũ�ƿ� =====");
                    System.out.print("üũ�ƿ��� ���� ID�� �Է��ϼ���: ");
                    try {
                        int checkOutId = Integer.parseInt(sc.nextLine());
                        system.checkOut(checkOutId);
                    } catch (NumberFormatException e) {
                        System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
                    }
                    System.out.println();
                    break;
                    
                case "6": //�� ���� ��� 
                    System.out.println("===== ��ü ���� ���� ���� =====");
                    for (Customer c : customers) {
                        system.saveInfo(c, c.getReservID().getReservID(), c.getMembers());
                    }
                    System.out.println("�� ���೻�� ����Ʈ.txt ���Ͽ� ��ϵǾ����ϴ�.");
                    System.out.println();
                    break;
                    
                case "7": //���� 
                    System.out.println("���α׷��� �����մϴ�.");
                    return;
                    
                default:
                    System.out.println("�߸��� �����Դϴ�. �ٽ� �õ��ϼ���.");
                    System.out.println();
            }
        }
    }
}