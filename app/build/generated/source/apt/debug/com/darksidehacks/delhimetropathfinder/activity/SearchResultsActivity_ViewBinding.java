// Generated code from Butter Knife. Do not modify!
package com.darksidehacks.delhimetropathfinder.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.darksidehacks.delhimetropathfinder.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchResultsActivity_ViewBinding implements Unbinder {
  private SearchResultsActivity target;

  @UiThread
  public SearchResultsActivity_ViewBinding(SearchResultsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchResultsActivity_ViewBinding(SearchResultsActivity target, View source) {
    this.target = target;

    target.actView = Utils.findRequiredViewAsType(source, R.id.stationSearch, "field 'actView'", AutoCompleteTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchResultsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.actView = null;
  }
}
