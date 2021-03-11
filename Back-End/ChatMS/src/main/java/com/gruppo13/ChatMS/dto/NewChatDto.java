package com.gruppo13.ChatMS.dto;

import java.util.Set;

public class NewChatDto {

    private String nome;

    private Set<Long> idPartecipanti;

    private Boolean isGroup;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Long> getIdPartecipanti() {
        return idPartecipanti;
    }

    public void setIdPartecipanti(Set<Long> idPartecipanti) {
        this.idPartecipanti = idPartecipanti;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean group) {
        isGroup = group;
    }
}
