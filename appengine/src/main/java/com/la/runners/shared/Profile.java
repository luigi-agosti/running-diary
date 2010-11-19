package com.la.runners.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import novoda.clag.introspector.annotation.Clag;
import novoda.clag.model.MetaEntity.OnConflictPolicy;

@PersistenceCapable
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    public Profile() {
    }
    
    public Profile(String userId, String nickname) {
        this.userId = userId;
        this.nickname = nickname;
    }
    
    @PrimaryKey
    @Clag(key=true,unique=true,onConflictPolicy=OnConflictPolicy.REPLACE)
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Clag(userId=true,hidden=true)
    @Persistent
    private String userId;
    
    @Persistent
    private String nickname;
    
    @Persistent
    private Date created;
    
    @Persistent
    private Date modified;
    
    @Persistent
    private Boolean shoes;
    
    @Persistent
    private Boolean heartRate;
    
    @Persistent
    private Boolean weight;
    
    @Persistent
    private Boolean weather;
    
    @Persistent
    private Integer unitSystem;
    
    @Clag(hidden=true)
    @Persistent
    private List<String> followers; 

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setShoes(Boolean shoes) {
        this.shoes = shoes;
    }

    public Boolean getShoes() {
        if(shoes == null) { 
            return Boolean.FALSE;
        }
        return shoes;
    }

    public void setHeartRate(Boolean heartRate) {
        this.heartRate = heartRate;
    }

    public Boolean getHeartRate() {
        if(heartRate == null) { 
            return Boolean.FALSE;
        }
        return heartRate;
    }

    public void setWeight(Boolean weight) {
        this.weight = weight;
    }

    public Boolean getWeight() {
        if(weight == null) { 
            return Boolean.FALSE;
        }
        return weight;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setWeather(Boolean weather) {
        this.weather = weather;
    }

    public Boolean getWeather() {
        if(weather == null) { 
            return Boolean.FALSE;
        }
        return weather;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setUnitSystem(Integer unitSystem) {
        this.unitSystem = unitSystem;
    }

    public Integer getUnitSystem() {
        return unitSystem;
    } 
    
}
