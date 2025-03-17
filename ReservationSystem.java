import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.System;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationSystem {
    private List<Reservation> reservations;
    private List<Room> rooms;
    OutputStreamWriter writer = null;

    public ReservationSystem(List<Reservation> reservations, List<Room> rooms) {
        this.reservations = reservations;
        this.rooms = rooms;
    }

    // ���� list[]�� �߰�
    public Reservation addReservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        Reservation reservation = new Reservation(customer, room, members, startDate, endDate);
        reservations.add(reservation);
        return reservation;
    }

    public void deleteReservation(Scanner sc) {
        System.out.println("===== ���� ���� =====");
        System.out.print("������ ���� ID�� �Է��ϼ���: ");
        int reservID = Integer.parseInt(sc.nextLine());
        Reservation foundReservation = reservations.stream()
            .filter(r -> r.getReservID() == reservID)
            .findFirst().orElse(null);
        if (foundReservation != null) {
            reservations.remove(foundReservation);
            foundReservation.getRoom().setFull(false); // ���� ����������� ǥ��
            System.out.println("������ �����Ǿ����ϴ�.");
        } else {
            System.out.println("�ش� ���� ID�� ã�� �� �����ϴ�.");
        }
        System.out.println();
    }

    // ���డ���� �� �� ���
    public Map<String, Long> getAvailableRoomCounts(LocalDateTime startDate, LocalDateTime endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.groupingBy(Room::getType, Collectors.counting()));
    }

    // ���డ���� �� ���
    public List<Room> getAvailableRooms(LocalDateTime startDate, LocalDateTime endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.toList());
    }

    // �� ���� ����
    public void saveInfo(Customer customer, int reservID, Room room, LocalDateTime startDate, LocalDateTime endDate) { 
    try { 
        String fileName = "�� ���೻�� ����Ʈ.txt"; 
        writer = new OutputStreamWriter(new FileOutputStream(fileName, true), "EUC-KR"); 
        writer.write("�̸�: " + customer.getName() + 
                     ", ��ȭ��ȣ: " + customer.getPhoneNum() + 
                     ", �����ȣ: " + reservID + 
                     ", �� ��ȣ: " + room.getRoomID() + 
                     ", �� Ÿ��: " + room.getType() + 
                     ", üũ�� ��¥: " + startDate.toString() + 
                     ", üũ�ƿ� ��¥: " + endDate.toString() + "\n"); 
    } catch (Exception e) {
        e.printStackTrace();
    } finally { 
        try { 
            if (writer != null) { 
                writer.close(); 
            } 
        } catch (Exception e2) {
            e2.printStackTrace(); 
        } 
    } 
}

    public void checkIn(Scanner sc) {
        System.out.println("===== üũ�� =====");
        System.out.print("üũ���� ���� ID�� �Է��ϼ���: ");
        try {
            int reservID = Integer.parseInt(sc.nextLine());
            for (Reservation reservation : reservations) {
                if (reservation.getReservID() == reservID) {
                    if (reservation.isCheckedIn()) {
                        System.out.println("�̹� üũ�ε� �����Դϴ�.");
                        return;
                    }
                    System.out.println("�����ȣ " + reservation.getReservID() + "�� üũ�� �Ϸ�.");
                    for (Room room : rooms) {
                        if (room.getRoomID() == reservation.getRoom().getRoomID()) {
                            System.out.println("�� ��ȣ�� " + room.getRoomID() + "�Դϴ�. ��ſ� �ð� �Ǽ���!");
                            room.setFull(true);
                            reservation.setCheckedIn(true);
                            break;
                        }
                    }
                    return;
                }
            }
            System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
        } catch (NumberFormatException e) {
            System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
        }
        System.out.println();
    }

    public void checkOut(Scanner sc) {
        System.out.println("===== üũ�ƿ� =====");
        System.out.print("üũ�ƿ��� ���� ID�� �Է��ϼ���: ");
        try {
            int reservID = Integer.parseInt(sc.nextLine());
            for (Reservation reservation : reservations) {
                if (reservation.getReservID() == reservID) {
                    if (reservation.isCheckedOut()) {
                        System.out.println("�̹� üũ�ƿ��� �����Դϴ�.");
                        return;
                    }
                    System.out.println("�����ȣ " + reservation.getReservID() + "�� üũ�ƿ� �Ϸ�.");
                    System.out.println("���� �̿���� " + getnextGrade(reservation.getVisitCount() + 1) + " ����Դϴ�.");
                    System.out.print("�߰� ������ �ʿ��Ͻʴϱ�? (��/�ƴϿ�) : ");
                    String response = sc.nextLine();

                    if (response.equalsIgnoreCase("��")) {
                        System.out.print("�߰� ���� �ݾ��� �Է��ϼ���: ");
                        int additionalPayment = Integer.parseInt(sc.nextLine());
                        additionalPayment(additionalPayment);
                    } else {
                        System.out.println("�湮���ּż� �����մϴ�.");
                    }

                    for (Room room : rooms) {
                        if (room.getRoomID() == reservation.getRoom().getRoomID()) {
                            room.setFull(false);
                            reservation.setCheckedOut(true);
                            break;
                        }
                    }
                    return;
                }
            }
            System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
        } catch (NumberFormatException e) {
            System.out.println("��ȿ�� ���� ID(����)�� �Է��ϼ���.");
        }
        System.out.println();
    }

    private void additionalPayment(int amount) {
        System.out.println("�߰� ���� " + amount + "���� �Ϸ�Ǿ����ϴ�.");
        System.out.println("�湮���ּż� �����մϴ�. ");
    }

    private String getnextGrade(int visitCount) {
            if (visitCount == 1) {
                return "��湮��";
            } else {
                return "�ܰ��";
            }
        }
}
