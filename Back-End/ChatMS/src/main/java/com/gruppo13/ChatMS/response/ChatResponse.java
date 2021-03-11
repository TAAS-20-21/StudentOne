package com.gruppo13.ChatMS.response;

import com.gruppo13.ChatMS.dto.MessageInfo;
import com.gruppo13.ChatMS.model.Message;
import com.gruppo13.ChatMS.model.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ChatResponse implements Serializable {

    private static final long serialVersionUID = -643266989769012601L;

    private long id;

    private Date dataCreazione;

    private String nome;

    private MessageInfo ultimoMessaggio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MessageInfo getUltimoMessaggio() {
        return ultimoMessaggio;
    }

    public void setUltimoMessaggio(MessageInfo ultimoMessaggio) {
        this.ultimoMessaggio = ultimoMessaggio;
    }
}
