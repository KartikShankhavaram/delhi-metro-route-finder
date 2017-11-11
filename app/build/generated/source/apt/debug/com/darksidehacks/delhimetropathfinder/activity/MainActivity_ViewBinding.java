// Generated code from Butter Knife. Do not modify!
package com.darksidehacks.delhimetropathfinder.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.darksidehacks.delhimetropathfinder.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131230797;

  private View view2131230844;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.main_toolbar, "field 'toolbar'", Toolbar.class);
    target.source = Utils.findRequiredViewAsType(source, R.id.source, "field 'source'", AutoCompleteTextView.class);
    target.destination = Utils.findRequiredViewAsType(source, R.id.destination, "field 'destination'", AutoCompleteTextView.class);
    target.loadingSpinner = Utils.findRequiredViewAsType(source, R.id.loading_icon, "field 'loadingSpinner'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.get_route, "field 'showRoute' and method 'getRoute'");
    target.showRoute = Utils.castView(view, R.id.get_route, "field 'showRoute'", Button.class);
    view2131230797 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.getRoute();
      }
    });
    target.message = Utils.findRequiredViewAsType(source, R.id.message, "field 'message'", TextView.class);
    view = Utils.findRequiredView(source, R.id.refreshButton, "field 'refreshButton' and method 'refresh'");
    target.refreshButton = Utils.castView(view, R.id.refreshButton, "field 'refreshButton'", ImageButton.class);
    view2131230844 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.refresh();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.source = null;
    target.destination = null;
    target.loadingSpinner = null;
    target.showRoute = null;
    target.message = null;
    target.refreshButton = null;

    view2131230797.setOnClickListener(null);
    view2131230797 = null;
    view2131230844.setOnClickListener(null);
    view2131230844 = null;
  }
}
