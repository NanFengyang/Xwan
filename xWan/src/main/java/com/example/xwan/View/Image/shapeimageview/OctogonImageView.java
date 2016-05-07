package com.example.xwan.View.Image.shapeimageview;

import android.content.Context;
import android.util.AttributeSet;

import com.example.xwan.R;
import com.example.xwan.View.Image.shapeimageview.shader.ShaderHelper;
import com.example.xwan.View.Image.shapeimageview.shader.SvgShader;

public class OctogonImageView extends ShaderImageView {

    public OctogonImageView(Context context) {
        super(context);
    }

    public OctogonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OctogonImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.imgview_octogon);
    }
}
