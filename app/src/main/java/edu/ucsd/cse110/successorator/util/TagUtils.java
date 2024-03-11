package edu.ucsd.cse110.successorator.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.lib.domain.Tag;

public class TagUtils {
    public static Drawable getTagDrawable(Resources resources, Tag tag) {
        if (tag == null) return null;
        switch (tag) {
                case HOME:
                    return ResourcesCompat.getDrawable(resources, R.drawable.h_button, null);
                case SCHOOl:
                    return ResourcesCompat.getDrawable(resources, R.drawable.s_button, null);
                case WORK:
                    return ResourcesCompat.getDrawable(resources, R.drawable.w_button, null);
                case ERRANDS:
                    return ResourcesCompat.getDrawable(resources, R.drawable.e_button, null);
        }
        throw new RuntimeException(String.format("No drawable defined for tag '%c'", tag.toChar()));
    }
}
