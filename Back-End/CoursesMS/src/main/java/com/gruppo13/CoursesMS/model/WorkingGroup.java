package com.gruppo13.CoursesMS.model;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(	name = "working_group")
public class WorkingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "working_group_members",
            joinColumns = @JoinColumn(name = "workingGroup_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> members;

    @OneToMany(mappedBy="workingGroup")
    private Set<CustomEvent> meeting;


    public WorkingGroup() {
    }

    public WorkingGroup(WorkingGroup workingGroup) {
        this.setName(workingGroup.getName());
        this.setMembers(workingGroup.getMembers());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Student> getMembers() {
        return members;
    }

    public void setMembers(Set<Student> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
