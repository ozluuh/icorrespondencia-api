package br.com.icorrespondencia.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private DeliveryType type;

    @PrePersist
    private void onPreSave() {
        if (this.deliveryDate == null) {
            this.deliveryDate = LocalDateTime.now();
            this.read = false;
        }

        switch (description.substring(description.length() - 1).toLowerCase()) {
            case "f":
            case "1":
            case "a":
            case "7":
                this.type = DeliveryType.CONTA_LUZ;
                break;
            case "0":
            case "2":
            case "b":
            case "d":
                this.type = DeliveryType.CONTA_AGUA;
                break;
            case "9":
            case "3":
            case "c":
            case "5":
                this.type = DeliveryType.ENCOMENDA;
                break;
            default:
                this.type = DeliveryType.OUTROS;
                break;
        }
    }
}
