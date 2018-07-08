package com.sj.jobMatcher.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public class Job {

    private String about;
    
    private String billRate;
    
    private String company;
    
    private Boolean driverLicenseRequired;
    
    private String guid;
    
    private Long jobId;
    
    private String jobTitle;
    
    private Location location;
    
    private Set<String> requiredCertificates;
    
    private LocalDateTime startDate;
    
    private Long workersRequired;

    // Getters and Setters

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBillRate() {
        return billRate;
    }

    public void setBillRate(String billRate) {
        this.billRate = billRate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean getDriverLicenseRequired() {
        return driverLicenseRequired;
    }

    public void setDriverLicenseRequired(Boolean driverLicenseRequired) {
        this.driverLicenseRequired = driverLicenseRequired;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<String> getRequiredCertificates() {
        return requiredCertificates;
    }

    public void setRequiredCertificates(Set<String> requiredCertificates) {
        this.requiredCertificates = requiredCertificates;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Long getWorkersRequired() {
        return workersRequired;
    }

    public void setWorkersRequired(Long workersRequired) {
        this.workersRequired = workersRequired;
    }

}
