package c8102ea2a569.service2tomcat.service;

import c8102ea2a569.service2tomcat.client.MusicBandClient;
import c8102ea2a569.service2tomcat.dto.MusicBandDTO;
import c8102ea2a569.service2tomcat.dto.RewardResponse;
import c8102ea2a569.service2tomcat.entity.GrammyRewardEntity;
import c8102ea2a569.service2tomcat.exception.ValidationException;
import c8102ea2a569.service2tomcat.repository.GrammyRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GrammyService {

    private final MusicBandClient musicBandClient;
    private final GrammyRewardRepository rewardRepository;

    @Transactional
    public void removeParticipant(Integer bandId) {
        MusicBandDTO band = musicBandClient.getBandById(bandId);

        if (band.getNumberOfParticipants() <= 1) {
            throw new ValidationException("Невозможно удалить участника — в группе должен остаться хотя бы один участник");
        }

        band.setNumberOfParticipants(band.getNumberOfParticipants() - 1);
        musicBandClient.updateBand(bandId, band);
    }

    @Transactional
    public RewardResponse rewardBand(Integer bandId, String genre) {
        MusicBandDTO band = musicBandClient.getBandById(bandId);

        if (!band.getGenre().equalsIgnoreCase(genre)) {
            throw new ValidationException(
                    "Жанр группы (" + band.getGenre() + ") не соответствует указанному жанру награды: " + genre
            );
        }

        if (rewardRepository.existsByBandIdAndGenre(bandId, genre)) {
            throw new ValidationException("Группа уже награждена Grammy в жанре " + genre);
        }

        GrammyRewardEntity reward = new GrammyRewardEntity();
        reward.setBandId(bandId);
        reward.setGenre(genre);
        reward.setRewardDate(LocalDateTime.now());
        rewardRepository.save(reward);

        return new RewardResponse(bandId, genre, "Группа была награждена Grammy в жанре " + genre);
    }
}
