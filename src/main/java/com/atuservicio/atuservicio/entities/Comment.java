package com.atuservicio.atuservicio.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment extends Base {
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private User emisor;
    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private User receptor;
    private Date date;
    private Double puntaje;
}
