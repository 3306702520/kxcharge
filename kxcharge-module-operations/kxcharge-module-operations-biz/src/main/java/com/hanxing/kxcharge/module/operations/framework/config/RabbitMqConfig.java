 package com.hanxing.kxcharge.module.operations.framework.config;


import com.hanxing.kxcharge.module.pile.mq.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


 @Configuration
 public class RabbitMqConfig {


     @Bean
     public Queue heartBeatQueue(){
         return new Queue(Heartbeat.QUEUE);
     }

     @Bean
     public Queue realTimeQueue(){
         return new Queue(RealTimeMonitor.QUEUE);
     }

     @Bean
     public Queue stopChargingQueue(){
         return new Queue(StopCharging.QUEUE);
     }

     @Bean
     public Queue remoteStartQueue(){
         return new Queue(StartChargingCommandReply.QUEUE);
     }

     @Bean
     public Queue remoteEndQueue(){
         return new Queue(RemoteShutdownCommandReply.QUEUE);
     }

 }
