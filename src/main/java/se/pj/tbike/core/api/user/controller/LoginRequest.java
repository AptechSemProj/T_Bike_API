package se.pj.tbike.core.api.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

	private String username;

	private String password;

}
