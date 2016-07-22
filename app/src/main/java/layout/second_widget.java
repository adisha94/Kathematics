package layout;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.app.PendingIntent;
import android.widget.Toast;

import com.kathaMain.adish.tutorial.R;
import KathaMain.MyActivity;
/**
 * Implementation of App Widget functionality.
 */
public class second_widget extends AppWidgetProvider
{


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {
        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.second_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        String url = "google.com";
        // There may be multiple widgets active, so update all of them
        Intent intent = new Intent(context, MyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.setData(Uri.parse(url));
        for (int appWidgetId : appWidgetIds)
        {
            PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.second_widget);
            updateAppWidget(context, appWidgetManager, appWidgetId);

            views.setOnClickPendingIntent(R.id.button, pending);
            appWidgetManager.updateAppWidget(R.id.second_widget, views);
            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
        }


        Bundle extra = intent.getExtras();
//        if (extra != null)
//            mAppWidgetManager
    }


    @Override
    public void onDeleted(Context context, int [] appWidgetId)
    {
        super.onDeleted(context, appWidgetId);
        // Toast.makeText(context, "onDeleted()  ", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onEnabled(Context context)
    {
        super.onEnabled(context);
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        super.onDisabled(context);
        // Enter relevant functionality for when the last widget is disabled
    }

}