package com.smartcarebackend.config;

import com.smartcarebackend.model.*;
import com.smartcarebackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DummyDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final GiverRepository giverRepository;
    private final GuardRepository guardRepository;
    private final ResidentRepository residentRepository;
    private final CistRepository cistRepository;
    private final ActivityRepository activityRepository;
    private final MealRepository mealRepository;
    private final NoticeRepository noticeRepository;
    private final VisitRepository visitRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1) Role 생성
        Role roleAdmin = new Role();
        roleAdmin.setRoleName(AppRole.ROLE_ADMIN);
        roleAdmin = roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setRoleName(AppRole.ROLE_USER);
        roleUser = roleRepository.save(roleUser);

        // 2) User 생성 (Giver용)
        User giverUser = new User();
        giverUser.setUsername("giver01");
        giverUser.setEmail("giver01@test.com");
        giverUser.setPassword("pass1234");
        giverUser.setRelation("요양보호사");
        giverUser.setPhone("010-1111-2222");
        giverUser.setSsn("123456-1234567");
        giverUser.setAgree(true);
        giverUser.setRole(roleAdmin); // ROLE_Admin으로 지정
        giverUser = userRepository.save(giverUser);

        // 3) User 생성 (Guard용)
        User guardUser = new User();
        guardUser.setUsername("guard01");
        guardUser.setEmail("guard01@test.com");
        guardUser.setPassword("pass5678");
        guardUser.setRelation("가족");
        guardUser.setPhone("010-3333-4444");
        guardUser.setSsn("987654-7654321");
        guardUser.setAgree(true);
        guardUser.setRole(roleUser); // ROLE_USER로 지정
        guardUser = userRepository.save(guardUser);

        // 4) Giver 생성 (giverUser와 1:1 매핑)
        Giver giver = new Giver();
        giver.setUser(giverUser);
        // 다른 필드(예: residents) 초기에는 빈 리스트
        giverRepository.save(giver);

        // 5) Guard 생성 (guardUser와 1:1 매핑)
        Guard guard = new Guard();
        guard.setRelation("자녀");
        guard.setPhone("010-5555-6666");
        guard.setSsn("456123-4561234");
        guard.setAgree(true);
        guard.setUser(guardUser);
        guardRepository.save(guard);

        // 6) Resident 생성
        Resident resident = new Resident();
        resident.setResName("김환자");
        resident.setResGender("남");
        resident.setResBirth(LocalDate.of(1940, 1, 1));
        resident.setResPhone("02-123-4567");
        resident.setResGrade("2등급");
        resident.setDementiaYn(true);
        resident.setFallYn(false);
        resident.setBedsoreYn(false);
        resident.setPostureYn(true);
        resident.setResDisease("고혈압, 당뇨");
        resident.setResLocation("101호");
        resident.setResEnterDate(LocalDate.of(2025, 2, 1));
        resident.setResAddress("서울 어딘가");
        resident.setSystemResCode("SYS-001");
        resident.setResSchoolGrade("초등학교 졸업");
        resident.setResLongTermCareNo("LTC-2025-001");
        resident.setResCareGroup("A그룹");
        resident.setResFoodType("일반식");
        resident.setResFunctionDis("경증 거동불편");
        resident.setResImageAddress("/images/resident001.png");
        resident.setGiver(giver);  // Giver와 N:1 관계
        residentRepository.save(resident);

        // Guard가 Resident와 N:1 → Guard에 Resident 세팅
        guard.setResident(resident);
        guardRepository.save(guard);

        // 7) Cist (검사) 생성
        Cist cist = new Cist();
        // cistDt, cisModifyDt는 @CreationTimestamp로 자동
        cist.setOrientation(5L);
        cist.setAttention(2L);
        cist.setSpatialTemporal(2L);
        cist.setExecutiveFunction(5L);
        cist.setMemory(8L);
        cist.setLanguage(3L);
        cist.setTotalScore(25L);
        cist.setCisGrade("보통"); // 또는 로직으로 자동 계산
        cist.setResident(resident);
        cist.setGiver(giver);
        cistRepository.save(cist);

        // 8) Activity (활동, 신체 측정 정보) 생성
        Activity activity = new Activity();
        // activityLastDt는 @CreationTimestamp로 자동
        activity.setSkeletalMass(24.5);
        activity.setWeight(60.2);
        activity.setHeight(165.0);
        activity.setFatMass(18.0);
        activity.setFatPercent(30.0);
        activity.setResident(resident);
        activity.setGiver(giver);
        activityRepository.save(activity);

        // 9) Meal (식사일지) 생성
        Meal meal = new Meal();
        // meaDt는 @CreationTimestamp로 자동
        meal.setFundDis("중증");
        meal.setBreTp("일반식");
        meal.setBreQty("1인분");
        meal.setBreTime(LocalTime.of(8, 0));
        meal.setLunTp("영양식");
        meal.setLunQty("1.5인분");
        meal.setLunTime(LocalTime.of(12, 30));
        meal.setDinTp("죽");
        meal.setDinQty("1인분");
        meal.setDinTime(LocalTime.of(18, 0));
        meal.setMorSnackQty("반인분");
        meal.setMorSnackTime(LocalTime.of(10, 0));
        meal.setAftSnackQty("간단간식");
        meal.setAftSnackTime(LocalTime.of(15, 30));
        meal.setRemark("특이 사항 없음");
        meal.setResident(resident);
        meal.setGiver(giver);
        mealRepository.save(meal);

        // 10) Notice (공지사항) 생성
        Notice notice = new Notice();
        notice.setNoticeType("식단");
        notice.setNoticeTitle("이번 주 식단 안내");
        notice.setNoticeContent("이번 주에는 영양식을 중심으로 식단이 구성됩니다.");
        notice.setNoticeDate(LocalDate.now().atTime(9, 0));
        notice.setNoticeCount(0);
        notice.setGiver(giver);
        noticeRepository.save(notice);

        // 11) Visit (면회 예약) 생성
        Visit visit = new Visit();
        visit.setVisDate(LocalDate.of(2025, 3, 10));
        visit.setVisTime(LocalTime.of(14, 0));
        visit.setVisTp("visit");
        visit.setVisRelation("아들");
        visit.setVisCnt(2);
        visit.setVisApply("pending");
        visit.setVisYn(false);  // 아직 방문 전
        visit.setRemark("퇴원 관련 상담 예정");
        visit.setGuard(guard);
        visit.setGiver(giver);
        visitRepository.save(visit);

        // 더미 데이터 생성 완료
        System.out.println("===== Dummy Data Loaded =====");
    }
}