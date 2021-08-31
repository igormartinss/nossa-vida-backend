package com.mci.br.nossavida.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Type(type="org.hibernate.type.BinaryType")
    @Lob
    private byte[] image;

    @ManyToOne
    private Period period;
}
