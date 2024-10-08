package inatools.backend.domain;

import inatools.backend.dto.strokecheck.StrokeCheckRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Getter
@Table(name = "stroke_check")
public class StrokeCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stroke_check_id")
    private Long id;

    private LocalDate recordAt;
    private Long testCount;
    private Double testResultAvg;

    @Enumerated(value = EnumType.STRING)
    private StrokeCheckTestType testType; // 테스트 종류

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected StrokeCheck() {}

    public StrokeCheck(LocalDate recordAt, Long testCount, Double testResultAvg, StrokeCheckTestType testType,
            Member member) {
        this.recordAt = recordAt;
        this.testCount = testCount;
        this.testResultAvg = testResultAvg;
        this.testType = testType;
        this.member = member;
    }

    public static StrokeCheck createStrokeCheck(StrokeCheckRequest request, Member member) {
        return new StrokeCheck(
                request.recordAt(),
                1L,
                request.testResultPercent(),
                request.testType(),
                member
        );
    }

    public void updateTestResult(Double newTestResultPercent) {
        this.testResultAvg = ((this.testResultAvg * this.testCount) + newTestResultPercent) / (this.testCount + 1);
        this.testCount += 1;
    }

}
