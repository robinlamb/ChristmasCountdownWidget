//Robin Lamb
//11/04/2019
//Christmas Countdown Widget

//This widget will get the current date and time from the device, determine if the current date is within
//the Christmas season (from December 25- January 6, Epiphany).  It will display "Merry Christmas" if it
//is within that time frame.  Otherwise, it will calculate and display the days left until Christmas.

package com.robinsmobilestuff.christmascountdownwidget;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.Calendar;
import android.view.View;


public class ChristmasCountdownWidget extends AppWidgetProvider{


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Loop to update each instance of the widget the user may have
        for (int widgetId : appWidgetIds){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);

            //Get current date from device
            Calendar calNow = Calendar.getInstance();

            boolean blIsInChristmasSeason = CheckDate(calNow);

            //Display Merry Christmas if it is the Christmas season
            if (blIsInChristmasSeason){
                remoteViews.setViewVisibility(R.id.tvNumber, View.GONE);
                remoteViews.setTextViewText(R.id.tvDaysUntil, "Merry");
            }

            //Calculate days until Christmas if it is not
            else{
                int intNumberDays = CalculateDaysUntilChristmas(calNow);

                remoteViews.setViewVisibility(R.id.tvNumber, View.VISIBLE);

                remoteViews.setTextViewText(R.id.tvNumber, String.valueOf(intNumberDays));

                //Grammar handling
                if (intNumberDays > 1){
                    remoteViews.setTextViewText(R.id.tvDaysUntil, "Days Until");
                }
                else {
                    remoteViews.setTextViewText(R.id.tvDaysUntil, "Day Until");
                }
            }
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }



    //Check current date to see if it falls within Christmas Season
    boolean CheckDate(Calendar calendar){
        if (((calendar.get(Calendar.MONTH) == Calendar.DECEMBER) &&
                (calendar.get(Calendar.DAY_OF_MONTH) >= 25)) || ((calendar.get(Calendar.MONTH) == Calendar.JANUARY) &&
                (calendar.get(Calendar.DAY_OF_MONTH) <= 6))){
            return true;
        }
        else
            return false;
    }


    //Calculate days until Christmas
    public int CalculateDaysUntilChristmas(Calendar calendar) {
        //Build a new calendar object of next Christmas
        Calendar nextChristmas = Calendar.getInstance();

        nextChristmas.set(Calendar.MONTH, Calendar.DECEMBER);
        nextChristmas.set(Calendar.DAY_OF_MONTH, 25);
        nextChristmas.set(Calendar.HOUR, 0);
        nextChristmas.set(Calendar.MINUTE, 0);

        //Subtract days
        return nextChristmas.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR);
    }
}
