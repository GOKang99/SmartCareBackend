package com.smartcarebackend.config;

import com.smartcarebackend.model.*;
import com.smartcarebackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecondDummy implements CommandLineRunner {

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
    private final CompositionRepository compositionRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1) Role 생성
        Role roleUser = roleRepository.findByRoleName(AppRole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

        Role roleAdmin = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

        // 요양사 유저 10명 생성
        for (int i = 1; i <= 10; i++) {
            User giverUser = new User("giver0" + i, "giver0" + i + "@test.com", passwordEncoder.encode("1234"));
            giverUser.setRole(roleAdmin);
            giverUser.setRealname("신짱구" + i);
            giverUser.setPhone("010-1234-123" + i);
            giverUser.setSsn("000000-000000" + i);
            giverUser.setUserimage("anyuser.png");
            userRepository.save(giverUser);

            // 4) Giver 생성 (giverUser와 1:1 매핑)
            Giver giver = new Giver();
            giver.setUser(giverUser);
            giverRepository.save(giver);

            // 6) Resident 생성
            Resident resident = new Resident();
            resident.setResName("김환자" + i);
            resident.setResGender(i % 2 == 0 ? "여" : "남");
            resident.setResBirth(LocalDate.of(1940, 1, 1));
            resident.setResPhone("02-123-456" + i);
            resident.setResGrade("2등급");
            resident.setDementiaYn(true);
            resident.setFallYn(false);
            resident.setBedsoreYn(false);
            resident.setPostureYn(true);
            resident.setResDisease("고혈압, 당뇨");
            resident.setResLocation("101호");
            resident.setResEnterDate(LocalDate.of(2025, 2, 1));
            resident.setResAddress("서울 어딘가");
            resident.setSystemResCode("SYS-00" + i);
            resident.setResSchoolGrade("초등학교 졸업");
            resident.setResLongTermCareNo("LTC-2025-00" + i);
            resident.setResCareGroup("A그룹");
            resident.setResFoodType("일반식");
            resident.setResFunctionDis("경증 거동불편");
            resident.setResImageAddress("/images/resident" + i + ".png");
            resident.setGiver(giver);  // Giver와 N:1 관계
            residentRepository.save(resident);

            // 7) Cist (검사) 생성
            Cist cist = new Cist();
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
        }

        // 보호자 유저 10명 생성
        for (int i = 1; i <= 10; i++) {
            User guardUser = new User("guard0" + i, "guard0" + i + "@test.com", passwordEncoder.encode("1234"));
            guardUser.setRole(roleUser);
            guardUser.setRealname("신짱아" + i);
            guardUser.setPhone("010-1234-12" + (i + 10));
            guardUser.setSsn("111111-111111" + i);
            guardUser.setUserimage("anyuser.png");
            userRepository.save(guardUser);

            // 5) Guard 생성 (guardUser와 1:1 매핑)
            Guard guard = new Guard();
            guard.setRelation("자녀");
            guard.setAgree(true);
            guard.setUser(guardUser);
            guardRepository.save(guard);

            // Guard가 Resident와 N:1 → Guard에 Resident 세팅
            Resident resident = residentRepository.findById((long)i).orElse(null);
            if (resident != null) {
                guard.setResident(resident);
                guardRepository.save(guard);
            }

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
            visit.setGiver(giverRepository.findById((long)i).orElse(null)); // 해당 요양사 지정
            visitRepository.save(visit);
        }
        // 12) Composition (신체 측정 정보) 생성
        for (int i = 1; i <= 10; i++) {
            // 각 Resident에 대해 Composition 데이터 생성
            Resident resident = residentRepository.findById((long)i).orElse(null);
            if (resident != null) {
                Composition composition = new Composition();
                composition.setComDate(LocalDate.now()); // 오늘 날짜
                composition.setComHeight(165.0 + (i % 5)); // 신장: 예시로 165에서 증가
                composition.setComWeight(60.0 + (i % 5)); // 체중: 예시로 60에서 증가
                composition.setComSmm(25.0 + (i % 3)); // 골격근량
                composition.setComBfm(15.0 + (i % 2)); // 체지방량
                composition.setComPbf(30.0 + (i % 4)); // 체지방율
                composition.setComBmi(22.0); // BMI: 고정값
                composition.setComFatLvl(3); // 내장지방레벨: 예시로 3 설정

                composition.setResident(resident); // Resident와 연결
                composition.setGiver(giverRepository.findById((long)i).orElse(null)); // Giver와 연결
                compositionRepository.save(composition);
            }
        }

        // 더미 데이터 생성 완료
        System.out.println("===== Dummy Data Loaded =====");
    }
}
