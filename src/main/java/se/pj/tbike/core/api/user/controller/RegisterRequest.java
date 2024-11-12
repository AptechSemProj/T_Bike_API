package se.pj.tbike.core.api.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

	private String username;

	private String password;

	private String name;

	private String phoneNumber;

}
