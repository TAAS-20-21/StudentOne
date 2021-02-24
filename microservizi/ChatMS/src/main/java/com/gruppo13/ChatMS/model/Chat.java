package com.gruppo13.ChatMS.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gruppo13.ChatMS.util.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 7071612763962915521L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = DateUtils.PATTERN_DATEHOUR, timezone = DateUtils.TIMEZONE)
    private Date dataCreazione;

    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> partecipanti;

    @OneToMany(
            mappedBy = "chat",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();

    private Long courseId;

    public Chat() {
    }

    public Chat(Date datacreazione, String nome) {
        this.setDataCreazione(datacreazione);
        this.setNome(nome);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<User> getPartecipanti() {
        return partecipanti;
    }

    public void setPartecipanti(Set<User> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addPartecipante(User user){
        this.partecipanti.add(user);
    }

    public void removePartecipante(User user){
        this.partecipanti.remove(user);
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
