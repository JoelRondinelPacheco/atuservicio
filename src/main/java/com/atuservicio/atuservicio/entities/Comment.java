package com.atuservicio.atuservicio.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends Base {

    @ManyToOne                          //Relación 'muchos a uno': muchos comentarios pueden ser escritos por un mismo autor ('User')
    @JoinColumn(name = "author_id")
    private User author;
    
    @ManyToOne                          ////Relación 'muchos a uno': muchos comentarios pueden tener como destino a un mismo destinatario ('User')
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date date;
    
    private String content;
    
    private Double score;
    
    @ManyToMany
    @JoinTable(
        name = "comments_ratings", 
        joinColumns = @JoinColumn(name = "comment_id"), 
        inverseJoinColumns = @JoinColumn(name = "qualification_id")
    )
    private List<Qualification> ratings;   //calificaciones

    //public Comment(User author, User receiver, Contract contract, String content, Double S)
    
    
}
