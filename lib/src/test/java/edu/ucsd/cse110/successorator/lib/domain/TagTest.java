package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
public class TagTest {
    Tag homeTag = Tag.HOME;
    Tag schoolTag = Tag.SCHOOl;
    Tag workTag = Tag.WORK;
    Tag errandsTag = Tag.ERRANDS;

    @Test
    public void toChar() {
        assertEquals(homeTag.toChar(), 'h');
        assertEquals(schoolTag.toChar(), 's');
        assertEquals(workTag.toChar(), 'w');
        assertEquals(errandsTag.toChar(), 'e');
    }

    @Test
    public void fromChar() {
        assertEquals(Tag.fromChar('h'), Tag.HOME);
        assertEquals(Tag.fromChar('s'), Tag.SCHOOl);
        assertEquals(Tag.fromChar('w'), Tag.WORK);
        assertEquals(Tag.fromChar('e'), Tag.ERRANDS);
    }

}
