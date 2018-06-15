package com.example.SIA.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;



/**
 * Created by c1632130 on 28/11/2017.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    String title;
    String link;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}


