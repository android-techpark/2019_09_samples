package superp.techpark.ru.lesson9;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Демонстрация работы интерполяторов на примере трех анимаций падения шарика
 */
public class InterpolatorFragment extends Fragment {

    @BindView(R.id.root)
    View root;

    @BindView(R.id.ball1)
    View ball1;

    @BindView(R.id.ball2)
    View ball2;

    @BindView(R.id.ball3)
    View ball3;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interpolator, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.play)
    void onPlayClick() {
        final int targetY = 2 * root.getHeight() / 3;
        final long duration = 5000L;

        final ObjectAnimator anim1 = ObjectAnimator.ofFloat(ball1, View.TRANSLATION_Y, 0, targetY)
                .setDuration(duration);
        final ObjectAnimator anim2 = ObjectAnimator.ofFloat(ball2, View.TRANSLATION_Y, 0, targetY)
                .setDuration(duration);
        final ObjectAnimator anim3 = ObjectAnimator.ofFloat(ball3, View.TRANSLATION_Y, 0, targetY)
                .setDuration(duration);

        anim1.setInterpolator(new LinearInterpolator());
        anim2.setInterpolator(new BounceInterpolator());
        anim3.setInterpolator(new OvershootInterpolator());

        anim1.start();
        anim2.start();
        anim3.start();
    }
}
