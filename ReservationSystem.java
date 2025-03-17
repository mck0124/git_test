import java.io.FileWriter;
import java.lang.System;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationSystem {
    private List<Reservation> reservations;
    private List<Room> rooms;
    FileWriter writer = null;

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
    public void saveInfo(Customer customer, int reservID, int members) {
        try {
            String fileName;

            fileName = "�� ���೻�� ����Ʈ.txt";
            writer = new FileWriter(fileName, true);
            writer.write("�̸�: " + customer.getName() + ", ��ȭ��ȣ: " + customer.getPhoneNum() + ", �����ȣ: " + reservID + ", ���� �ο�: "
                    + members + "\n");
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

    public void checkIn(Scanner sc) {
        System.out.println("===== üũ�� =====");
        System.out.print("üũ���� ���� ID�� �Է��ϼ���: ");
        try {
            int reservID = Integer.parseInt(sc.nextLine());
            for (Reservation reservation : reservations) {
                if (reservation.getReservID() == reservID) {
                    System.out.println("�����ȣ " + reservation.getReservID() + "�� üũ�� �Ϸ�.");
                    for (Room room : rooms) {
                        if (room.getRoomID() == reservation.getRoom().getRoomID()) {
                            System.out.println("�� ��ȣ�� " + room.getRoomID() + "�Դϴ�. ��ſ� �ð� �Ǽ���!");
                            room.setFull(true);
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

    public void checkOut(int reservID) {
        System.out.println("===== üũ�ƿ� =====");
        System.out.print("üũ�ƿ��� ���� ID�� �Է��ϼ���: ");
        for (Reservation reservation : reservations) {
            if (reservation.getReservID() == reservID) {
                System.out.println("�����ȣ " + reservation.getReservID() + "�� üũ�ƿ� �Ϸ�.");
                System.out.println("���� �̿���� " + getnextGrade(reservation.getVisitCount()+1) + " ����Դϴ�.");
                System.out.print("�߰� ������ �ʿ��Ͻʴϱ�? (��/�ƴϿ�) : ");
                Scanner sc = new Scanner(System.in);
                String response = sc.nextLine();

                if (response.equalsIgnoreCase("��")) {
                    System.out.print("�߰� ���� �ݾ��� �Է��ϼ���: ");
                    int additionalPayment = sc.nextInt();
                    additionalPayment(additionalPayment);
                }else System.out.println("�湮���ּż� �����մϴ�. ");

                for (Room room : rooms) {
                    if (room.getRoomID() == reservation.getRoom().getRoomID()) {
                        room.setFull(false);
                        break;
                    }
                }
                break;
            }
        }
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
