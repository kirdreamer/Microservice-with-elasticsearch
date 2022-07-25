package kirdreamer.serverproject.model;

import lombok.Getter;

@Getter
public class PostRequestModel implements PostRequestModelBuilder {
    private String firstName;
    private String lastName;
    private String email;

    @Override
    public PostRequestModelBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public PostRequestModelBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public PostRequestModelBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public PostRequestModel build() {
        return this;
    }
}
