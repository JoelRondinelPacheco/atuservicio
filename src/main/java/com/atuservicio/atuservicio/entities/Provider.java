/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atuservicio.atuservicio.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author dario
 */
@Entity
@Table(name = "users")
public class Provider extends User{
    private String category;
}
