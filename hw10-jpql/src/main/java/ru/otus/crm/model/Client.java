package ru.otus.crm.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("MethodDoesntCallSuperMethod")
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @SuppressWarnings("DefaultAnnotationParam")
    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    public Client(String name) {
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        setAddress(address);
        setPhones(phones);
    }

    public void setAddress(Address address) {
        this.address = Optional.ofNullable(address)
                .map(a -> {
                    var clonedAddress = new Address(a.getId(), a.getStreet());
                    clonedAddress.setClient(this);
                    return clonedAddress;
                })
                .orElse(null);
    }

    public void setPhones(List<Phone> phones) {
        this.phones = Optional.ofNullable(phones).orElse(Collections.emptyList()).stream()
                .map(phone -> {
                    var clonedPhone = new Phone(phone.getId(), phone.getNumber());
                    clonedPhone.setClient(this);
                    return clonedPhone;
                })
                .toList();
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        var clonedAddress = Optional.ofNullable(address)
                .map(a -> new Address(a.getId(), a.getStreet()))
                .orElse(null);
        var clonedPhones = phones.stream()
                .map(phone -> new Phone(phone.getId(), phone.getNumber()))
                .toList();
        return new Client(id, name, clonedAddress, clonedPhones);
    }

    @Override
    public String toString() {
        return "Client{id=%d, name='%s', address=%s, phones=%s}".formatted(id, name, address, phones);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id)
                && Objects.equals(name, client.name)
                && Objects.equals(address, client.address)
                && Objects.equals(phones, client.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phones);
    }
}
