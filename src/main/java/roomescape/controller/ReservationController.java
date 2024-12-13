package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ReservationController {

    // 메모리에 예약 데이터 저장
    private List<Reservation> reservations = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1);  // 예약 ID 자동 증가

    public ReservationController() {
        // 테스트를 위한 임의의 예약 데이터 추가
        reservations.add(new Reservation(idCounter.getAndIncrement(), "브라운", "2024-12-12", "10:00"));
        reservations.add(new Reservation(idCounter.getAndIncrement(), "솔라", "2024-12-12", "11:00"));
        reservations.add(new Reservation(idCounter.getAndIncrement(), "부리", "2024-12-13", "14:00"));
    }

    // 예약 관리 페이지 로드
    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy"; // reservation-legacy.html 페이지 반환
    }

    // 예약 목록 조회 API
    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservations; // 예약 목록 반환
    }

    // 예약 추가 API
    @PostMapping("/reservations")
    @ResponseBody
    public Reservation createReservation(@RequestBody Reservation reservation) {
        int id = idCounter.getAndIncrement(); // 새로운 예약 ID
        Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(newReservation); // 예약 목록에 추가
        return newReservation; // 추가된 예약 반환
    }

    // 예약 취소 API
    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public String deleteReservation(@PathVariable int id) {
        reservations.removeIf(reservation -> reservation.getId() == id); // ID에 해당하는 예약 삭제
        return "예약이 취소되었습니다."; // 삭제 확인 메시지 반환
    }
}
