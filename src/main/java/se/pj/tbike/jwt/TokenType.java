package se.pj.tbike.jwt;

public enum TokenType {

    /**
     *
     */
    BEARER("Bearer "),
    ;

    private final String prefix;

    TokenType(String prefix) {
        this.prefix = prefix;
    }

    public static String extractToken(String header) {
        if (header == null) {
            return null;
        }
        TokenType[] types = values();
        for (TokenType type : types) {
            if (header.startsWith(type.prefix)) {
                return header.substring(type.prefix.length());
            }
        }
        return null;
    }
}
