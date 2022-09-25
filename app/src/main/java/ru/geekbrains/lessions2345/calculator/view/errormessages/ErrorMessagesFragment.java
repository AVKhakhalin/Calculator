package ru.geekbrains.lessions2345.calculator.view.errormessages;

import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.ERROR_MESSAGE_KEY;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import ru.geekbrains.lessions2345.calculator.R;

public class ErrorMessagesFragment extends BottomSheetDialogFragment {
    public ErrorMessagesFragment newInstance(String errorMessage) {
        Bundle args = new Bundle();
        ErrorMessagesFragment fragment = new ErrorMessagesFragment();
        args.putString(ERROR_MESSAGE_KEY, errorMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
        @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_errormessages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            ((TextView) view.findViewById(R.id.message)).
                setText(getArguments().getString(ERROR_MESSAGE_KEY));
        }
    }
}
