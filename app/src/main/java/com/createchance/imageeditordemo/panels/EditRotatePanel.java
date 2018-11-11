package com.createchance.imageeditordemo.panels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.createchance.imageeditor.IEManager;
import com.createchance.imageeditor.ops.ModelViewOperator;
import com.createchance.imageeditordemo.R;

/**
 * Rotate edit panel.
 *
 * @author gaochao1-iri
 * @date 2018/11/5
 */
public class EditRotatePanel extends AbstractPanel implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "EditRotatePanel";

    private View mRotatePanel;

    private TextView mTvRotateX, mTvRotateY, mTvRotateZ, mTranslateX, mTranslateY, mTranslateZ;

    private ModelViewOperator mCurOp;

    public EditRotatePanel(Context context, PanelListener listener) {
        super(context, listener, TYPE_ROTATE);

        mRotatePanel = LayoutInflater.from(mContext).inflate(R.layout.edit_panel_rotate, mParent, false);
        mRotatePanel.findViewById(R.id.iv_cancel).setOnClickListener(this);
        mRotatePanel.findViewById(R.id.iv_apply).setOnClickListener(this);
        ((SeekBar) mRotatePanel.findViewById(R.id.sb_rotate_x)).setOnSeekBarChangeListener(this);
        ((SeekBar) mRotatePanel.findViewById(R.id.sb_rotate_y)).setOnSeekBarChangeListener(this);
        ((SeekBar) mRotatePanel.findViewById(R.id.sb_rotate_z)).setOnSeekBarChangeListener(this);
        ((SeekBar) mRotatePanel.findViewById(R.id.sb_translate_x)).setOnSeekBarChangeListener(this);
        ((SeekBar) mRotatePanel.findViewById(R.id.sb_translate_y)).setOnSeekBarChangeListener(this);
        ((SeekBar) mRotatePanel.findViewById(R.id.sb_translate_z)).setOnSeekBarChangeListener(this);
        mTvRotateX = mRotatePanel.findViewById(R.id.tv_rotate_x);
        mTvRotateY = mRotatePanel.findViewById(R.id.tv_rotate_y);
        mTvRotateZ = mRotatePanel.findViewById(R.id.tv_rotate_z);
        mTranslateX = mRotatePanel.findViewById(R.id.tv_translate_x);
        mTranslateY = mRotatePanel.findViewById(R.id.tv_translate_y);
        mTranslateZ = mRotatePanel.findViewById(R.id.tv_translate_z);
        mTvRotateX.setText(String.format(mContext.getString(R.string.edit_rotate_x), 180));
        mTvRotateY.setText(String.format(mContext.getString(R.string.edit_rotate_y), 0));
        mTvRotateZ.setText(String.format(mContext.getString(R.string.edit_rotate_z), 0));
        mTranslateX.setText(String.format(mContext.getString(R.string.edit_translate_x), 0f));
        mTranslateY.setText(String.format(mContext.getString(R.string.edit_translate_y), 0f));
    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    @Override
    public void show(ViewGroup parent, int surfaceWidth, int surfaceHeight) {
        super.show(parent, surfaceWidth, surfaceHeight);

        mParent.addView(mRotatePanel);
    }

    @Override
    public void close(boolean discard) {
        super.close(discard);

        if (discard && mCurOp != null) {
            IEManager.getInstance().removeOperator(mCurOp);
            mCurOp = null;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_cancel:
                close(true);
                break;
            case R.id.iv_apply:
                close(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }

        if (mCurOp == null) {
            mCurOp = new ModelViewOperator.Builder()
                    .build();
            IEManager.getInstance().addOperator(mCurOp);
        }
        switch (seekBar.getId()) {
            case R.id.sb_rotate_x:
                mTvRotateX.setText(String.format(mContext.getString(R.string.edit_rotate_x), progress));
                mCurOp.setRotateX(progress);
                break;
            case R.id.sb_rotate_y:
                mTvRotateY.setText(String.format(mContext.getString(R.string.edit_rotate_y), progress));
                mCurOp.setRotateY(progress);
                break;
            case R.id.sb_rotate_z:
                mTvRotateZ.setText(String.format(mContext.getString(R.string.edit_rotate_z), progress));
                mCurOp.setRotateZ(progress);
                break;
            case R.id.sb_translate_x:
                mTranslateX.setText(String.format(mContext.getString(R.string.edit_translate_x),
                        ((progress - seekBar.getMax() / 2.0f) * 4.0f) / seekBar.getMax()));
                mCurOp.setTranslateX(((progress - seekBar.getMax() / 2.0f) * 4.0f) / seekBar.getMax());
                break;
            case R.id.sb_translate_y:
                mTranslateY.setText(String.format(mContext.getString(R.string.edit_translate_y),
                        ((progress - seekBar.getMax() / 2.0f) * 4.0f) / seekBar.getMax()));
                mCurOp.setTranslateY(((progress - seekBar.getMax() / 2.0f) * 4.0f) / seekBar.getMax());
                break;
            case R.id.sb_translate_z:
                float transZ = mCurOp.getNear() +
                        (progress * (mCurOp.getFar() - mCurOp.getNear()) / seekBar.getMax());
                mTranslateZ.setText(String.format(mContext.getString(R.string.edit_translate_z), transZ));
                mCurOp.setTranslateZ(transZ);
                break;
            default:
                break;
        }
        IEManager.getInstance().updateOperator(mCurOp);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
