package kirdreamer.serverproject.model;

public interface PostRequestModelBuilder {
    PostRequestModelBuilder setFirstName(String firstName);

    PostRequestModelBuilder setLastName(String lastName);

    PostRequestModelBuilder setEmail(String email);

    PostRequestModel build();
}
