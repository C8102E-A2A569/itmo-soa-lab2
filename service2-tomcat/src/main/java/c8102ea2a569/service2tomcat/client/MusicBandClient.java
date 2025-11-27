package c8102ea2a569.service2tomcat.client;

import c8102ea2a569.service2tomcat.dto.MusicBandDTO;
import c8102ea2a569.service2tomcat.exception.ResourceNotFoundException;
import c8102ea2a569.service2tomcat.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MusicBandClient {

    private final RestTemplate restTemplate;

    @Value("${service1.base-url}")
    private String service1BaseUrl;

    public MusicBandDTO getBandById(Integer bandId) {
        try {
            String url = service1BaseUrl + "/api/bands/" + bandId;
            ResponseEntity<MusicBandDTO> response = restTemplate.getForEntity(url, MusicBandDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Группа с ID " + bandId + " не найдена в Service 1");
        } catch (Exception e) {
            throw new ServiceUnavailableException("Service 1 недоступен: " + e.getMessage());
        }
    }

    public void updateBand(Integer bandId, MusicBandDTO dto) {
        try {
            String url = service1BaseUrl + "/api/bands/" + bandId;
            restTemplate.put(url, dto);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Группа с ID " + bandId + " не найдена в Service 1");
        } catch (Exception e) {
            throw new ServiceUnavailableException("Service 1 недоступен: " + e.getMessage());
        }
    }
}
