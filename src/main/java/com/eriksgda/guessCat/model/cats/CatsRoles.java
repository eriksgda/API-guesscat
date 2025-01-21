package com.eriksgda.guessCat.model.cats;

import lombok.Getter;

@Getter
public enum CatsRoles {
    USER("user");

    private String role;

    CatsRoles(String role){
        this.role = role;
    }

}
