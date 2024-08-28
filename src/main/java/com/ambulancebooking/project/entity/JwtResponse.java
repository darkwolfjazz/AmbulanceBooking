package com.ambulancebooking.project.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

private String email;
private String jwtToken;

}
