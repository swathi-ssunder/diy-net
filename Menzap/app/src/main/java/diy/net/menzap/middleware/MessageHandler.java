package diy.net.menzap.middleware;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;

import diy.net.menzap.helper.DataHolder;
import diy.net.menzap.helper.EventDBHelper;
import diy.net.menzap.helper.MenuDBHelper;
import diy.net.menzap.model.Event;
import diy.net.menzap.model.Menu;
import diy.net.menzap.model.message.Message;
import fi.tkk.netlab.dtn.scampi.applib.SCAMPIMessage;

/**
 * Created by swathissunder on 22/09/16.
 */

public class MessageHandler {

    //==========================================================================//
    // Definitions of the SCAMPIMessage fields
    //==========================================================================//
    public static final String MSG_TYPE = "TYPE";
    public static final String MSG_SENDER = "SENDER";
    public static final String MSG_TIMESTAMP = "TIMESTAMP";
    public static final String MSG_UNIQUE_ID = "UNIQUE_ID";
    public static final String MSG_EVENT_NAME = "EVENT_NAME";
    public static final String MSG_EVENT_DESCRIPTION = "EVENT_DESCRIPTION";
    public static final String MSG_LOCATION = "LOCATION";
    public static final String MSG_START_TIME = "START_TIME";
    public static final String MSG_END_TIME = "END_TIME";

    public static final String MSG_MENU_NAME = "MENU_NAME";
    public static final String MSG_MENU_DESCRIPTION = "MENU_DESCRIPTION";
    public static final String MSG_CATEGORY = "CATEGORY";
    public static final String MSG_SERVED_ON = "SERVED_ON";
    //==========================================================================//

    private Context context;


    public MessageHandler(Context context) {
        this.context = context;
    }

    public void handleIncomingMessage(SCAMPIMessage msg )
            throws IOException {

        // Database where incoming messages are to be stored
        SQLiteOpenHelper db;

        switch(Message.MessageType.valueOf(msg.getString( MSG_TYPE ))) {
            case ENTER:
                break;
            case EXIT:
                break;
            case MENU:

                Menu menu = new Menu(msg.getInteger(MSG_SENDER), msg.getString(MSG_MENU_NAME), msg.getString(MSG_MENU_DESCRIPTION),
                        msg.getInteger(MSG_CATEGORY), msg.getString(MSG_SERVED_ON),
                        msg.getInteger(MSG_TIMESTAMP), msg.getInteger(MSG_UNIQUE_ID));

                db = new MenuDBHelper(this.context);
                // Insert into the database
                if (((MenuDBHelper)db).insert(menu)) {
                    Log.d("MENU added", ((MenuDBHelper)db).getAll().toString());
                }
                db.close();
                // notifying only when the difference of time with current time is 5 seconds
                if( System.currentTimeMillis() - msg.getInteger(MSG_TIMESTAMP) < 300000)
                    DataHolder.getInstance().getNotificationHelper().notifyForMenu(menu);

                break;
            case REVIEW:
                break;
            case EVENT:

                Event event = new Event(msg.getInteger(MSG_SENDER), msg.getString(MSG_EVENT_NAME), msg.getString(MSG_EVENT_DESCRIPTION),
                        msg.getString(MSG_LOCATION), msg.getString(MSG_START_TIME), msg.getString(MSG_END_TIME),
                        msg.getInteger(MSG_TIMESTAMP), msg.getInteger(MSG_UNIQUE_ID));

                db = new EventDBHelper(this.context);
                // Insert into the database
                if (((EventDBHelper)db).insert(event)) {
                    Log.d("EVENT added", ((EventDBHelper)db).getAll().toString());
                }
                db.close();
                // notifying only when the difference of time with current time is 5 seconds
                if( System.currentTimeMillis() - msg.getInteger(MSG_TIMESTAMP) < 300000)
                    DataHolder.getInstance().getNotificationHelper().notifyForEvent(event);

                break;
        }
    }
}