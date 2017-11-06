package com.flame239.dumbaccountservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Data
@Entity
public class Account {
    protected Account() {
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Id
    @GeneratedValue
    private long id;
    @JsonProperty("partner_id")
    @Column(nullable = false)
    private long partnerId;
    @JsonProperty("profile_id")
    @Column(nullable = false)
    private long profileId;
    @Column(nullable = false)
    @Length(min = 4)
    private String name;
    @JsonProperty("avatar_url")
    @URL
    private String avatarUrl;
}
