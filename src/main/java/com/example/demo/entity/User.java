package com.example.demo.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	/** ユーザーID */
	@Id
	private Integer id;
	
	/** ユーザーネーム */
	private String name;
	
	/** ユーザーのメールアドレス */
	private String mail;
	
	/** ユーザーの年齢 */
	private Integer age;
}
