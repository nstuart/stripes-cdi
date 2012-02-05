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
import javax.validation.constraints.Size;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "project")
public class Project extends Base implements Serializable {

    @NotNull
    @Size(max = 255, min = 1)
    private String projectName;
    private String description;
    @Size(min = 10)
    private String homePage;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
