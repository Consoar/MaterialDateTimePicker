/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wdullaer.materialdatetimepicker.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class LunarMonthView extends MonthView {

    public LunarMonthView(Context context) {
        super(context);
    }

    @Override
    public void drawMonthDay(Canvas canvas, int year, int month, int day,
            int x, int y, int startX, int stopX, int startY, int stopY) {
        if (mSelectedDay == day) {
            canvas.drawCircle(x , y + (MINI_DAY_NUMBER_TEXT_SIZE / 3), (int)(DAY_SELECTED_CIRCLE_SIZE*1.05),
                    mSelectedCirclePaint);
        }

        // If we have a mindate or maxdate, gray out the day number if it's outside the range.
        if (isOutOfRange(year, month, day)) {
            mMonthNumPaint.setColor(mDisabledDayTextColor);
            mLunarMonthNumPaint.setColor(mDisabledDayTextColor);
        } else if (mSelectedDay == day) {
            mMonthNumPaint.setColor(mSelectedDayTextColor);
            mLunarMonthNumPaint.setColor(mSelectedDayTextColor);
        } else if (mHasToday && mToday == day) {
            mMonthNumPaint.setColor(mTodayNumberColor);
            mLunarMonthNumPaint.setColor(mTodayNumberColor);
        } else {
            mMonthNumPaint.setColor(mDayTextColor);
            mLunarMonthNumPaint.setColor(mDayTextColor);
        }
        Solar solar = new Solar();
        solar.solarYear = year;
        solar.solarMonth = month+1;
        solar.solarDay = day;
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        canvas.drawText(String.format("%d", day), x, y, mMonthNumPaint);
        canvas.drawText(LunarSolarConverter.getLunarDayString(lunar.lunarDay), x, y+getFontHeight(), mLunarMonthNumPaint);
    }

    public int getFontHeight() {
        Paint.FontMetrics fm = mMonthNumPaint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }
}
