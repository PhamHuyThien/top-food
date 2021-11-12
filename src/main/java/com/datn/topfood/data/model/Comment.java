package com.datn.topfood.data.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment extends Base{

	@Column(length = 1000)
	private String content;
	
	@ManyToOne 
	@JoinColumn(name = "account_id")
	private Account account;

}
