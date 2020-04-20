package superp.techpark.ru.lesson9;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MyCoolBehavior extends CoordinatorLayout.Behavior<View> {

    public MyCoolBehavior() {
    }

    public MyCoolBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent,
                                   @NonNull View child,
                                   @NonNull View dependency) {
        return super.layoutDependsOn(parent, child, dependency) || dependency.getId() == R.id.button_trigger;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent,
                                          @NonNull View child,
                                          @NonNull View dependency) {
        if(dependency.getId() == R.id.button_trigger) {
            child.setTranslationY(Math.min(0, dependency.getTop() - child.getBottom()));
            return true;
        }
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
