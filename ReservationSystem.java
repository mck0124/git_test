import java.lang.System;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReservationSystem {
    private List<Reservation> reservations;
    private List<Room> rooms;

    public ReservationSystem(List<Reservation> reservations, List<Room> rooms) {
        this.reservations = reservations;
        this.rooms = rooms;
    }

    public Reservation addReservation(Customer customer, Room room, int members, LocalDateTime startDate, LocalDateTime endDate) {
        Reservation reservation = new Reservation(customer, room, members, startDate, endDate);
        reservations.add(reservation);
        return reservation;
    }

    public Map<String, Long> getAvailableRoomCounts(LocalDateTime startDate, LocalDateTime endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.groupingBy(Room::getType, Collectors.counting()));
    }

    public List<Room> getAvailableRooms(LocalDateTime startDate, LocalDateTime endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.toList());
    }

    public void checkIn(int reservID) {
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
                break;
            }
        }
    }

    public void checkOut(int reservID) {
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
            if (visitCount == 0) {
                return "��湮��";
            } else {
                return "�ܰ��";
            }
        }
}
