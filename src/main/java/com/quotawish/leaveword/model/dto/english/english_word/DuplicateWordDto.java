package com.quotawish.leaveword.model.dto.english.english_word;

public class DuplicateWordDto {

    private String wordHead;
    private Long count;

    public DuplicateWordDto() {}

    public DuplicateWordDto(String wordHead, Long count) {
        this.wordHead = wordHead;
        this.count = count;
    }

    public String getWordHead() {
        return wordHead;
    }

    public void setWordHead(String wordHead) {
        this.wordHead = wordHead;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
