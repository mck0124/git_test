import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Reservation {
    // 필드
// 고객, 예약 ID, 방, 체크인 날짜, 체크아웃 날짜
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
        this.room.setFull(true); // 방이 예약되었음을 표시
        this.members = members;
        this.visitCount++;
        this.reservID = nextReservID++; // 예약 ID 자동 생성
    }

    public static void createReservation(Scanner sc, List<Customer> customers, ReservationSystem system, DateTimeFormatter formatter) {
        // 고객 전화번호 입력 (+존재 여부 확인)
        System.out.println("===== 방 예약 =====");
        System.out.print("예약할 고객 전화번호를 입력하세요: ");
        String phoneNum = sc.nextLine();
        System.out.println();
        Customer foundCustomer = customers.stream()
                .filter(c -> c.getPhoneNum().equals(phoneNum))
                .findFirst().orElse(null);
        if (foundCustomer == null) {
            System.out.println("고객을 찾을 수 없습니다.");
            System.out.println();
            return;
        }

        // 예약 날짜 입력
        System.out.println("----- 예약 날짜 입력 -----");
        LocalDate startDate = null, endDate = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println("체크인 날짜를 입력하세요 (ex. 2025-03-20): ");
            String startDateStr = sc.nextLine();
            System.out.println();
            System.out.println("체크아웃 날짜를 입력하세요 (ex. 2025-03-20): ");
            String endDateStr = sc.nextLine();
            System.out.println();
            try {
                startDate = LocalDate.parse(startDateStr, formatter);
                endDate = LocalDate.parse(endDateStr, formatter);

                // 유효한 예약 날짜인지 확인 절차 (오입력 방지)
                if (startDate.isAfter(endDate)) {
                    System.out.println("체크인 날짜는 체크아웃 날짜보다 이전이어야 합니다. 다시 입력해 주세요.");
                } else if (startDate.isBefore(LocalDate.now())) {
                    System.out.println("체크인 날짜는 현재보다 미래여야 합니다. 다시 입력해주세요.");
                } else validDate = true;
            } catch (Exception e) {
                System.out.println("날짜 형식이 잘못되었습니다. (ex. 2025-03-20)");
                System.out.println();
            }
        }

        // 해당 날짜에 예약 가능한 방 목록 (필터링)
        Map<String, Long> availableRooms = system.getAvailableRoomCounts(startDate, endDate);
        if (availableRooms.isEmpty()) {
            System.out.println(startDate + "에 예약 가능한 방이 없습니다.");
            System.out.println();
            return;
        }

        // 방 타입별로 그룹화 해서 출력하기
        System.out.println("----- " + startDate + "에 예약 가능한 방 -----");
        availableRooms.forEach((type, count) ->
                System.out.println(type + "룸: " + count + "개 남음"));
        System.out.println();

        // 방 타입 선택
        System.out.print("예약할 방 타입을 입력하세요 (single/deluxe/double): ");
        String roomType = sc.nextLine();
        System.out.println();
        Room selectedRoom = system.getAvailableRooms(startDate, endDate).stream()
                .filter(r -> r.getType().equalsIgnoreCase(roomType))
                .findFirst().orElse(null);

        if (selectedRoom == null) {
            System.out.println("선택하신 방은 예약하실 수 없습니다.");
            System.out.println();
            return;
        }

        // 정원 초과 체크 및 추가요금 계산
        int extraCharge = 0;
        int days = (int)java.time.Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();
        if (foundCustomer.getMembers() > selectedRoom.getFullMember()) {
            int extraPeople = foundCustomer.getMembers() - selectedRoom.getFullMember();
            extraCharge = extraPeople * 10000 * days;
            System.out.println("정원을 " + extraPeople + "명 초과로 인해 인당 10,000원씩  " + extraCharge + "원의 추가요금이 부과됩니다.");
        }
        System.out.println();

        // 예약 생성
        Reservation reservation = system.addReservation(foundCustomer, selectedRoom, foundCustomer.getMembers(), startDate, endDate);
        foundCustomer.setReservID(reservation);
        System.out.println("----- 예약 완료 -----");
        System.out.println("예약이 완료되었습니다.\n예약 ID: " + reservation.getReservID());
        System.out.println();
    }
    
    public void printReserv(Scanner sc, List<Reservation> reservations) {
        System.out.println("===== 예약 정보 출력 =====");
        System.out.print("예약 ID를 입력하세요: ");
        int reservID = Integer.parseInt(sc.nextLine());
        Reservation foundReservation = reservations.stream()
            .filter(r -> r.getReservID() == reservID)
            .findFirst().orElse(null);
        if (foundReservation != null) {
        if (foundReservation.getReservID() == reservID) {
            System.out.println("----- 예약 내역 -----");
            System.out.println("예약 내역 출력:");
            System.out.println("고객 이름: " + foundReservation.getCustomer().getName());
            System.out.println("예약 ID: " + foundReservation.getReservID());
            System.out.println("방번호: " + foundReservation.getRoom().getRoomID());
            System.out.println("체크인 날짜: " + foundReservation.getStartDate());
            System.out.println("체크아웃 날짜: " + foundReservation.getEndDate());
            int days = (int)java.time.Duration.between(foundReservation.getStartDate().atStartOfDay(), foundReservation.getEndDate().atStartOfDay()).toDays();
            int basePrice = foundReservation.getRoom().getPrice() * days;
            int extraCharge = 0;
            if (foundReservation.getMembers() > foundReservation.getRoom().getFullMember()) {
                extraCharge = (foundReservation.getMembers() - foundReservation.getRoom().getFullMember()) * 10000 * days;
            }
            System.out.println("기본 가격: " + basePrice + "원");
            if (extraCharge > 0) {
                System.out.println("추가 요금: " + extraCharge + "원");
                System.out.println("총 가격: " + (basePrice + extraCharge) + "원");
            }
        } else {
            System.out.println("해당 예약 ID를 찾을 수 없습니다.");
}
        } else {
            System.out.println("예약을 찾을 수 없습니다.");
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
