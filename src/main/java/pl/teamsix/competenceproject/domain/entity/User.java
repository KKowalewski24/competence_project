package pl.teamsix.competenceproject.domain.entity;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private int age;
    private char gender; // 'F' or 'M'
    private ArrayList interests;
    private String profile;
    private String phoneNumber;

    public User(String firstName, String lastName, int age, char gender,
                ArrayList interests, String profile, String phoneNumber) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.interests = interests;
        this.profile = profile;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public ArrayList getInterests() {
        return interests;
    }

    public void setInterests(ArrayList interest) {
        this.interests = interest;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(age, user.age)
                .append(gender, user.gender)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(interests, user.interests)
                .append(profile, user.profile)
                .append(phoneNumber, user.phoneNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(firstName)
                .append(lastName)
                .append(age)
                .append(gender)
                .append(interests)
                .append(profile)
                .append(phoneNumber)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("age", age)
                .append("gender", gender)
                .append("interests", interests)
                .append("profile", profile)
                .append("phoneNumber", phoneNumber)
                .toString();
    }
}
