package com.picpay_simplificado.services;

import com.picpay_simplificado.Dto.NotificationDto;
import com.picpay_simplificado.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

        NotificationDto notificationRequest = new NotificationDto(email,message);

        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify",notificationRequest,String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK))
        {
            System.out.println("Erro ao enviar notificação");
            throw new Exception("Serviço de notificação indisponivel");
        }
    }


}
