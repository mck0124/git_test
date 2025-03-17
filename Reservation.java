import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Reservation {
    // �ʵ�
// ��, ���� ID, ��, üũ�� ��¥, üũ�ƿ� ��¥
    private Customer customer;
    private int reservID;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private int members;
    private int visitCount;
    private static int nextReservID = 1;
    private boolean isCheckedIn;
    private boolean isCheckedOut;

    public Reservation() {
    }

    public Reservation(Customer customer, Room room, int members, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.room = room;
        this.members = members;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservID = nextReservID++;
    }

    public void addReservation(Customer customer, Room room, int members, LocalDate startDate, LocalDate endDate) {
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
        LocalDate startDate = null, endDate = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println("üũ�� ��¥�� �Է��ϼ��� (ex. 2025-03-20): ");
            String startDateStr = sc.nextLine();
            System.out.println();
            System.out.println("üũ�ƿ� ��¥�� �Է��ϼ��� (ex. 2025-03-20): ");
            String endDateStr = sc.nextLine();
            System.out.println();
            try {
                startDate = LocalDate.parse(startDateStr, formatter);
                endDate = LocalDate.parse(endDateStr, formatter);

                // ��ȿ�� ���� ��¥���� Ȯ�� ���� (���Է� ����)
                if (startDate.isAfter(endDate)) {
                    System.out.println("üũ�� ��¥�� üũ�ƿ� ��¥���� �����̾�� �մϴ�. �ٽ� �Է��� �ּ���.");
                } else if (startDate.isBefore(LocalDate.now())) {
                    System.out.println("üũ�� ��¥�� ���纸�� �̷����� �մϴ�. �ٽ� �Է����ּ���.");
                } else validDate = true;
            } catch (Exception e) {
                System.out.println("��¥ ������ �߸��Ǿ����ϴ�. (ex. 2025-03-20)");
                System.out.println();
            }
        }

        // �ش� ��¥�� ���� ������ �� ��� (���͸�)
        Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
        if (availableRooms.isEmpty()) {
            System.out.println(startDate + "�� ���� ������ ���� �����ϴ�.");
            System.out.println();
            return;
        }

        // �� Ÿ�Ժ��� �׷�ȭ �ؼ� ����ϱ�
        System.out.println("----- " + startDate + "�� ���� ������ �� -----");
        availableRooms.forEach((type, count) ->
                System.out.println(type + "��: " + count + "�� ����"));
        System.out.println();

        // �� Ÿ�� ����
        System.out.print("������ �� Ÿ���� �Է��ϼ��� (single/deluxe/double): ");
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

        // ���� �ʰ� üũ �� �߰���� ���
        int extraCharge = 0;
        int days = (int)java.time.Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();
        if (foundCustomer.getMembers() > selectedRoom.getFullMember()) {
            int extraPeople = foundCustomer.getMembers() - selectedRoom.getFullMember();
            extraCharge = extraPeople * 10000 * days;
            System.out.println("������ " + extraPeople + "�� �ʰ��� ���� �δ� 10,000����  " + extraCharge + "���� �߰������ �ΰ��˴ϴ�.");
        }
        System.out.println();

        // ���� ����
        Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer.getMembers(), startDate, endDate);
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
            int days = (int)java.time.Duration.between(foundReservation.getStartDate().atStartOfDay(), foundReservation.getEndDate().atStartOfDay()).toDays();
            int basePrice = foundReservation.getRoom().getPrice() * days;
            int extraCharge = 0;
            if (foundReservation.getMembers() > foundReservation.getRoom().getFullMember()) {
                extraCharge = (foundReservation.getMembers() - foundReservation.getRoom().getFullMember()) * 10000 * days;
            }
            System.out.println("�⺻ ����: " + basePrice + "��");
            if (extraCharge > 0) {
                System.out.println("�߰� ���: " + extraCharge + "��");
                System.out.println("�� ����: " + (basePrice + extraCharge) + "��");
            }
        } else {
            System.out.println("�ش� ���� ID�� ã�� �� �����ϴ�.");
}
        } else {
            System.out.println("������ ã�� �� �����ϴ�.");
        }
        System.out.println();
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }
}
