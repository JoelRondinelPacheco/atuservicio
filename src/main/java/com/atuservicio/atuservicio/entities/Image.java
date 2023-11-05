package com.atuservicio.atuservicio.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="images")
@ToString
public class Image {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    private String id;

    private String mime;
    private String name;

    @Lob
    @Column(name = "content", length = 16777215)
    //@Column(name = "content", columnDefinition="MEDIUMBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;
}
