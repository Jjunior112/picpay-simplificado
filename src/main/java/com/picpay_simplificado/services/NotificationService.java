package com.picpay_simplificado.services;

import com.picpay_simplificado.Dto.NotificationDto;
import com.picpay_simplificado.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDto notificationRequest = new NotificationDto(email, message);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    "https://util.devi.tools/api/v1/notify",
                    notificationRequest,
                    Void.class
            );

            if (!response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                throw new Exception("Serviço de notificação respondeu com erro: " + response.getStatusCode());
            }

        } catch (RestClientException ex) {
            System.err.println("Erro ao enviar notificação: " + ex.getMessage());
            throw new Exception("Serviço de notificação indisponível no momento. Tente novamente mais tarde.", ex);
        }
    }


}
