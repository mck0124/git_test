import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.lang.System;

public class Reservation {
    // �ʵ�
    // ��, ���� ID, ��, üũ�� ��¥, üũ�ƿ� ��¥
    private Customer customer;
    private int reservID;
    private Room room;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Customer members;
    private int visitCount;
    private static int nextReservID = 1;

    public Reservation() {
    }

    public Reservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.members = members;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservID = nextReservID++;
    }

    public void addReservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room.setFull(true); // ���� ����Ǿ����� ǥ��
        this.members = members;
        this.visitCount++;
        this.reservID = nextReservID++; // ���� ID �ڵ� ����
    }

    public static void createReservation(Scanner sc, List<Customer> customers, ReservationSystem system, DateTimeFormatter formatter) {
        // �� ��ȭ��ȣ �Է� (+���� ���� Ȯ��)
        System.out.println("===== �� ���� =====");
        System.out.print("������ �� ��ȭ��ȣ�� �Է��ϼ���: ");
        String phoneNum = sc.nextLine();
        System.out.println();
        Customer foundCustomer = customers.stream()
                .filter(c -> c.getPhoneNum().equals(phoneNum))
                .findFirst().orElse(null);
        if (foundCustomer == null) {
            System.out.println("���� ã�� �� �����ϴ�.");
            System.out.println();
            return;
        }

        // ���� ��¥ �Է�
        System.out.println("----- ���� ��¥ �Է� -----");
        LocalDateTime startDate = null, endDate = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println("üũ�� ��¥�� �Է��ϼ��� (ex. 2025-03-20 14:00): ");
            String startDateStr = sc.nextLine();
            System.out.println();
            System.out.println("üũ�ƿ� ��¥�� �Է��ϼ��� (ex. 2025-03-20 14:00): ");
            String endDateStr = sc.nextLine();
            System.out.println();
            try {
                startDate = LocalDateTime.parse(startDateStr, formatter);
                endDate = LocalDateTime.parse(endDateStr, formatter);

                // ��ȿ�� ���� ��¥���� Ȯ�� ���� (���Է� ����)
                if (startDate.isAfter(endDate)) {
                    System.out.println("üũ�� ��¥�� üũ�ƿ� ��¥���� �����̾�� �մϴ�. �ٽ� �Է��� �ּ���.");
                } else if (startDate.isBefore(LocalDateTime.now())) {
                    System.out.println("üũ�� ��¥�� ���纸�� �̷����� �մϴ�. �ٽ� �Է����ּ���.");
                } else validDate = true;
            } catch (Exception e) {
                System.out.println("��¥ ������ �߸��Ǿ����ϴ�. (ex. 2025-03-20 14:00)");
                System.out.println();
            }
        }

        // �ش� ��¥�� ���� ������ �� ��� (���͸�)
        Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
        if (availableRooms.isEmpty()) {
            System.out.println(startDate.toLocalDate() + "�� ���� ������ ���� �����ϴ�.");
            System.out.println();
            return;
        }

        // �� Ÿ�Ժ��� �׷�ȭ �ؼ� ����ϱ�
        System.out.println("----- " + startDate.toLocalDate() + "�� ���� ������ �� -----");
        availableRooms.forEach((type, count) ->
                System.out.println(type + "��: " + count + "�� ����"));
        System.out.println();

        // �� Ÿ�� ����
        System.out.print("������ �� Ÿ���� �Է��ϼ��� (single/double): ");
        String roomType = sc.nextLine();
        System.out.println();
        Room selectedRoom = system.getAvailableRooms(startDate, endDate).stream()
                .filter(r -> r.getType().equalsIgnoreCase(roomType))
                .findFirst().orElse(null);

        if (selectedRoom == null) {
            System.out.println("�����Ͻ� ���� �����Ͻ� �� �����ϴ�.");
            System.out.println();
            return;
        }

        // ���� ����
        Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer, startDate, endDate);
        foundCustomer.setReservID(reservation);
        System.out.println("----- ���� �Ϸ� -----");
        System.out.println("������ �Ϸ�Ǿ����ϴ�.\n���� ID: " + reservation.getReservID());
        System.out.println();
    }

    public void printReserv(Scanner sc, List<Reservation> reservations) {
        System.out.println("===== ���� ���� ��� =====");
        System.out.print("���� ID�� �Է��ϼ���: ");
        int reservID = Integer.parseInt(sc.nextLine());
        Reservation foundReservation = reservations.stream()
            .filter(r -> r.getReservID() == reservID)
            .findFirst().orElse(null);
        if (foundReservation != null) {
            if (foundReservation.getReservID() == reservID) {
                System.out.println("----- ���� ���� -----");
                System.out.println("���� ���� ���:");
                System.out.println("�� �̸�: " + foundReservation.getCustomer().getName());
                System.out.println("���� ID: " + foundReservation.getReservID());
                System.out.println("���ȣ: " + foundReservation.getRoom().getRoomID());
                System.out.println("üũ�� ��¥: " + foundReservation.getStartDate());
                System.out.println("üũ�ƿ� ��¥: " + foundReservation.getEndDate());
                int days = (int)java.time.Duration.between(foundReservation.getStartDate(), foundReservation.getEndDate()).toDays() + 1;
                System.out.println("����: " + foundReservation.getRoom().getPrice() * days + "��");
            } else {
                System.out.println("�ش� ���� ID�� ã�� �� �����ϴ�.");
            }
        } else {
            System.out.println("������ ã�� �� �����ϴ�.");
        }
        System.out.println();
    }

    public void deleteReserv() {
        this.customer = null;
        this.room = null;
        this.startDate = null;
        this.endDate = null;
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getReservID() {
        return reservID;
    }

    public void setReservID(int reservID) {
        this.reservID = reservID;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Customer getMembers() {
        return members;
    }

    public Customer setMembers(Customer members) {
        this.members = members;
        return this.members;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}
