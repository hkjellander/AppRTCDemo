package org.appspot.apprtc;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

import android.test.ActivityInstrumentationTestCase2;

public class ConnectActivityTests extends ActivityInstrumentationTestCase2<ConnectActivity> {

  public ConnectActivityTests() {
    super(ConnectActivity.class);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    getActivity();
  }

  public void testEnterText() {
    String url = "http://my.company.com";
    String room = "my_room1234";

    // Type the text and click Connect button.
    onView(withId(R.id.url_edittext))
        .perform(clearText())
        .perform(typeText(url));
    onView(withId(R.id.room_edittext))
        .perform(typeText(room));

    onView(withId(R.id.connect_button))
        .perform(click());

    // TODO(kjellander): Verify that the outgoing Intent contains the right URL (or parts of it).
  }
}
