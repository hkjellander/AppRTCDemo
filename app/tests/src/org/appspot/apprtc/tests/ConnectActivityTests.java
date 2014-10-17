package org.appspot.apprtc.tests;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.clearText;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

import android.test.ActivityInstrumentationTestCase2;

import org.appspot.apprtc.ConnectActivity;
import org.appspot.apprtc.R;

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
    String url = "https://apprtc.appspot.com";
    String room = "my_room1234";

    // Type the text and click Connect button.
    onView(withId(R.id.url_edittext))
        .perform(clearText())
        .perform(typeText(url));
    onView(withId(R.id.room_edittext))
        .perform(typeText(room));

    onView(withId(R.id.connect_button))
        .perform(click());

    // TODO(kjellander): Mock out the http request so this can be run offline (and with invalid
    // URLs).
    onView(withId(R.id.room_name))
        .check(matches(withText(room)));
  }

  /**
   * Test the isValidUrl function. This should really be run as a real unit test that doesn't
   * require running on a device, but since running pure JUnit tests isn't properly supported by
   * Android Studio, we just run it here (even if it doesn't need the Activity context).
   * See https://github.com/Element84/AndroidTestExample/issues/1 for more info.
   */
  public void testIsValidUrl() {
    assertTrue(ConnectActivity.isValidUrl("http://foo.com/?r=bar"));
    assertTrue(ConnectActivity.isValidUrl("https://foo.com/?r=bar&r2=baz"));
    assertFalse(ConnectActivity.isValidUrl("ftp://foo.com"));
    assertFalse(ConnectActivity.isValidUrl("ftp://foo.com/?r=bar"));
  }

}
