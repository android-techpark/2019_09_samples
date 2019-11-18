package superp.techpark.ru.lesson9;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Пример показывает как можно комбинировать работу нескольких анимаций с помощью разных API
 */
public class AnimatorSetFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_animator_set, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.play)
    void onPlayClick() {
        reset();

        final int targetY = 2 * root.getHeight() / 3;
        final AnimatorSet together = new AnimatorSet();
        final long duration = 2000L;
        together.playTogether(ObjectAnimator.ofFloat(ball1, View.TRANSLATION_Y, 0, targetY).setDuration(duration),
                ObjectAnimator.ofFloat(ball3, View.TRANSLATION_Y, 0, targetY).setDuration(duration));
        final AnimatorSet all = new AnimatorSet();
        all.playSequentially(together, ObjectAnimator.ofFloat(ball2, View.TRANSLATION_Y, 0, targetY).setDuration(duration));
        all.start();
    }

    @OnClick(R.id.play_with_builder)
    void onPlayWithBuilderClick() {
        reset();

        final int targetY = 2 * root.getHeight() / 3;
        final long duration = 2000L;
        final AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(ball1, View.TRANSLATION_Y, 0, targetY).setDuration(duration))
                .with(ObjectAnimator.ofFloat(ball3, View.TRANSLATION_Y, 0, targetY).setDuration(duration))
                .after(ObjectAnimator.ofFloat(ball2, View.TRANSLATION_Y, 0, targetY).setDuration(duration));
        set.start();
    }

    private void reset() {
        ball1.setTranslationY(0);
        ball2.setTranslationY(0);
        ball3.setTranslationY(0);
    }
}
