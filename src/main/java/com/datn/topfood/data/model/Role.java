package com.datn.topfood.data.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import com.datn.topfood.util.enums.RoleName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private RoleName role;

	@EqualsAndHashCode.Exclude
	@ToStringExclude
	@ManyToMany(mappedBy = "roles")
	private Set<Account> accounts;
}
