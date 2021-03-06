package com.fjy.smartMonitorSystem.service.Impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fjy.smartMonitorSystem.dao.LocationMapper;
import com.fjy.smartMonitorSystem.dao.SensorMapper;
import com.fjy.smartMonitorSystem.model.Location;
import com.fjy.smartMonitorSystem.model.SB;
import com.fjy.smartMonitorSystem.model.Sensor;
import com.fjy.smartMonitorSystem.model.Vo.SensorVo;
import com.fjy.smartMonitorSystem.netty.handler.SimpleChatServerHandler;
import com.fjy.smartMonitorSystem.service.SensorService;

import io.netty.channel.group.ChannelGroup;

@Service
public class SensorServiceimpl implements SensorService {

	@Autowired
	private SensorMapper sensorMapper;
	@Autowired
	private LocationMapper locationMapper;

	@Override
	public boolean sava(Sensor sensor) {
		// 数据保存至数据库
		if (sensorMapper.save(sensor) > 0) {
			ChannelGroup chats = SimpleChatServerHandler.chats;
			if(chats != null && chats.size() > 0) {
				// 数据传输到客户端
				chats.writeAndFlush(new SB<Double>(11, sensor.getType(), sensor.getData()));
				System.out.println("asd");
			} else {
				System.out.println("没有客户端连接");
			}
			return true;
		}
		return false;
	}

	@Override
	public List<Sensor> getByTimeAndType(SensorVo sensorVo) {
		Long time = sensorVo.getTime().longValue();
		switch (sensorVo.getUnit()) {
		case "y":
			sensorVo.setSelectTime(new Timestamp(System.currentTimeMillis() - time * 1000 * 60 * 60 * 24 * 30 * 12));
			break;
		case "M":
			sensorVo.setSelectTime(new Timestamp(System.currentTimeMillis() - time * 1000 * 60 * 60 * 24 * 30));
			break;
		case "d":
			sensorVo.setSelectTime(new Timestamp(System.currentTimeMillis() - time * 1000 * 60 * 60 * 24));
			break;
		case "h":
			sensorVo.setSelectTime(new Timestamp(System.currentTimeMillis() - time * 1000 * 60 * 60));
			break;
		case "m":
			sensorVo.setSelectTime(new Timestamp(System.currentTimeMillis() - time * 1000 * 60));
			break;
		case "s":
			sensorVo.setSelectTime(new Timestamp(System.currentTimeMillis() - time * 1000));
			break;
		default:
			break;
		}
		List<Sensor> sensors = sensorMapper.getByTimeAndType(sensorVo);
		return sensors;
	}

	@Override
	public List<Sensor> getByType(String type) {
		return sensorMapper.getByType(type);
	}

	@Override
	public List<Sensor> getAll() {
		return sensorMapper.getAll();
	}

	@Override
	public void saveLocation(Location location) {
		location.setCreateTime(new Timestamp(System.currentTimeMillis()));
		locationMapper.save(location);
		SimpleChatServerHandler.chats.writeAndFlush(new SB<Location>(12, "localtion", location));
	}

	@Override
	public Location getLocation() {
		Location location = locationMapper.getlast();
		return location;
	}

}
