/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "tracker_role")
public class Role extends Base implements Serializable {

    @NotNull
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
