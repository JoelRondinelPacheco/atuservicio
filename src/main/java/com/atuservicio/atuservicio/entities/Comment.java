package com.atuservicio.atuservicio.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment extends Base {
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    private Date date;
    private Double score;
}
