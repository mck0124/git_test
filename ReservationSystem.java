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

    // 예약 list[]에 추가
    public Reservation addReservation(Customer customer, Room room, Customer members, LocalDateTime startDate, LocalDateTime endDate) {
        Reservation reservation = new Reservation(customer, room, members, startDate, endDate);
        reservations.add(reservation);
        return reservation;
    }

    // 예약가능한 방 수 출력
    public Map<String, Long> getAvailableRoomCounts(LocalDateTime startDate, LocalDateTime endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.groupingBy(Room::getType, Collectors.counting()));
    }

    // 예약가능한 방 출력
    public List<Room> getAvailableRooms(LocalDateTime startDate, LocalDateTime endDate) {
        return rooms.stream()
                .filter(room -> reservations.stream()
                        .noneMatch(reservation -> reservation.getRoom().equals(room) &&
                                (reservation.getStartDate().isBefore(endDate) && reservation.getEndDate().isAfter(startDate))))
                .collect(Collectors.toList());
    }

    // 고객 정보 저장
    public void saveInfo(Customer customer, int reservID, int members) {
        try {
            String fileName;

            fileName = "고객 예약내역 리스트.txt";
            writer = new FileWriter(fileName, true);
            writer.write("이름: " + customer.getName() + ", 전화번호: " + customer.getPhoneNum() + ", 예약번호: " + reservID + ", 예약 인원: "
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
        System.out.println("===== 체크인 =====");
        System.out.print("체크인할 예약 ID를 입력하세요: ");
        try {
            int reservID = Integer.parseInt(sc.nextLine());
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
                    return;
                }
            }
            System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
        } catch (NumberFormatException e) {
            System.out.println("유효한 예약 ID(숫자)를 입력하세요.");
        }
        System.out.println();
    }

    public void checkOut(int reservID) {
        System.out.println("===== 체크아웃 =====");
        System.out.print("체크아웃할 예약 ID를 입력하세요: ");
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
            if (visitCount == 1) {
                return "재방문고객";
            } else {
                return "단골고객";
            }
        }
}
