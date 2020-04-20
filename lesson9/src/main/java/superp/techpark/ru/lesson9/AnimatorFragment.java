package superp.techpark.ru.lesson9;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Пример демонстрирует работу с {@link ValueAnimator} и {@link ObjectAnimator}
 */
public class AnimatorFragment extends Fragment {

    @BindView(R.id.target)
    TextView target;

    @BindDimen(R.dimen.start_font_size)
    int startFontSize;

    @BindDimen(R.dimen.end_font_size)
    int endFontSize;

    private ValueAnimator animator;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animator, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        reset();
    }

    /**
     * Вращает текст и одновременно меняет прозрачность
     */
    @OnClick(R.id.rotate_and_alpha)
    void onRotateAndAlphaClick() {
        reset();

        final float endValue = 360f;
        animator = ValueAnimator.ofFloat(0, endValue);
        animator.setDuration(1000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                target.setRotation(value);
                target.setAlpha(1f - value / endValue);
            }
        });
        animator.start();
    }

    /**
     * Триджы вращает текст на 360 градусов со сменой направления вращения
     */
    @OnClick(R.id.rotate_and_reverse)
    void onRotateAndReverseClick() {
        reset();

        animator = ObjectAnimator.ofFloat(target, View.ROTATION, 0, 360);
        animator.setDuration(1000L);
        animator.setRepeatCount(2);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    /**
     * Анимированно меняет цвет текста
     */
    @OnClick(R.id.text_color)
    void onTextColorClick() {
        reset();

        animator = ValueAnimator.ofArgb(0xFFFF0000, 0xFF00FF00, 0xFF0000FF);
        animator.setDuration(6000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                target.setTextColor((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * Анимированно меняет размер шрифта
     */
    @OnClick(R.id.text_size)
    void onTextColorSize() {
        reset();

        animator = ValueAnimator.ofInt(startFontSize, endFontSize);
        animator.setDuration(3000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                target.setTextSize((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void reset() {
        if (animator != null) {
            animator.cancel();
        }
        target.setTextColor(Color.BLACK);
        target.setTextSize(startFontSize);
        target.setRotation(0);
        target.setAlpha(1f);
    }

}
