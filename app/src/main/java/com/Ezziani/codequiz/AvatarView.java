package com.Ezziani.codequiz;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class AvatarView extends View {

    private Paint skinPaint, hairPaint, eyePaint, pupilPaint,
            mouthPaint, bgPaint, shirtPaint, neckPaint,
            glowPaint, blushPaint, whitePaint, lidPaint;

    private float mouthOpenAmount = 0f;    // 0 = closed, 1 = open
    private float blinkAmount = 0f;        // 0 = open eyes, 1 = closed
    private boolean isThinking = false;
    private float talkAmount = 0f;
    private ValueAnimator talkAnimator;
    private ValueAnimator thinkAnimator;
    private float thinkRotation = 0f;

    public AvatarView(Context context) { super(context); init(); }
    public AvatarView(Context context, AttributeSet attrs) { super(context, attrs); init(); }
    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle); init();
    }

    private void init() {
        skinPaint = makePaint(Color.parseColor("#FFDAB9"), Paint.Style.FILL);
        hairPaint = makePaint(Color.parseColor("#2C1810"), Paint.Style.FILL);
        eyePaint  = makePaint(Color.WHITE, Paint.Style.FILL);
        pupilPaint = makePaint(Color.parseColor("#1A1A2E"), Paint.Style.FILL);
        mouthPaint = makePaint(Color.parseColor("#C0392B"), Paint.Style.FILL);
        bgPaint    = makePaint(Color.parseColor("#F4F3FF"), Paint.Style.FILL);
        shirtPaint = makePaint(Color.parseColor("#7C6AF7"), Paint.Style.FILL);
        neckPaint  = makePaint(Color.parseColor("#FFDAB9"), Paint.Style.FILL);
        blushPaint = makePaint(Color.parseColor("#FFB6C1"), Paint.Style.FILL);
        blushPaint.setAlpha(120);
        whitePaint = makePaint(Color.WHITE, Paint.Style.FILL);
        lidPaint   = makePaint(Color.parseColor("#FFDAB9"), Paint.Style.FILL);
        glowPaint  = makePaint(Color.parseColor("#7C6AF7"), Paint.Style.STROKE);
        glowPaint.setStrokeWidth(6f);
        glowPaint.setAlpha(80);
    }

    private Paint makePaint(int color, Paint.Style style) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(color);
        p.setStyle(style);
        return p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth(), h = getHeight();
        float cx = w / 2f, cy = h / 2f;

        // Background circle
        canvas.drawCircle(cx, cy, Math.min(w,h)*0.48f, bgPaint);
        canvas.drawCircle(cx, cy, Math.min(w,h)*0.48f, glowPaint);

        float scale = Math.min(w, h) / 400f;

        // ── Shirt / Body ──────────────────────────────────────
        RectF shirt = new RectF(cx-90*scale, cy+115*scale, cx+90*scale, cy+190*scale);
        canvas.drawRoundRect(shirt, 30*scale, 30*scale, shirtPaint);

        // Collar
        Paint collarPaint = makePaint(Color.WHITE, Paint.Style.FILL);
        canvas.drawOval(new RectF(cx-30*scale, cy+105*scale, cx+30*scale, cy+145*scale), collarPaint);

        // ── Neck ──────────────────────────────────────────────
        canvas.drawRoundRect(new RectF(cx-18*scale, cy+88*scale, cx+18*scale, cy+125*scale),
                10*scale, 10*scale, neckPaint);

        // ── Head ──────────────────────────────────────────────
        canvas.drawOval(new RectF(cx-80*scale, cy-110*scale, cx+80*scale, cy+95*scale), skinPaint);

        // ── Hair ──────────────────────────────────────────────
        // Top hair
        canvas.drawOval(new RectF(cx-82*scale, cy-130*scale, cx+82*scale, cy-40*scale), hairPaint);
        // Side hair left
        canvas.drawOval(new RectF(cx-95*scale, cy-90*scale, cx-60*scale, cy+20*scale), hairPaint);
        // Side hair right
        canvas.drawOval(new RectF(cx+60*scale, cy-90*scale, cx+95*scale, cy+20*scale), hairPaint);
        // Forehead coverage
        canvas.drawRect(cx-80*scale, cy-130*scale, cx+80*scale, cy-60*scale, hairPaint);

        // ── Eyebrows ──────────────────────────────────────────
        Paint browPaint = makePaint(Color.parseColor("#2C1810"), Paint.Style.STROKE);
        browPaint.setStrokeWidth(5*scale);
        browPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(cx-55*scale, cy-45*scale, cx-25*scale, cy-50*scale, browPaint);
        canvas.drawLine(cx+25*scale, cy-50*scale, cx+55*scale, cy-45*scale, browPaint);

        // ── Eyes ──────────────────────────────────────────────
        float eyeY = cy - 20*scale;
        float eyeW = 28*scale, eyeH = 22*scale;

        // Left eye white
        canvas.drawOval(new RectF(cx-58*scale, eyeY-eyeH, cx-58*scale+eyeW*2, eyeY+eyeH), eyePaint);
        // Right eye white
        canvas.drawOval(new RectF(cx+58*scale-eyeW*2, eyeY-eyeH, cx+58*scale, eyeY+eyeH), eyePaint);

        // Pupils
        canvas.drawCircle(cx-44*scale, eyeY, 10*scale, pupilPaint);
        canvas.drawCircle(cx+44*scale, eyeY, 10*scale, pupilPaint);

        // Eye shine
        Paint shinePaint = makePaint(Color.WHITE, Paint.Style.FILL);
        canvas.drawCircle(cx-40*scale, eyeY-5*scale, 4*scale, shinePaint);
        canvas.drawCircle(cx+48*scale, eyeY-5*scale, 4*scale, shinePaint);

        // Blink — eyelids
        if (blinkAmount > 0f) {
            lidPaint.setColor(Color.parseColor("#FFDAB9"));
            float blinkH = eyeH * 2 * blinkAmount;
            canvas.drawOval(new RectF(cx-58*scale, eyeY-eyeH, cx-58*scale+eyeW*2, eyeY-eyeH+blinkH), lidPaint);
            canvas.drawOval(new RectF(cx+58*scale-eyeW*2, eyeY-eyeH, cx+58*scale, eyeY-eyeH+blinkH), lidPaint);
        }

        // ── Nose ──────────────────────────────────────────────
        Paint nosePaint = makePaint(Color.parseColor("#E8A87C"), Paint.Style.FILL);
        canvas.drawOval(new RectF(cx-8*scale, cy+22*scale, cx+8*scale, cy+38*scale), nosePaint);

        // ── Blush ─────────────────────────────────────────────
        canvas.drawOval(new RectF(cx-72*scale, cy+30*scale, cx-38*scale, cy+55*scale), blushPaint);
        canvas.drawOval(new RectF(cx+38*scale, cy+30*scale, cx+72*scale, cy+55*scale), blushPaint);

        // ── Mouth ─────────────────────────────────────────────
        float mouthY = cy + 60*scale;
        float openH = (mouthOpenAmount + talkAmount) * 20*scale;
        float mouthW = 35*scale;

        if (openH > 2) {
            // Open mouth
            RectF mouthRect = new RectF(cx-mouthW, mouthY-openH/2,
                    cx+mouthW, mouthY+openH/2);
            canvas.drawRoundRect(mouthRect, 12*scale, 12*scale, mouthPaint);
            // Teeth
            Paint teethPaint = makePaint(Color.WHITE, Paint.Style.FILL);
            RectF teethRect = new RectF(cx-mouthW+4, mouthY-openH/2,
                    cx+mouthW-4, mouthY);
            canvas.drawRoundRect(teethRect, 8*scale, 8*scale, teethPaint);
        } else {
            // Smile line
            Paint smilePaint = makePaint(Color.parseColor("#C0392B"), Paint.Style.STROKE);
            smilePaint.setStrokeWidth(4*scale);
            smilePaint.setStrokeCap(Paint.Cap.ROUND);
            RectF smileRect = new RectF(cx-mouthW, mouthY-15*scale,
                    cx+mouthW, mouthY+10*scale);
            canvas.drawArc(smileRect, 15, 150, false, smilePaint);
        }

        // ── Thinking dots ─────────────────────────────────────
        if (isThinking) {
            Paint dotPaint = makePaint(Color.parseColor("#7C6AF7"), Paint.Style.FILL);
            float dotY = cy - 140*scale;
            for (int i = 0; i < 3; i++) {
                float angle = thinkRotation + i * 120;
                float dx = (float)(30*scale * Math.cos(Math.toRadians(angle)));
                float dy = (float)(15*scale * Math.sin(Math.toRadians(angle)));
                dotPaint.setAlpha(i == 0 ? 255 : i == 1 ? 180 : 100);
                canvas.drawCircle(cx + dx, dotY + dy, 8*scale, dotPaint);
            }
        }
    }

    public void setMouthOpen(boolean open) {
        mouthOpenAmount = open ? 1f : 0f;
        invalidate();
    }

    public void blink() {
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f, 0f);
        anim.setDuration(200);
        anim.addUpdateListener(a -> {
            blinkAmount = (float) a.getAnimatedValue();
            invalidate();
        });
        anim.start();
    }

    public void startTalking() {
        if (talkAnimator != null) talkAnimator.cancel();
        talkAnimator = ValueAnimator.ofFloat(0f, 1f, 0f);
        talkAnimator.setDuration(300);
        talkAnimator.setRepeatCount(ValueAnimator.INFINITE);
        talkAnimator.setRepeatMode(ValueAnimator.REVERSE);
        talkAnimator.addUpdateListener(a -> {
            talkAmount = (float) a.getAnimatedValue();
            invalidate();
        });
        talkAnimator.start();
    }

    public void stopTalking() {
        if (talkAnimator != null) talkAnimator.cancel();
        talkAmount = 0f;
        mouthOpenAmount = 0f;
        invalidate();
    }

    public void setThinking(boolean thinking) {
        isThinking = thinking;
        if (thinking) {
            if (thinkAnimator != null) thinkAnimator.cancel();
            thinkAnimator = ValueAnimator.ofFloat(0f, 360f);
            thinkAnimator.setDuration(1200);
            thinkAnimator.setRepeatCount(ValueAnimator.INFINITE);
            thinkAnimator.setInterpolator(new LinearInterpolator());
            thinkAnimator.addUpdateListener(a -> {
                thinkRotation = (float) a.getAnimatedValue();
                invalidate();
            });
            thinkAnimator.start();
        } else {
            if (thinkAnimator != null) thinkAnimator.cancel();
            invalidate();
        }
    }
}