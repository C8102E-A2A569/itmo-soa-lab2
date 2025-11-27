package c8102ea2a569.service2tomcat.repository;

import c8102ea2a569.service2tomcat.entity.GrammyRewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrammyRewardRepository extends JpaRepository<GrammyRewardEntity, Long> {
    boolean existsByBandIdAndGenre(Integer bandId, String genre);
}
