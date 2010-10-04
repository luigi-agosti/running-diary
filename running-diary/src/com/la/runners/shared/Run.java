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
public class Run implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Clag(key=true,unique=true,onConflictPolicy=OnConflictPolicy.REPLACE)
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent
    private Date date;

    @Clag(hidden=true)
    @Persistent
    private Integer year;
    
    @Clag(hidden=true)
    @Persistent
    private Integer day;
    
    @Clag(hidden=true)
    @Persistent
    private Integer month;
    
    @Clag(hidden=true)
    @Persistent
    private Integer hour;

    @Persistent
    private Date modified;
    
    @Persistent
    private Double distance;
    
    @Persistent
    private Long time;
    
    @Persistent
    private String note;
    
    @Persistent
    private String shoes;
    
    @Persistent
    private Integer heartRate;
    
    @Persistent
    private Integer weight;
    
    @Persistent
    private Boolean share;
    
    @Clag(userId=true,hidden=true)
    @Persistent
    private String userId;
    
    public Run(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDistance() {
        return distance;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

	public void setShoes(String shoes) {
		this.shoes = shoes;
	}

	public String getShoes() {
		return shoes;
	}

	public void setHeartRate(Integer heartRate) {
		this.heartRate = heartRate;
	}

	public Integer getHeartRate() {
		return heartRate;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setShare(Boolean share) {
		this.share = share;
	}

	public Boolean getShare() {
		return share;
	}

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getDay() {
        return day;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getHour() {
        return hour;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
    
}
