package com.sj.jobMatcher.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Worker {

    private Long age;
    
    private List<Availability> availability = new ArrayList<>(0);
    
    private Set<String> certificates = new HashSet<>(0);
    
    private String email;
    
    private String guid;
    
    private Boolean hasDriversLicense;
    
    private Boolean isActive;
    
    private JobSearchAddress jobSearchAddress;
    
    private Name name;
    
    private String phone;
    
    private Long rating;
    
    private Set<String> skills = new HashSet<>(0);
    
    private String transportation;
    
    private Long userId;

    // Getters and Setters

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }

    public Set<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<String> certificates) {
        this.certificates = certificates;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Boolean getHasDriversLicense() {
        return hasDriversLicense;
    }

    public void setHasDriversLicense(Boolean hasDriversLicense) {
        this.hasDriversLicense = hasDriversLicense;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public JobSearchAddress getJobSearchAddress() {
        return jobSearchAddress;
    }

    public void setJobSearchAddress(JobSearchAddress jobSearchAddress) {
        this.jobSearchAddress = jobSearchAddress;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
