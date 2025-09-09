package com.ruhuna.uniplus.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(name = "regno")
    private String regNo;
    private String nic;
    private  String name;

    @Column(unique = true)
    private String email;
    private  String contact;
    private  String password;

    private Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Event> events = new ArrayList<>();

    public Admin() {}

    public Admin(String regNo, String nic, String name, String email, String contact, String password) {
        this.regNo = regNo;
        this.nic = nic;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
    }


    public Long getAdminId() {
        return adminId;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getNic() {
        return nic;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getPassword() {
        return password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<Event> getEvents() {
        return events;
    }



    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Admin{"+
                "adminId=" + adminId +
                ", regNo='" + regNo + '\'' +
                ", nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", createdAt=" + createdAt +
                '}';

    }
}
