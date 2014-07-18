package org.robobinding.widget.listview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class FooterAttributesTest {
    private FooterAttributes footerAttributes = new FooterAttributes();
    private Context context = new Activity();
    private ListView listView = new ListView(context);
    private View subView = new View(context);

    @Test
    public void testLayoutAttribute() {
	assertThat(footerAttributes.layoutAttribute(), is("footerLayout"));
    }

    @Test
    public void testSubViewPresentationModelAttribute() {
	assertThat(footerAttributes.subViewPresentationModelAttribute(), is("footerPresentationModel"));
    }

    @Test
    public void testVisibilityAttribute() {
	assertThat(footerAttributes.visibilityAttribute(), is("footerVisibility"));
    }

    @Test
    public void shouldAddSubViewToListView() {
	footerAttributes.addSubView(listView, subView, context);

	List<View> footerViews = Robolectric.shadowOf(listView).getFooterViews();
	assertThat(footerViews.size(), is(1));
	assertThat(footerViews.get(0), is(subView));
    }

    @Test
    public void shouldCreateNewFooterVisibility() {
	assertNotNull(footerAttributes.createVisibility(listView, subView));
    }
}