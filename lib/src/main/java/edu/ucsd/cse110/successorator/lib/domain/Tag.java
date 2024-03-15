package edu.ucsd.cse110.successorator.lib.domain;

public enum Tag {
    HOME,
    SCHOOl,
    WORK,
    ERRANDS;


    public char toChar() {
        switch (this) {
            case HOME: return 'h';
            case SCHOOl: return 's';
            case WORK: return 'w';
            case ERRANDS: return 'e';
            default:
                throw new RuntimeException("unreachable");
        }
    }

    public static Tag fromChar(char c) {
        switch (c) {
            case 'h': return HOME;
            case 's': return SCHOOl;
            case 'w': return WORK;
            case 'e': return ERRANDS;
            default:
                throw new RuntimeException(String.format("no such Tag for %c", c));
        }
    }
}
