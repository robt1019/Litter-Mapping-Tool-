package org.littermappingtool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by rob on 04/08/15.
 */
public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button addNewItemButton = (Button) findViewById(R.id.start_logging_litter);
        addNewItemButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                // start event with null event id value if not logging litter as part of an event
                Intent intent = new Intent(MenuActivity.this, LitterMapperActivity.class);
                Long nullLong = null;
                intent.putExtra(EventPicker.EXTRA_EVENT_ID, nullLong);
                startActivity(intent);
            }
        });

        Button listAllItemsButton = (Button) findViewById(R.id.log_litter_with_event);
        listAllItemsButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                //Load new event menu

                Intent intent = new Intent(MenuActivity.this, EventPicker.class);
//                intent.putExtra();
                startActivity(intent);
            }
        });
    }

}
