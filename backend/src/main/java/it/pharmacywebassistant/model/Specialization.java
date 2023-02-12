package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "Specialization") @Table(name = "Specializations")
@NoArgsConstructor
public final class Specialization implements Serializable {

    @Serial
    private static final long serialVersionUID = -72649182736446L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "vote")
    @NotNull(message = "{Specialization.Vote.NotNull}")
    @Range(min = 66, max = 110, message = "{Dregree.Vote.Range}")
    private Byte vote;

    @Column(name = "releaseDate")
    @NotNull(message = "{Specialization.ReleaseDate.NotNull}")
    private Date releaseDate;

    @Column(name = "university")
    @NotNull(message = "{Specialization.University.NotNull}")
    private String university;

    @Column(name = "type")
    @NotNull(message = "{Specialization.Type.NotNull}")
    private String type;

    @OneToOne(mappedBy = "specialization")
    private Doctor doctor;

    public Specialization(Long id, Byte vote, Date releaseDate, String university, String type, Doctor doctor) {
        this.id = id;
        this.vote = vote;
        this.releaseDate = releaseDate;
        this.university = university;
        this.type = type;
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getVote() {
        return vote;
    }

    public void setVote(Byte vote) {
        this.vote = vote;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
