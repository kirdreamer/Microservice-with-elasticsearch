package kirdreamer.serverproject.elasticsearch_solution.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import static kirdreamer.serverproject.elasticsearch_solution.helper.Indexes.USER_INDEX;

@Getter
@Setter
@Document(indexName = USER_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class User {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String firstName;

    @Field(type = FieldType.Text)
    private String lastName;

    @Field(type = FieldType.Text)
    private String email;

}
