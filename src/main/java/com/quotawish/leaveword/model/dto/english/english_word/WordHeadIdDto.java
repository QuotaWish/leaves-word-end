package com.quotawish.leaveword.model.dto.english.english_word;

import lombok.Data;

@Data
public class WordHeadIdDto {

    private String head;
    private Long id;

    public WordHeadIdDto(String head, Long id) {
        this.head = head;
        this.id = id;
    }

}
