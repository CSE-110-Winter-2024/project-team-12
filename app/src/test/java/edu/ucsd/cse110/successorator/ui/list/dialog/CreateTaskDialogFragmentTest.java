package edu.ucsd.cse110.successorator.ui.list.dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentCreateMitBinding;
import edu.ucsd.cse110.successorator.lib.domain.Task;

import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.P}) // Use the appropriate SDK version
public class CreateTaskDialogFragmentTest {

    @Mock
    private MainViewModel mockMainViewModel;
    @Mock
    private ViewModelProvider.Factory mockFactory;
    @Mock
    private DialogInterface mockDialog;
    @Mock
    private FragmentCreateMitBinding mockView;

    private CreateTaskDialogFragment fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockFactory.create(any())).thenReturn(mockMainViewModel);
        fragment = CreateTaskDialogFragment.newInstance();
        fragment.activityModel = mockMainViewModel;
        fragment.view = mockView;
    }
}