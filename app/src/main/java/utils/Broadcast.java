package utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by marksv on 1/4/2015.
 */
public class Broadcast {
    public static void broadcastUpdate(Context context, final String action, final String msg) {
        final Intent intent = new Intent(action);
        Bundle extras = new Bundle();
        extras.putString(MSGCode.EXTRA_DATA.toString(), msg);
        intent.putExtras(extras);
        context.sendBroadcast(intent);
    }
}
