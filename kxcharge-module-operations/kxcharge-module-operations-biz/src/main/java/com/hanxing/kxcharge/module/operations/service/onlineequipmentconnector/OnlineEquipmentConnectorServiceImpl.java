package com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector;

import com.hanxing.kxcharge.framework.common.enums.ChargeEquipStatusEnum;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.ConnectorStatusInfoReqDto;
import com.hanxing.kxcharge.module.operations.dal.dataobject.onlinequipmentconnector.OnlineEquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.redis.onlinequipmentconnector.OnlineEquipmentConnectorDao;
import com.hanxing.kxcharge.module.operations.mq.producer.interflow.InteconnectedProducer;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class OnlineEquipmentConnectorServiceImpl implements OnlineEquipmentConnectorService{


    @Resource
    OnlineEquipmentConnectorDao onlineEquipmentConnectorDao;

    @Resource
    InteconnectedProducer inteconnectedProducer;


    private static final ExpiringMap<String, OnlineEquipmentConnectorDO> heartMap = ExpiringMap.builder()
            .maxSize(1)
            .expiration(200, TimeUnit.SECONDS)
            .variableExpiration().expirationPolicy(ExpirationPolicy.CREATED).build();

    private static final ExpiringMap<String, OnlineEquipmentConnectorDO> realTimeMap = ExpiringMap.builder()
            .maxSize(1)
            .expiration(1000, TimeUnit.SECONDS)
            .variableExpiration().expirationPolicy(ExpirationPolicy.CREATED).build();



    @Override
    public void heartBeat(String pileCode, String gunNumber, String state) {
        OnlineEquipmentConnectorDO onlineEquipmentConnectorDO = new OnlineEquipmentConnectorDO();
        String code = pileCode + gunNumber;
        onlineEquipmentConnectorDO.setCode(code);
        onlineEquipmentConnectorDO.setState(state);
        OnlineEquipmentConnectorDO connectorDO = heartMap.get(pileCode+gunNumber);
        if (connectorDO==null || !connectorDO.getState().equals(onlineEquipmentConnectorDO.getState())) {
            onlineEquipmentConnectorDao.set(onlineEquipmentConnectorDO);

            //故障了发送一次请求
            if ("01".equals(state)) {
                ConnectorStatusInfoReqDto connectorStatusInfoReqDto = new ConnectorStatusInfoReqDto();
                connectorStatusInfoReqDto.setStationId(onlineEquipmentConnectorDao.get(code,"stationId"));
                connectorStatusInfoReqDto.setStatus(ChargeEquipStatusEnum.ERROR.getStatus());
                connectorStatusInfoReqDto.setConnectorId(code);
                connectorStatusInfoReqDto.setLockStatus(0);
                connectorStatusInfoReqDto.setParkStatus(0);
                inteconnectedProducer.sendConnectorStatusInfo(connectorStatusInfoReqDto);
            }
            heartMap.put(pileCode + gunNumber, connectorDO);
        }
    }

    @Override
    public void login(Long stationId, String pileCode, Integer number) {
        //一般一个桩不会超过15个枪
        for (int i = 1; i <= number; i++){
            String code = pileCode + "0" + i;
            OnlineEquipmentConnectorDO onlineEquipmentConnectorDO = new OnlineEquipmentConnectorDO();
            onlineEquipmentConnectorDO.setCode(code);
            onlineEquipmentConnectorDO.setState("00");
            onlineEquipmentConnectorDO.setWorkState("02");
            onlineEquipmentConnectorDO.setStationId(stationId);
            onlineEquipmentConnectorDao.set(onlineEquipmentConnectorDO);

            ConnectorStatusInfoReqDto connectorStatusInfoReqDto = new ConnectorStatusInfoReqDto();
            connectorStatusInfoReqDto.setStationId(stationId);
            connectorStatusInfoReqDto.setStatus(ChargeEquipStatusEnum.FREE_TIME.getStatus());
            connectorStatusInfoReqDto.setConnectorId(code);
            connectorStatusInfoReqDto.setLockStatus(0);
            connectorStatusInfoReqDto.setParkStatus(0);
            inteconnectedProducer.sendConnectorStatusInfo(connectorStatusInfoReqDto);

        }
    }

    @Override
    public void realTimeMonitor(String pileCode, String gunNumber, String workState, String gunHomingStatus, String insertGunStatus) {
        String code = pileCode + gunNumber;
        OnlineEquipmentConnectorDO onlineEquipmentConnectorDO = new OnlineEquipmentConnectorDO();
        onlineEquipmentConnectorDO.setCode(code);
        onlineEquipmentConnectorDO.setWorkState(workState);
        onlineEquipmentConnectorDO.setIsHoming(gunHomingStatus);
        onlineEquipmentConnectorDO.setIsInsertArms(insertGunStatus);

        OnlineEquipmentConnectorDO connectorDO = realTimeMap.get(pileCode+gunNumber);
        if (connectorDO==null
                || !connectorDO.getWorkState().equals(onlineEquipmentConnectorDO.getWorkState())
                || !connectorDO.getIsInsertArms().equals(onlineEquipmentConnectorDO.getIsInsertArms())
                || !connectorDO.getIsHoming().equals(onlineEquipmentConnectorDO.getIsHoming())) {

            onlineEquipmentConnectorDao.set(onlineEquipmentConnectorDO);
            String cacheWorkState = onlineEquipmentConnectorDao.get(code, "workState");
            if (!cacheWorkState.equals(workState)) {
                ConnectorStatusInfoReqDto connectorStatusInfoReqDto = new ConnectorStatusInfoReqDto();
                connectorStatusInfoReqDto.setStationId(0L);
                if (cacheWorkState.equals("01")) {
                    connectorStatusInfoReqDto.setStatus(ChargeEquipStatusEnum.OCCUPATION_UN_CHARGE.getStatus());
                }
                if (cacheWorkState.equals("02")) {
                    connectorStatusInfoReqDto.setStatus(ChargeEquipStatusEnum.UNKNOWN.getStatus());
                }
                if (cacheWorkState.equals("00")) {
                    connectorStatusInfoReqDto.setStatus(ChargeEquipStatusEnum.FREE_TIME.getStatus());
                }
                connectorStatusInfoReqDto.setConnectorId(code);
                connectorStatusInfoReqDto.setLockStatus(0);
                connectorStatusInfoReqDto.setParkStatus(0);
                inteconnectedProducer.sendConnectorStatusInfo(connectorStatusInfoReqDto);
            }

            realTimeMap.put(pileCode + gunNumber, connectorDO);
        }



    }

    @Override
    public Integer getGunInfo(String connectorCode) {
        return null;
    }

    @Override
    public Integer getGunState(String connectorCode) {
        String state =  onlineEquipmentConnectorDao.get(connectorCode,"state");
        if (state == null){
            return -1;
        }
        return Integer.parseInt(state);
    }

    @Override
    public Integer getPileState(String pileCode, Integer number) {
        for (int i = 1; i <= number; i++){
            String state = onlineEquipmentConnectorDao.get(pileCode + "0" + number,"state");
            if (state==null){
                return -1;
            }
            if (("01").equals(state)){
                return 1;
            }
        }
        return 0;
    }

    public Integer getGunWorkState(String connectorCode) {
        String workState = onlineEquipmentConnectorDao.get(connectorCode,"workState");
        if (workState == null){
            return 0;
        }
        return Integer.parseInt(workState);
    }

    @Override
    public Long getStationId(String connectorCode) {
        Integer integer = onlineEquipmentConnectorDao.get(connectorCode,"stationId");
        if (integer == null){
            return 0L;
        }
        Long stationId = integer.longValue();
        return stationId;
    }

    @Override
    public Integer getInsertArmsState(String connectorCode) {
        String isInsertArms = onlineEquipmentConnectorDao.get(connectorCode,"isInsertArms");
        if (isInsertArms == null){
            return 0;
        }
        return Integer.parseInt(isInsertArms);
    }

    @Override
    public OnlineEquipmentConnectorDO getObject(String connectorCode) {
        return onlineEquipmentConnectorDao.getObject(connectorCode);
    }


}
