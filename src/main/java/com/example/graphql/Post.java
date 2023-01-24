package com.example.graphql;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Post {

    private String id;
    private String title;
    private String text;
    private String category;
    private String authorId;

}