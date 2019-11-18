package superp.techpark.ru.lesson9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Демонстрирует разницу между Property и View Animation. Обратите внимание на кликабельность
 * шарика в начале и в конце анимации.
 */
public class ComparisonFragment extends Fragment {

    private static final long ANIMATION_DURATION = 3000L;

    @BindView(R.id.radio_view_anim)
    RadioButton radioViewAnim;

    @BindView(R.id.ball)
    View ball;

    @BindView(R.id.play)
    Button playButton;

    @BindDimen(R.dimen.anim_distance)
    int distance;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comparision, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.play)
    void onPlayClick() {
        if (radioViewAnim.isChecked()) {
            playViewAnimation();
        } else {
            playPropertyAnimation();
        }
    }

    @OnClick(R.id.ball)
    void onBallClick() {
        Toast.makeText(getContext(), "Ball clicked", Toast.LENGTH_SHORT).show();
    }

    private void playViewAnimation() {
        reset();
        final TranslateAnimation animation = new TranslateAnimation(0, 0, 0, distance);
        animation.setDuration(ANIMATION_DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(final Animation animation) {
                playButton.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                playButton.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }
        });
        ball.startAnimation(animation);
    }

    private void playPropertyAnimation() {
        reset();
        playButton.setEnabled(false);
        ball.animate()
                .translationY(distance)
                .setDuration(ANIMATION_DURATION)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        playButton.setEnabled(true);
                    }
                }).start();
    }

    private void reset() {
        ball.clearAnimation();
        ball.setTranslationY(0);
    }
}
