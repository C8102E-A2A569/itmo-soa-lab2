package c8102ea2a569.service2tomcat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "grammy_rewards",
        uniqueConstraints = @UniqueConstraint(columnNames = {"band_id", "genre"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrammyRewardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "band_id", nullable = false)
    private Integer bandId;

    @NotNull
    @Column(nullable = false, length = 50)
    private String genre;

    @NotNull
    @Column(name = "reward_date", nullable = false)
    private LocalDateTime rewardDate;
}
