package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Component
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS, value = WebApplicationContext.SCOPE_SESSION)
@Table(name="reservaties")
public class Reservatie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Transient
    private String slug;

    @Transient
    private StepOneData stepOneData;

    @Transient
    private StepTwoData stepTwoData;

    @Transient
    private User user;
}
