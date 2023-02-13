package it.pharmacywebassistant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity(name = "Degree") @Table(name = "Degrees")
@NoArgsConstructor
public final class Degree implements Serializable {

    @Serial
    private static final long serialVersionUID = -72649182736446L;

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "vote")
    @NotNull(message = "{Degree.Vote.NotNull}")
    @Range(min = 66, max = 110, message = "{Dregree.Vote.Range}")
    private Byte vote;

    @Column(name = "releaseDate")
    @NotNull(message = "{Degree.ReleaseDate.NotNull}")
    private Date releaseDate;

    @Column(name = "university")
    @NotNull(message = "{Degree.University.NotNull}")
    private String university;

    @OneToOne(mappedBy = "degree")
    private Doctor doctor;

    public Degree(Long id, Byte vote, Date releaseDate, String university) {
        this.id = id;
        this.vote = vote;
        this.releaseDate = releaseDate;
        this.university = university;
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
}
