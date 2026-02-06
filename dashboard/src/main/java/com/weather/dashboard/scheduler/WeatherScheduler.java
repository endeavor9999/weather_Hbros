package com.weather.dashboard.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import lombok.RequiredArgsConstructor;
import java.util.*;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class WeatherScheduler {

    private final SimpMessagingTemplate messagingTemplate;

    // 1초마다 실행 (가상의 센서 데이터 전송)
    @Scheduled(fixedRate = 1000)
    public void sendWeatherData() {
        // 서울, 부산, 대구, 광주 랜덤 온도 생성
        Map<String, Object> data = new HashMap<>();
        data.put("SEOUL", 20 + new Random().nextInt(10)); // 20~29도
        data.put("BUSAN", 25 + new Random().nextInt(5));
        
        // 날씨 상태 랜덤 (BUSAN이 비오면 'RAIN' 전송)
        data.put("BUSAN_STATUS", new Random().nextInt(10) > 7 ? "RAIN" : "SUN");

        // '/topic/weather'를 듣고 있는 앱(형님)에게 쏜다!
        //messagingTemplate.convertAndSend("/topic/weather", data);
        messagingTemplate.convertAndSend("/topic/weather", (Object) data);
        System.out.println("Data Sent: " + data); // 로그 확인용
    }
}