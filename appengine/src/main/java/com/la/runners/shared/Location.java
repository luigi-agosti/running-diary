package com.la.runners.shared;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import novoda.clag.introspector.annotation.Clag;
import novoda.clag.model.MetaEntity.OnConflictPolicy;

@PersistenceCapable
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Clag(key=true,unique=true,onConflictPolicy=OnConflictPolicy.REPLACE)
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Clag(userId=true,hidden=true)
    @Persistent
    private String userId;
    
    @Persistent
    private Long latitude;
    
    @Persistent
    private Long longitude;
    
    @Persistent
    private Long altitude;
    
    @Persistent
    private Long time;
    
    @Persistent
    private Date timestamp;
    
    @Persistent
    private Long speed;
    
    @Persistent
    private Long distance;
    
    @Persistent
    private Long totalDistance;
    
    @Persistent
    private Long runId;

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public Long getDistance() {
        return distance;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public void setAltitude(Long altitude) {
        this.altitude = altitude;
    }

    public Long getAltitude() {
        return altitude;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }
    
    public void setLatitudeAsDouble(double latitude) {
        this.latitude = (long)(latitude*1E6);
    }
    
    public double getLatitudeAsDouble() {
        return latitude/1E6;
    }

    public double getLongitudeAsDouble() {
        return longitude/1E6;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }
    
    public void setLongitudeAsDouble(double longitude) {
        this.longitude = (long)(longitude*1E6);
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setTotalDistance(Long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Long getTotalDistance() {
        return totalDistance;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public Long getRunId() {
        return runId;
    }
    
    @Override
    public String toString() {
    	return "Location<lat,long,id> : " + getLatitude() + " " + getLongitude() + " " + getId();
    }
    
}
