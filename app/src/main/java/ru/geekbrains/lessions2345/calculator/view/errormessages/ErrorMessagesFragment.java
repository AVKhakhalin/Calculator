package ru.geekbrains.lessions2345.calculator.view.errormessages;

import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.ERROR_MESSAGE_KEY;
import static ru.geekbrains.lessions2345.calculator.view.ViewConstants.ERROR_TYPE_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import ru.geekbrains.lessions2345.calculator.R;
import ru.geekbrains.lessions2345.calculator.core.Constants;

public class ErrorMessagesFragment extends BottomSheetDialogFragment {
    public ErrorMessagesFragment newInstance(String errorMessage, int errorType) {
        Bundle args = new Bundle();
        ErrorMessagesFragment fragment = new ErrorMessagesFragment();
        args.putString(ERROR_MESSAGE_KEY, errorMessage);
        args.putInt(ERROR_TYPE_KEY, errorType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);
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
            if (getArguments().getInt(ERROR_TYPE_KEY) == Constants.ERRORS_IN_STRING_TYPE)
                ((TextView) view.findViewById(R.id.error_message_title)).
                    setText(requireContext().
                    getString(R.string.error_message_title_in_string_type_text));
            else if (getArguments().getInt(ERROR_TYPE_KEY) == Constants.ERRORS_INPUTTING_TYPE)
                ((TextView) view.findViewById(R.id.error_message_title)).
                    setText(requireContext().
                    getString(R.string.error_message_title_inputting_type_text));
        }
    }
}
