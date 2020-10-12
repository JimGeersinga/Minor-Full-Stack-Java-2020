package com.jim.spring_rest.dao;

import com.jim.spring_rest.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AccountHolder extends BaseEntity {
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="street", column=@Column(name = "address_street")),
            @AttributeOverride(name="city", column=@Column(name = "address_city")),
            @AttributeOverride(name="state", column=@Column(name = "address_state")),
            @AttributeOverride(name="postalCode", column=@Column(name = "address_postal_code")),
            @AttributeOverride(name="country", column=@Column(name = "address_country"))
    })
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "accountHolder_account",
            joinColumns = @JoinColumn(name="accountHolder_Id"),
            inverseJoinColumns = @JoinColumn(name="account_Id")
    )
    Set<Account> accounts;
}
