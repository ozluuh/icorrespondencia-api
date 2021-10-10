package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import br.com.icorrespondencia.api.domain.validation.View;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonView(View.Private.class)
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime deliveryDate;

    private boolean read;

    @JsonProperty("read_at")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime readAt;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Room room;

    private String description;

    @PrePersist
    private void onPreSave() {
        if (this.deliveryDate == null) {
            this.deliveryDate = LocalDateTime.now();
            this.read = false;
        }
    }
}
