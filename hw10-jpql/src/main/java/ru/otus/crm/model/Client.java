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
import java.util.List;
import java.util.Objects;
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
        this.address = address;
        if (address != null) {
            address.setClient(this);
        }
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones != null ? new ArrayList<>(phones) : new ArrayList<>();
        this.phones.forEach(phone -> {
            if (phone != null) {
                phone.setClient(this);
            }
        });
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        Address clonedAddress = null;
        if (this.address != null) {
            clonedAddress = new Address(this.address.getId(), this.address.getStreet());
            clonedAddress.setClient(this);
        }

        List<Phone> clonedPhones = this.phones.stream()
                .map(phone -> {
                    Phone clonedPhone = new Phone(phone.getId(), phone.getNumber());
                    clonedPhone.setClient(this);
                    return clonedPhone;
                })
                .toList();

        Client clonedClient = new Client();
        clonedClient.id = this.id;
        clonedClient.name = this.name;
        clonedClient.setAddress(clonedAddress);
        clonedClient.setPhones(clonedPhones);

        return clonedClient;
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
