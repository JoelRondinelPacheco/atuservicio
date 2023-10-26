package com.atuservicio.atuservicio.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Comment extends Base {
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private User emisor;
    private User receptor;
    private Date date;
    private Double puntaje;
}
