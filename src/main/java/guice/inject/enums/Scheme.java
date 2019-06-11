package guice.inject.enums;

public enum Scheme {
    BASIC("basic"),
    MUTUAL("mutual");

    public final String value;

    Scheme(String value) {
        this.value = value;
    }
}
