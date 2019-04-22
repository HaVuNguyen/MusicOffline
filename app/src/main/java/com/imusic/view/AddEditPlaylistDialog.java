package com.imusic.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imusic.R;
import com.imusic.models.Playlist;

public class AddEditPlaylistDialog extends Dialog {
    private Context mContext;
    private IOnSubmitListener mListener;
    private Playlist mPlaylist;
    private Button mBtnOk;
    private EditText mEditText;
    private TextView mTvTitle;

    public AddEditPlaylistDialog(Context context, Playlist playlist, IOnSubmitListener iOnSubmitListener) {
        super(context);
        mPlaylist = playlist;
        mListener = iOnSubmitListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_fragment);
        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        this.getWindow().setAttributes(wlp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
    }

    private void init() {
        mBtnOk = findViewById(R.id.btnSubmit);
        mEditText = findViewById(R.id.edtPlayListName);
        mTvTitle = findViewById(R.id.tvTitle);
        if (mPlaylist != null) {
            mTvTitle.setText("Edit Playlist");
            mEditText.setText(mPlaylist.getTitle());
            mEditText.setSelection(mEditText.getText().length());
        } else {
            mTvTitle.setText("Create Playlist");
        }
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Please enter playlist name", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.submit(mEditText.getText().toString());
                }
                dismiss();
            }
        });
    }

    public interface IOnSubmitListener {
        void submit(String name);
    }
}
