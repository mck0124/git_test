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
                System.out.println("예약번호 " + reservation.getReservID() + "로 체크인 완료.");
                for (Room room : rooms) {
                    if (room.getRoomID() == reservation.getRoom().getRoomID()) {
                        System.out.println("방 번호는 " + room.getRoomID() + "입니다. 즐거운 시간 되세요!");
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
                System.out.println("예약번호 " + reservation.getReservID() + "로 체크아웃 완료.");
                System.out.println("다음 이용부터 " + getnextGrade(reservation.getVisitCount()+1) + " 등급입니다.");
                System.out.print("추가 결제가 필요하십니까? (네/아니오) : ");
                Scanner sc = new Scanner(System.in);
                String response = sc.nextLine();

                if (response.equalsIgnoreCase("네")) {
                    System.out.print("추가 결제 금액을 입력하세요: ");
                    int additionalPayment = sc.nextInt();
                    additionalPayment(additionalPayment);
                }else System.out.println("방문해주셔서 감사합니다. ");

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
        System.out.println("추가 결제 " + amount + "원이 완료되었습니다.");
        System.out.println("방문해주셔서 감사합니다. ");
    }

    private String getnextGrade(int visitCount) {
            if (visitCount == 0) {
                return "재방문고객";
            } else {
                return "단골고객";
            }
        }
}
